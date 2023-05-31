package net.amygdalum.allotropy.fluent;

import static org.junit.platform.commons.support.ReflectionSupport.findFields;

import java.nio.file.Paths;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.TestInstances;
import org.junit.platform.commons.support.HierarchyTraversalMode;

import io.undertow.Undertow;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;

public class LocalHttpServer implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Store store = context.getRoot().getStore(Namespace.create(LocalHttpServer.class));
        Server server = store.getOrComputeIfAbsent(Server.class, s -> Server.start(), Server.class);
        TestInstances testInstances = context.getRequiredTestInstances();
        for (var instance : testInstances.getAllInstances()) {
            for (var field : findFields(instance.getClass(), f -> f.getType() == Server.class, HierarchyTraversalMode.BOTTOM_UP)) {
                field.setAccessible(true);
                field.set(instance, server);
            }
        }
    }

    public static record Server(Undertow server, int port) implements Store.CloseableResource {

        public static Server start() {
            int port = 8080;
            while (port <= 9000) {
                try {
                    Undertow server = Undertow.builder()
                        .addHttpListener(port, "localhost")
                        .setHandler(new ResourceHandler(new FileResourceManager(Paths.get("src/test/resources/html").toFile())))
                        .build();
                    server.start();
                    return new Server(server, port);
                } catch (Exception e) {
                    port++;
                    continue;
                }
            }
            throw new IllegalStateException("cannot find appropriate port to start local http server");
        }

        public String url(String path) {
            return "http://localhost:" + port + path;
        }

        @Override
        public void close() {
            server.stop();
        }
    }

}
