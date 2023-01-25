package org.example.hmac;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static String hmacWithJava(String algorithm, String data, String key)
            throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(secretKeySpec);
        return bytesToHex(mac.doFinal(data.getBytes()));
    }

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte h : hash) {
            String hex = Integer.toHexString(0xff & h);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    public static void main( String[] args ) throws NoSuchAlgorithmException, InvalidKeyException {
        String valueToDigest = "The quick brown fox jumps over the lazy dog";
        String key = "secret";
        String algorithm ="HmacSHA512";

        App hm = new App();
        String messageDigest = hm.hmacWithJava(algorithm, valueToDigest, key);
        System.out.println(messageDigest);
    }
}
