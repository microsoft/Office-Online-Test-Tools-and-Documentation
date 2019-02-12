
..  _Proof Keys:

Verifying that requests originate from Office Online by using proof keys
========================================================================

When processing WOPI requests from Office Online, you might want to verify that these requests are coming from Office
Online. To do this, you use proof keys.

Office Online signs every WOPI request with a private key. The corresponding public key is available in the
**proof-key** element in the WOPI discovery XML. The signature is sent with every request in the **X-WOPI-Proof** and
**X-WOPI-ProofOld** HTTP headers.

The signature is assembled from information that is available to the WOPI host when it processes the incoming WOPI
request. To verify that a request came from Office Online, you must:

* Create the expected value of the proof headers.
* Use the public key provided in WOPI discovery to decrypt the proof provided in the **X-WOPI-Proof** header.
* Compare the expected proof to the decrypted proof. If they match, the request originated from Office Online.
* Ensure that the **X-WOPI-TimeStamp** header is no more than 20 minutes old.

When validating proof keys, if a request is not signed properly, the host must return a :http:statuscode:`500`.

..  note::
    Requests to the :term:`FileUrl` will not be signed. The FileUrl is used exactly as provided by the host, so it does
    not necessarily include the access token, which is required to construct the expected proof.

..  tip::
    The `Office Online GitHub repository <https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation>`_
    contains a set of unit tests that hosts can adapt to verify proof key validation implementations. See
    :ref:`Proof key unit tests` for more information.


Constructing the expected proof
-------------------------------

To construct the expected proof, you must assemble a byte array consisting of the access token, the URL of the
request (in uppercase), and the value of the **X-WOPI-TimeStamp** HTTP header from the request. Each of these values
must be converted to a byte array. In addition, you must include the length, in bytes, of each of these values.

To convert the access token and request URL values, which are strings, to byte arrays, you must ensure the original
strings are in UTF-8 first, then convert the UTF-8 strings to byte arrays. Convert the **X-WOPI-TimeStamp** header
to a *long* and then into a byte array. Do not treat it as a string.

Then, assemble the data as follows:

* 4 bytes that represent the length, in bytes, of the ``access_token`` on the request.
* The ``access_token``.
* 4 bytes that represent the length, in bytes, of the full URL of the WOPI request, including any query string
  parameters.
* The WOPI request URL in all uppercase. All query string parameters on the request URL should be included.
* 4 bytes that represent the length, in bytes, of the **X-WOPI-TimeStamp** value.
* The **X-WOPI-TimeStamp** value.

The following code samples illustrate the construction of an expected proof in C#, Java, and Python.

..  literalinclude:: ../../../../samples/SampleWopiHandler/SampleWopiHandler/ProofKeyHelper.cs
    :caption: Constructing the expected in proof in C#
    :language: csharp
    :linenos:
    :lineno-match:
    :dedent: 8
    :lines: 62-96
    :emphasize-lines: 3-22


Retrieving the public key
-------------------------

Office Online provides two different public keys as part of the WOPI discovery XML: the current key and the old key.
Two keys are necessary because the discovery data is meant to be cached by the host, and Office Online periodically
rotates the keys it uses to sign requests. When the keys are rotated, the current key becomes the old key, and a new
current key is generated. This helps to minimize the risk that a host does not have updated key information from WOPI
discovery when Office Online rotates keys.

Both keys are represented in the discovery XML in two different formats. One format is for WOPI hosts that use the
.NET framework. The other format can be imported in a variety of different programming languages and platforms.


Using .NET to retrieve the public key
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

If your application is built on the .NET framework, you should use the contents of the **value** and **oldvalue**
attributes of the *proof-key* element in the WOPI discovery XML. These two attributes contain the Base64-encoded public
keys that are exported by using the `RSACryptoServiceProvider.ExportCspBlob`_ method of the .NET Framework.

To import this key in your application, you must decode it from Base64 then import it by using the
`RSACryptoServiceProvider.ImportCspBlob`_ method.

..  _RSACryptoServiceProvider.ExportCspBlob: https://msdn.microsoft.com/en-us/library/
    system.security.cryptography.rsacryptoserviceprovider.exportcspblob(v=vs.110).aspx
..  _RSACryptoServiceProvider.ImportCspBlob: https://msdn.microsoft.com/en-us/library/
    system.security.cryptography.rsacryptoserviceprovider.importcspblob(v=vs.110).aspx


Using the RSA modulus and exponent to retrieve the public key
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

For hosts that don't use the .NET framework, Office Online provides the RSA modulus and exponent directly. The
modulus and exponent of the current key are found in the **modulus** and **exponent** attributes of the *proof-key*
element in the WOPI discovery XML. The modulus and exponent of the old key are found in the **oldmodulus** and
**oldexponent** attributes. All four of these values are Base64-encoded.

The steps to import these values differ based on the language, platform, and cryptography API that you are using.

The following examples show how to import the public key by using the modulus and exponent in both Java and Python
(using the PyCrypto library).

