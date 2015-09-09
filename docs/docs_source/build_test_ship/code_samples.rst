..  _Code samples:

Example code
============

Sample WOPI host
----------------

The `Office Online GitHub repository <repo>`_ contains a
`sample implementation of a WOPI host <https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/tree/master/samples/SampleWopiHandler>`_
written in C#. This sample implementation illustrates many of the concepts necessary to implement a WOPI server,
including:

* Handling requests at particular :ref:`Endpoints`
* Handling WOPI operations such as :ref:`CheckFileInfo`, :ref:`GetFile`, and :ref:`PutFile`
* Example :ref:`proof key verification <Proof Keys>` (also see :ref:`Proof key unit tests`)

..  _sample:
    https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/tree/master/samples/SampleWopiHandler


..  _Proof key unit tests:

Proof key unit tests
--------------------

Example test cases and data that can be used to validate proof key verification implementations can be found here:
https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/SampleWopiHandler/SampleWopiHandler.UnitTests/ProofKeyTests.cs

While these tests are written in C#, they can be adapted to any language. If you are having difficulties implementing
proof keys, these test cases can be a useful tool for troubleshooting. See also the :ref:`Troubleshooting proof keys`
section.
