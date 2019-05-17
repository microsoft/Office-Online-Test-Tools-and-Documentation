# coding=utf-8
import struct
from collections import namedtuple
from base64 import b64decode

from Crypto.Hash import SHA256
from Crypto.PublicKey import RSA
from Crypto.Signature import PKCS1_v1_5
from Crypto.Util import asn1


__author__ = 'Tyler Butler <tyler.butler@microsoft.com>'

ProofKeyDiscoveryData = namedtuple('ProofKeyDiscoveryData',
                                   ('value', 'modulus', 'exponent', 'oldvalue', 'oldmodulus', 'oldexponent'))
ProofKeyValidationInput = namedtuple('ProofKeyValidationInput',
                                     ('access_token', 'timestamp', 'url', 'proof', 'proof_old'))


def uint_bytearray(n):
    """
    Converts a numeric value representing an unsigned 32-bit integer into a bytestring in big-endian byte order.
    :param n: Number to encode
    :return: Packed bytestring in big-endian byte order
    """
    return struct.pack('>I', n)


def ulonglong_bytearray(l):
    """
    Converts a numeric value representing an unsigned 64-bit integer into a bytestring in big-endian byte order.
    :param l: Number to encode
    :return: Packed bytestring in big-endian byte order
    """
    return struct.pack('>Q', l)


def generate_key(modulus_b64, exp_b64):
    """
    Generates an RSA public key given a base64-encoded modulus and exponent
    :param modulus_b64: base64-encoded modulus
    :param exp_b64: base64-encoded exponent
    :return: an RSA public key
    """
    mod = int(b64decode(modulus_b64).encode('hex'), 16)
    exp = int(b64decode(exp_b64).encode('hex'), 16)
    seq = asn1.DerSequence()
    seq.append(mod)
    seq.append(exp)
    der = seq.encode()
    return RSA.importKey(der)


def try_verification(expected_proof, signed_proof_b64, public_key):
    """
    Verifies the signature of a signed WOPI request using a public key provided in
    WOPI discovery.
    :param expected_proof: a bytearray of the expected proof data
    :param signed_proof_b64: the signed proof key provided in the X-WOPI-Proof or
    X-WOPI-ProofOld headers. Note that the header values are base64-encoded, but
    will be decoded in this method
    :param public_key: the public key provided in WOPI discovery
    :return: True if the request was signed with the private key corresponding to
    the public key; otherwise, False
    """
    signed_proof = b64decode(signed_proof_b64)
    verifier = PKCS1_v1_5.new(public_key)
    h = SHA256.new(expected_proof)
    v = verifier.verify(h, signed_proof)
    return v


class ProofKeyHelper(object):
    def __init__(self, proof_key_discovery_data):
        self.current_key = generate_key(proof_key_discovery_data.modulus, proof_key_discovery_data.exponent)
        self.old_key = generate_key(proof_key_discovery_data.oldmodulus, proof_key_discovery_data.oldexponent)

    def validate(self, proof_key_validation_input):
        # get the signed proof and proof_old values
        proof = proof_key_validation_input.proof
        proof_old = proof_key_validation_input.proof_old

        # get access_token and access_token length and encode as a bytearray
        access_token = proof_key_validation_input.access_token.encode('utf-8')
        access_token_bytes = access_token  # str is already a bytestring in Python 2

        access_token_length = len(access_token)
        access_token_length_bytes = uint_bytearray(access_token_length)

        # get URL and length and encode as bytearray
        url = proof_key_validation_input.url.upper().encode('utf-8')
        url_bytes = url  # str is already a bytestring in Python 2

        url_length = len(url)
        url_length_bytes = uint_bytearray(url_length)

        # get timestamp and timestamp length and encode as bytearray
        timestamp = proof_key_validation_input.timestamp
        timestamp_bytes = ulonglong_bytearray(timestamp)
        timestamp_length = len(timestamp_bytes)
        timestamp_length_bytes = uint_bytearray(timestamp_length)

        expected_proof = (access_token_length_bytes +
                          access_token_bytes +
                          url_length_bytes +
                          url_bytes +
                          timestamp_length_bytes +
                          timestamp_bytes)

        # TODO: in real code you should also check that the timestamp is no more than 20 minutes old,
        # but because we're using predefined test cases we can't do that here

        validation_result = (try_verification(expected_proof, proof, self.current_key) or
                             try_verification(expected_proof, proof_old, self.current_key) or
                             try_verification(expected_proof, proof, self.old_key))
        return validation_result