Java
^^^^

..  literalinclude:: ../../../../samples/java/ProofKeyTester.java
    :caption: Generating a public key from a modulus and exponent in Java
    :language: java
    :linenos:
    :lineno-match:
    :lines: 121-129
    :dedent: 4

Python
^^^^^^

..  literalinclude:: ../../../../samples/python/proof_keys/__init__.py
    :caption: Generating a public key from a modulus and exponent in Python
    :language: python
    :linenos:
    :lineno-match:
    :lines: 38-51


Verifying the proof keys
------------------------

After you import the key, you can use a verification method provided by your cryptography library to verify incoming
requests were signed by Office Online. Because Office Online rotates the current and old proof keys periodically, you
have to check three combinations of proof key values:

* The **X-WOPI-Proof** value using the current public key
* The **X-WOPI-ProofOld** value using the current public key
* The **X-WOPI-Proof** value using the old public key

If any one of the values is valid, the request was signed by Office Online.

The following examples show how to verify one of these combinations in C#, Java, and Python.


Verification in C#
~~~~~~~~~~~~~~~~~~

..  literalinclude:: ../../../../samples/SampleWopiHandler/SampleWopiHandler/ProofKeyHelper.cs
    :caption: Sample proof key validation code in C#
    :language: csharp
    :linenos:
    :lineno-match:
    :dedent: 8
    :lines: 108-130


Verification in Java
~~~~~~~~~~~~~~~~~~~~

..  literalinclude:: ../../../../samples/java/ProofKeyTester.java
    :caption: Sample proof key validation code in Java
    :language: java
    :linenos:
    :lineno-match:
    :lines: 98-110
    :dedent: 4


Verification in Python
~~~~~~~~~~~~~~~~~~~~~~

..  literalinclude:: ../../../../samples/python/proof_keys/__init__.py
    :caption: Sample proof key validation code in Python
    :language: python
    :linenos:
    :lineno-match:
    :lines: 54-70


Proof key tests in the WOPI validator
-------------------------------------

The WOPI validator includes several tests that help verify proof key implementations.

ProofKeys.CurrentValid.OldValid
    Tests that hosts accept requests where the **X-WOPI-Proof** value is correctly signed with the current proof key,
    and the **X-WOPI-ProofOld** value is signed with the old proof key.

ProofKeys.CurrentValid.OldInvalid
    Tests that hosts accept requests where the **X-WOPI-Proof** value is correctly signed with the current proof key,
    but the **X-WOPI-ProofOld** value is invalid. This scenario is unusual and should not happen in a production
    environment, but since the **X-WOPI-Proof** value is signed with the current public key, the request should be
    accepted.

ProofKeys.CurrentInvalid.OldValidSignedWithCurrentKey
    Tests that hosts accept requests where the **X-WOPI-Proof** value is invalid but the **X-WOPI-ProofOld** value is
    signed with *current* public key. This can happen when a WOPI client such as Office Online has rotated proof keys
    but the host hasn't re-run :ref:`WOPI discovery <discovery>` yet.

ProofKeys.CurrentValidSignedWithOldKey.OldInvalid
    Tests that hosts accept requests where the **X-WOPI-ProofOld** value is invalid but the **X-WOPI-Proof** value is
    signed with *old* public key. This can happen when a WOPI client has rotated proof keys, the host has re-run
    :ref:`WOPI discovery <discovery>` and has the updated keys, but the datacenter machine making the WOPI request
    does not yet have the updated keys.

ProofKeys.CurrentInvalid.OldValidSignedWithOldKey
    Tests that hosts reject requests where the **X-WOPI-Proof** value is invalid, and the **X-WOPI-ProofOld** value
    is signed with the *old* public key. This scenario is unusual and should not happen in a production
    environment; such requests should be rejected.

ProofKeys.CurrentInvalid.OldInvalid
    Tests that hosts reject requests with invalid current and old proof keys.

ProofKeys.TimestampOlderThan20Min
    Tests that hosts reject requests with an **X-WOPI-Timestamp** value that represents a time more than 20
    minutes old.


..  _Troubleshooting proof keys:

Troubleshooting proof key implementations
-----------------------------------------

If you are having difficulty with your proof key verification implementation, here are some common issues that you
should investigate:

* Verify you're converting the URL to uppercase.
* Verify you're including any query string parameters on the URL when transforming it for the purposes of building the
  expected proof value.
* Verify you're using the same encoding for any special characters that may be in the URL.
* Verify you're using an HTTPS URL if your WOPI endpoints are HTTPS. This is especially important if you have SSL
  termination in your network prior to your WOPI request handlers. In this case, the URL Office Online will use to sign
  the requests will be HTTPS, but the URL your WOPI handlers ultimately receive will be HTTP. If you simply use the
  incoming request URL your expected proof will not match the signature provided by Office Online.

In addition, use the :ref:`Proof key unit tests` to verify your implementation with sample data.
