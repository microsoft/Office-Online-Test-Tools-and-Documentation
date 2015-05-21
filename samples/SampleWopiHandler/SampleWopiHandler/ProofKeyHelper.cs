using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;

namespace SampleWopiHandler
{
    public struct KeyInfo
    {
        private string _key;
        private string _exponent;
        private string _modulus;

        public KeyInfo(string key, string modulus, string exponent)
        {
            _key = key;
            _exponent = exponent;
            _modulus = modulus;
        }

        public string Key { get { return _key; } }
        public string Exponent { get { return _exponent; } }
        public string Modulus { get { return _modulus; } }
    }

    public class ProofKeyValidationInput
    {
        private string _accessToken;
        private long _timestamp;
        private string _url;
        private string _proof;
        private string _oldProof;

        public ProofKeyValidationInput(string accessToken, long timestamp, string url, string proof, string oldProof)
        {
            _accessToken = accessToken;
            _timestamp = timestamp;
            _url = url;
            _proof = proof;
            _oldProof = oldProof;
        }

        public string AccessToken { get { return _accessToken; } }
        public long Timestamp { get { return _timestamp; } }
        public string Url { get { return _url; } }
        public string Proof { get { return _proof; } }
        public string OldProof { get { return _oldProof; } }
    }

    public class ProofKeysHelper
    {
        private KeyInfo _currentKey;
        private KeyInfo _oldKey;

        public ProofKeysHelper(KeyInfo current, KeyInfo old)
        {
            _currentKey = current;
            _oldKey = old;
        }

        public bool Validate(ProofKeyValidationInput testCase)
        {
            // Encode values from headers into byte[]
            var accessTokenBytes = Encoding.UTF8.GetBytes(testCase.AccessToken);
            var hostUrlBytes = Encoding.UTF8.GetBytes(testCase.Url.ToUpperInvariant());
            var timeStampBytes = EncodeNumber(testCase.Timestamp);

            // prepare a list that will be used to combine all those arrays together
            List<byte> expectedProof = new List<byte>(
                4 + accessTokenBytes.Length +
                4 + hostUrlBytes.Length +
                4 + timeStampBytes.Length);

            expectedProof.AddRange(EncodeNumber(accessTokenBytes.Length));
            expectedProof.AddRange(accessTokenBytes);
            expectedProof.AddRange(EncodeNumber(hostUrlBytes.Length));
            expectedProof.AddRange(hostUrlBytes);
            expectedProof.AddRange(EncodeNumber(timeStampBytes.Length));
            expectedProof.AddRange(timeStampBytes);

            // create another byte[] from that list
            byte[] expectedProofArray = expectedProof.ToArray();

            // validate it against current and old keys in proper combinations
            bool validationResult =
                TryVerification(expectedProofArray, testCase.Proof, _currentKey) ||
                TryVerification(expectedProofArray, testCase.OldProof, _currentKey) ||
                TryVerification(expectedProofArray, testCase.Proof, _oldKey);

            // TODO:
            // in real code you should also check that TimeStamp header is no more than 20 minutes old
            // but because we're using predefined test cases to validate that the method works
            // we can't do it here.
            return validationResult;
        }

        private static byte[] EncodeNumber(int value)
        {
            return BitConverter.GetBytes(value).Reverse().ToArray();
        }

        private static byte[] EncodeNumber(long value)
        {
            return BitConverter.GetBytes(value).Reverse().ToArray();
        }

        private static bool TryVerification(byte[] expectedProof, string signedProof, KeyInfo keyToTry)
        {
            using (RSACryptoServiceProvider rsaAlg = new RSACryptoServiceProvider())
            {
                try
                {
                    rsaAlg.ImportCspBlob(Convert.FromBase64String(keyToTry.Key));
                    return rsaAlg.VerifyData(expectedProof, "SHA256", Convert.FromBase64String(signedProof));
                }
                catch (FormatException)
                {
                    return false;
                }
                catch (CryptographicException)
                {
                    return false;
                }
            }
        }
    }
}