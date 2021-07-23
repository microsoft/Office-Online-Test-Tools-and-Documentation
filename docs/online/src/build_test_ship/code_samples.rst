
..  _Code samples:

Example code
============

Sample WOPI host
----------------

The `|wac| GitHub repository <https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation>`_
contains a
`sample implementation of a WOPI host <https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/tree/master/samples/SampleWopiHandler>`_
written in C#. This sample implementation illustrates many of the concepts necessary to implement a WOPI server,
including:

* Handling requests at particular :ref:`Endpoints`
* Handling WOPI operations such as :ref:`CheckFileInfo`, :ref:`GetFile`, and :ref:`PutFile`
* Example :ref:`proof key verification <Proof Keys>` (also see :ref:`Proof key unit tests`)

You can find a documents preview implementation written on golang here - https://github.com/ildarusmanov/msofficepreview

..  _Proof key unit tests:

Proof key unit tests
--------------------

Example test cases and data that can be used to validate proof key verification implementations can be found here:
https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/SampleWopiHandler/SampleWopiHandler.UnitTests/ProofKeyTests.cs

While these tests are written in C#, they can be adapted to any language. If you are having difficulties implementing
proof keys, these test cases can be a useful tool for troubleshooting. See also the :ref:`Troubleshooting proof keys`
section.

These same test cases along with basic proof key validation implementations are available in both Java and Python:

* Java: https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/java/ProofKeyTester.java
* Python: https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/python/proof_keys/tests.py

Note that the Python samples depend on PyCrypto being installed.
