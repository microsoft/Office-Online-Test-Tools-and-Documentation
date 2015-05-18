
..  _Proof Keys:

Verifying that requests originate from Office Online by using proof keys
========================================================================

When processing WOPI requests from Office Online, you might want to verify that these requests are coming from Office
Online. To do this, you use proof keys.

Office Online signs every WOPI request with a private key. The corresponding public key is available in the proof-key
element in the WOPI discovery XML. The signature is sent with every request in the **X-WOPI-Proof** and
**X-WOPI-ProofOld** HTTP headers.

The signature is assembled from information that is available to the WOPI host when it processes the incoming WOPI
request. To verify that a request came from Office Online, you must:

* Create the expected value of the proof headers.
* Use the public key provided in WOPI discovery to decrypt the proof provided in the **X-WOPI-Proof** header.
* Compare the expected proof to the decrypted proof. If they match, the request originated from Office Online.

Constructing the expected proof
-------------------------------

To construct the expected proof, you must assemble a byte array consisting of the access token, the URL of the
request (in uppercase), and the value of the **X-WOPI-TimeStamp** HTTP header from the request. Each of these values
must be converted to a byte array. In addition, you must include the length, in bytes, of each of these values.

To convert the access token and request URL values, which are strings, to byte arrays, you must encode the original
strings in UTF-8. Convert the **X-WOPI-TimeStamp** header to a *long* and then into a byte array. Do not treat it as a
string.

Then, assemble the data as follows:

* 4 bytes that represent the length, in bytes, of the access_token on the request.
* The access_token.
* 4 bytes that represent the length, in bytes, of the full URL of the WOPI request.
* The WOPI request URL in all uppercase.
* 4 bytes that represent the length, in bytes, of the **X-WOPI-TimeStamp** value.
* The **X-WOPI-TimeStamp** value.

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

If your application is built on the .NET framework, you should use the contents of the **value** and **valueOld**
attributes of the proof-key element in the WOPI discovery XML. These two attributes contain the Base64-encoded public
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
modulus and exponent of the current key are found in the modulus and exponent attributes of the proof-key element in
the WOPI discovery XML. The modulus and exponent of the old key are found in the **oldmodulus** and **oldexponent**
attributes. All four of these values are Base64-encoded.

The steps to import these values differ based on the language, platform, and cryptography API that you are using.

The following example shows how to import the public key by using the modulus and exponent in a Python program using
the PyCrypto library.

..  code-block:: python

    from base64 import b64decode
    from Crypto.PublicKey import RSA
    from Crypto.Util import asn1

    def generate_key(modulus_b64, exp_b64):
        mod = int(b64decode(modulus_b64).encode('hex'), 16)
        exp = int(b64decode(exp_b64).encode('hex'), 16)
        seq = asn1.DerSequence()
        seq.append(mod)
        seq.append(exp)
        der = seq.encode()
        return RSA.importKey(der)

    # proof_key_attributes is from the discovery XML
    key = generate_key(proof_key_attributes['modulus'], proof_key_attributes['exponent'])

Verifying the proof keys
------------------------

After you import the key, you can use the VerifyData method to verify the proof keys. Because Office Online rotates
the current and old proof keys periodically, you have to check three combinations of proof key values:

* The **X-WOPI-Proof** value using the current public key
* The **X-WOPI-ProofOld** value using the current public key
* The **X-WOPI-Proof** value using the old public key

If any one of the values is valid, the request was signed by Office Online.

The following example shows how to verify one of these combinations in .NET.

..  code-block:: csharp

    private static bool TryVerification(byte[] expectedProof, byte[] signedProof, byte[] publicKeyToTry, int keySize)
    {
        using (RSACryptoServiceProvider rsaAlg = new RSACryptoServiceProvider(keySize))
        {
            try
            {
                rsaAlg.ImportCspBlob(publicKeyToTry);
                bool result = rsaAlg.VerifyData(expectedProof, "SHA256", signedProof);
                return result;
            }
            catch(Exception e)
            {
                return false;
            }
        }
    }

The following example shows how to verify one of these combinations in Python using the PyCrypto library.

..  code-block:: python

    from base64 import b64decode
    from Crypto.Hash import SHA256
    from Crypto.Signature import PKCS1_v1_5

    def try_verification(expected_proof, signed_proof, public_key):
        verifier = PKCS1_v1_5.new(public_key)
        h = SHA256.new(expected_proof)
        return verifier.verify(h, signed_proof)
