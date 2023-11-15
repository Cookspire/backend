package com.sinter.cookspire.utils;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;

@Component
public class ImageSignatureValidator {

    private static final byte[] JPEG_SIGNATURE = { (byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0 };
    private static final byte[] PNG_SIGNATURE = { (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A };

    public boolean isValigJpeg(InputStream inputStream) throws IOException{
        return isFileSignatureValid(inputStream, JPEG_SIGNATURE);
    }

    public boolean isValigPng(InputStream inputStream) throws IOException{
        return isFileSignatureValid(inputStream, PNG_SIGNATURE);
    }

    private boolean isFileSignatureValid(InputStream inputStream, byte[] expectedSignature) throws IOException{
        byte[] fileSignature= new byte[expectedSignature.length];

        int bytesRead= inputStream.read(fileSignature);

        return bytesRead == expectedSignature.length && bytesMatch(fileSignature, expectedSignature);
    }

    private static boolean bytesMatch(byte[] a, byte[]b){
        for(int i=0;i<a.length; i++){
            if(a[i] !=b[i]){
                return false;
            }
        }
        return true;
    }

    
}
