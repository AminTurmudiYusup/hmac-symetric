package org.example.hmac.secretkey;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class VerifySignature {
    public static SecretKeySpec decodeSecretKey(String key) {
        byte[] byteSecretKey = Base64.getDecoder().decode(key);
        SecretKeySpec secretKeySpec = new SecretKeySpec(byteSecretKey, "HmacSHA512");
        return secretKeySpec;
    }
    public static byte[] createSignature(SecretKeySpec secretKeySpec, String message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA512"); // get access to Mac object which implements HmacSHA512 algorithm.
        mac.init(secretKeySpec); // Initialize Mac object with symmetric key(K), same as with sender
        return mac.doFinal(message.getBytes());
    }

    public static String hex(byte[] bytes) {
        char[] result = Hex.encodeHex(bytes);
        return new String(result);
    }
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
    String secretKey ="dmFsdWU=";
    SecretKeySpec secretKeySpec = decodeSecretKey(secretKey);
    String signature ="c00c6e2eba824256b808ede5f567e3de226937cb7a378c8046e44e41b07d02c2aba6396779ad00571d7e6183e2e781125ac7cb9708f3864a2dd60865cb06e061";
    String messageToCompare= "didi";
    byte[] byteSignature = createSignature(secretKeySpec, messageToCompare);
    String hexSignature = hex(byteSignature);
    System.out.println("Is same ?? "+signature.equals(hexSignature));
    }
}
