package test;

import com.medeasy.Encryption;

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

public class Encrept {
    public static void main(String[] args) throws Exception {
//        try {
//                KeyStore keyStore = KeyStore.getInstance("JCEKS");
//                FileInputStream stream =  new FileInputStream("mykeystore.jks");
//                keyStore.load(stream,"secret".toCharArray());
//                Key key = keyStore.getKey("myKey","secret".toCharArray());
//                String data  = "Rifat Hridoy";
//                String encryptedData = encryptUsingASEKey (data, key.getEncoded() );
//            System.out.println(encryptedData);
//                String decryptedData = decryptUsingASEKey(encryptedData,key.getEncoded());
//            System.out.println(decryptedData);
//        }
//     catch (Exception exception) {
//    }

        Encryption en = new Encryption();
        String str = en.getEncryptedKey("Rifat");
        System.out.println(en.getDecryptedKey("JiPnstpy3QzhA2bQBb3eqQ=="));
}

//    private static String decryptUsingASEKey(String data, byte[] key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
//        SecretKeySpec secKey = new SecretKeySpec(key,"AES");
//        Cipher cipher = Cipher.getInstance("AES");
//        cipher.init(Cipher.DECRYPT_MODE,secKey);
//        byte[] newBytes = cipher.doFinal(Base64.getDecoder().decode(data.getBytes()));
//        return new String(newBytes);
//    }
//    private static String encryptUsingASEKey(String data, byte[] key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
//        SecretKeySpec secKey = new SecretKeySpec(key,"AES");
//        Cipher cipher = Cipher.getInstance("AES");
//        cipher.init(Cipher.ENCRYPT_MODE,secKey);
//        byte[] newBytes = cipher.doFinal(data.getBytes());
//        return Base64.getEncoder().encodeToString(newBytes);
//    }
    }
