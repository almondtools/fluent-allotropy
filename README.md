Fluent Allotropy
================

Fluent Allotropy is an assertion framework for [Allotropy](https://github.com/almondtools/allotropy) for testing layouts of web sites.

It provides an embedded domain specific language with full java type support and domain specific assertions.

Comparison with Galen
---------------------

| Feature                  | Allotropy               | Galen                          |
| ------------------------ | ----------------------- | ------------------------------ |
| Specification Language   | Java                    | Domain Specific Language       |
| Platform                 | JUnit                   | Galen                          |
| Complexity               | Declarative (no loops)  | Turing Complete (if, for, ...) |
|                          | (use Java for loops)    |                                |
| Device Specification     | Must be Provided        | Yes                            |
|                          | (use allotropy)         |                                |
| Assertions on            |                         |                                |
| - Sub Components         | Runtime Evaluated       | Statically Evaluated           |
| - Quantifiers            | Declarative             | Imperative                     |
| - Relative Position      | X                       | X                              |
| - Dimensions             | X                       | X                              |
| - Alignment              | X                       | X                              |
| - Text Content           | X                       | X                              |
| - CSS Properties         | X                       | X                              |
| - Visibility             | X                       | X                              |
| - Layering               | X                       | X                              |
| - Counting               | X                       | X                              |
| - OCR                    | -                       | X                              |
| - Colors                 | - (planned)             | X                              |
| - Image                  | - (planned)             | X                              |
| - Imperative Extension   | - (use Java)            | X                              |
