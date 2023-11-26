package com.sinter.cookspire.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
public class Decryptor {

    Logger logger=LoggerFactory.getLogger(getClass());
    
    public boolean isSame(String hashText,String text, byte[] salt){
        logger.info("Entering decrypt function");
        boolean response= false;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes=md.digest(text.getBytes());
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<bytes.length;i++){
                sb.append(Integer.toString((bytes[i] &(0xff))+0x100, 16).substring(1));
            }
            System.out.println("Salt:"+salt);
            System.out.println("Hashin DB:"+hashText);
            System.out.println("Hashed pwd:"+sb.toString());
            if(sb.toString().equals(hashText)){
                response= true;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("Exception during text decrypt.");
        }
        return response;

    }
}
