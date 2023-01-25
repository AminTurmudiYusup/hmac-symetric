package org.example.hmac.secretkey;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class CreateSecretKeyAndSignature {

    public static String createSecretKey(String key) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA512");
        return Base64.getEncoder().encodeToString(secretKeySpec.getEncoded());
    }

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
        String secretKey = createSecretKey("value");
        System.out.println("Secret Key >> "+secretKey);
        SecretKeySpec secretKeySpec = decodeSecretKey(secretKey);
        System.out.println("Insert message to create signature");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        byte[] byteSignature = createSignature(secretKeySpec, input);
        System.out.println(hex(byteSignature));


        //decode secret key base 64 into SecretKey
        String secretKeyBaru="ojw8cDqeF44UurgcyWt96yyh24jytvki";
        SecretKeySpec secretKeySpec1 = decodeSecretKey(secretKeyBaru);
        System.out.println(""+secretKeySpec1);
        secretKeyBaru=Base64.getEncoder().encodeToString(secretKeySpec1.getEncoded());
        System.out.println("secretKeyBaru>>"+secretKeyBaru);
    }

}