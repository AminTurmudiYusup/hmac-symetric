package org.example.hmac.secretkey;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ImportSecretKeyandCreateSignature {

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
    public static void main(String[] args) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeyException {
        String httpMethod="GET";
        String realtiveUrl="/switching/rest/access/detail";
        String b2BAccessToken ="12utfhkry345850";
        String requestBody="";
        String xTimeStamp="2021-11-02T13:14:15+07:00";
        String secretKey="ojw8cDqeF44UurgcyWt96yyh24jytvki";
        if(!requestBody.isEmpty()){
            ObjectMapper objectMapper= new ObjectMapper();
            JsonNode jsonNode= objectMapper.readValue(requestBody, JsonNode.class);
            requestBody= jsonNode.toString();
            requestBody= DigestUtils.sha256Hex(requestBody).toLowerCase();
        }else{
            requestBody="";
            requestBody= DigestUtils.sha256Hex(requestBody).toLowerCase();
        }
        String finalString = httpMethod.concat(":").concat(realtiveUrl).concat(":").concat(b2BAccessToken).concat(":").concat(requestBody).concat(":").concat(xTimeStamp);
       System.out.println(finalString);
        SecretKeySpec secretKeySpec=decodeSecretKey(secretKey);
        byte[] signature=createSignature(secretKeySpec, finalString);
        System.out.println(hex(signature));

    }
}
