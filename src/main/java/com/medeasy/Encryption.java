package com.medeasy;

import io.github.cdimascio.dotenv.Dotenv;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryption {
    private Key initEncryption() throws Exception
    {
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        FileInputStream stream =  new FileInputStream(Dotenv.load().get("ENCRYPTION_KEYSTORE_PATH"));
        keyStore.load(stream,Dotenv.load().get("ENCRYPTION_SECRET").toCharArray());
        Key key = keyStore.getKey(Dotenv.load().get("ENCRYPTION_ALIAS"), Dotenv.load().get("ENCRYPTION_SECRET").toCharArray());
        return key;
    }
    public String getEncryptedKey(String secret)
    {
        String encryptedData = null;
        try {

            encryptedData = encryptUsingASEKey (secret, initEncryption().getEncoded() );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return encryptedData;
    }
    public String getDecryptedKey(String encryptedData)
    {
        String decryptedData = null;
        try {
            decryptedData = decryptUsingASEKey(encryptedData,initEncryption().getEncoded());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return decryptedData;
    }

    private String decryptUsingASEKey(String data, byte[] key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secKey = new SecretKeySpec(key,"AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE,secKey);
        byte[] newBytes = cipher.doFinal(Base64.getDecoder().decode(data.getBytes()));
        return new String(newBytes);
    }
    private String encryptUsingASEKey(String data, byte[] key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secKey = new SecretKeySpec(key,"AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,secKey);
        byte[] newBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(newBytes);
    }
}
