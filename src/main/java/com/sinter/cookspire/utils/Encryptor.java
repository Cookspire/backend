package com.sinter.cookspire.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.sinter.cookspire.dto.EncryptorDTO;

public class Encryptor {

    public static EncryptorDTO encryptor(String text) {

        EncryptorDTO response=new EncryptorDTO();
        
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] salt=generateSalt();
            md.update(salt);
            byte[] bytes=md.digest(text.getBytes());
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<bytes.length;i++){
                sb.append(Integer.toString((bytes[i] &(0xff))+0x100, 16).substring(1));
            }
            response.setHashText(sb.toString());
            response.setSalt(salt.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return response;

    }

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

}
