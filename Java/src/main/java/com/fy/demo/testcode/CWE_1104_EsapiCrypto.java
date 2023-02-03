package com.fy.demo.testcode;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.crypto.CipherText;
import org.owasp.esapi.crypto.PlainText;
import org.owasp.esapi.errors.EncryptionException;

import javax.crypto.SecretKey;

public class CWE_1104_EsapiCrypto {

    private static final org.owasp.esapi.Encryptor encryptor = ESAPI.encryptor();
    private static final org.owasp.esapi.Encoder encoder = ESAPI.encoder();

    public String encrypt(String text)  {
        PlainText plainText = new PlainText(text);
        try {
            CipherText cipherText = encryptor.encrypt(plainText);
            return encoder.encodeForBase64(cipherText.asPortableSerializedByteArray(), false);
        } catch (EncryptionException e) {
        }
        return "";
    }

    public String encrypt(SecretKey key, String text) {
        PlainText plainText = new PlainText(text);
        CipherText cipherText = null;
        try {
            cipherText = encryptor.encrypt(key, plainText);
            return encoder.encodeForBase64(cipherText.asPortableSerializedByteArray(), false);
        } catch (EncryptionException e) {
        }
        return "";
    }
    public String decrypt(byte[] data) {
        try {
            PlainText plainText = encryptor.decrypt(CipherText.fromPortableSerializedBytes(data));
            return plainText.toString();
        } catch (EncryptionException e) {
        }
        return "";
    }

    public String decrypt(SecretKey key, byte[] data) {
        try {
            PlainText plainText = encryptor.decrypt(key, CipherText.fromPortableSerializedBytes(data));
            return plainText.toString();
        } catch (EncryptionException e) {
        }
        return "";
    }
}
