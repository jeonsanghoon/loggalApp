package com.altsoft.Framework.DataInfo;

import android.util.Base64;

import com.altsoft.Framework.Global;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class SecurityInfo {
    public enum enSecType
    {
        SHA1, SHA256
    }

    public String ConvertSha(String data)
    {
        return ConvertSha(data, enSecType.SHA256);
    }
    public String ConvertSha(String data, enSecType secType)
    {
        MessageDigest md = null; // 이 부분을 SHA-256, MD5로만 바꿔주면 된다.
        try {
            if(secType == enSecType.SHA1) md = MessageDigest.getInstance("SHA-1");
            else if(secType == enSecType.SHA256) md = MessageDigest.getInstance("SHA-256");

        } catch (NoSuchAlgorithmException e) {
            return "";
        }

        try {
            md.update(data.getBytes("UTF-8"), 0, data.length()
); // "세이프123"을 SHA-1으로 변환할 예정!
        } catch (UnsupportedEncodingException e) {
            return "";
        }
        byte bytes[] = md.digest();
        // Another way to make HEX, my previous post was only the method like your solution
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) // This is your byte[] result..
        {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
    public enum enAesType{
        AES128, AES256
    }
    public String DecryptAes(String text)
    {
        return DecryptAes( text, Global.getResourceInfo().getAesKey());
    }
    public String DecryptAes(String text, String key)
    {
        return DecryptAes( text, key, enAesType.AES256);
    }
    public String DecryptAes(String text, String key, enAesType enType)
    {
        try {
            if(Global.getValidityCheck().isEmpty(text)) return "";

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            if (enType == enAesType.AES128) {
                key = Global.getStringInfo().padLeft(key, 16,'0') ;
                byte[] keyBytes = new byte[16];
                byte[] b = key.getBytes("UTF-8");
                int len = b.length;
                if (len > keyBytes.length) len = keyBytes.length;
                System.arraycopy(b, 0, keyBytes, 0, len);
                SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
                IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
                cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

                return new String(cipher.doFinal(Base64.decode(text, 0)), "UTF-8");
            } else {
                key = Global.getStringInfo().padLeft(key, 32,'0') ;
                byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
                AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
                SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

                cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
                return new String(cipher.doFinal(Base64.decode(text, 0)), "UTF-8");
            }
        }catch(Exception ex)
        {
            return "";
        }
    }

    public String EncryptAes(String text)
    {

        return EncryptAes( text, Global.getResourceInfo().getAesKey() );
    }
    public String EncryptAes(String text, String key)
    {

        return EncryptAes( text, key, enAesType.AES256);
    }
    public  String EncryptAes(String text, String key , enAesType enType)
    {
        try {
            if(Global.getValidityCheck().isEmpty(text)) return "";
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            if (enType == enAesType.AES128) {
                key = Global.getStringInfo().padLeft(key, 16,'0') ;
                byte[] keyBytes = new byte[16];
                byte[] b = key.getBytes("UTF-8");
                int len = b.length;
                if (len > keyBytes.length) len = keyBytes.length;
                System.arraycopy(b, 0, keyBytes, 0, len);
                SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
                IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

                return Base64.encodeToString(cipher.doFinal(text.getBytes("UTF-8")), 0);
            } else {
                key = Global.getStringInfo().padLeft(key, 32,'0') ;
                byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
                byte[] textBytes = text.getBytes("UTF-8");
                SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
                cipher.init(Cipher.ENCRYPT_MODE, newKey, new IvParameterSpec(ivBytes));

                return Base64.encodeToString(cipher.doFinal(textBytes), 0);
            }
        }catch(Exception ex){
            return "";
        }
     }
}
