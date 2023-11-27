package com.sinter.cookspire.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.sinter.cookspire.exception.ApplicationException;

@Component
public class ImageSignatureValidator {

    Logger logger = LoggerFactory.getLogger(ImageSignatureValidator.class);

    @Autowired
    MessageSource msgSrc;

    private static final byte[] JPEG_SIGNATURE = { (byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0 };
    private static final byte[] PNG_SIGNATURE = { (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A };

    public static boolean isValigJpeg(InputStream inputStream) throws IOException {
        return isFileSignatureValid(inputStream, JPEG_SIGNATURE);
    }

    public static boolean isValigPng(InputStream inputStream) throws IOException {
        return isFileSignatureValid(inputStream, PNG_SIGNATURE);
    }

    private static boolean isFileSignatureValid(InputStream inputStream, byte[] expectedSignature) throws IOException {
        byte[] fileSignature = new byte[expectedSignature.length];

        int bytesRead = inputStream.read(fileSignature);

        return bytesRead == expectedSignature.length && bytesMatch(fileSignature, expectedSignature);
    }

    private static boolean bytesMatch(byte[] a, byte[] b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean processImageFormat(InputStream fileInputStream) {
        boolean response = false;
        try {
            if ((ImageSignatureValidator.isValigJpeg(fileInputStream) ||
                    ImageSignatureValidator.isValigPng(fileInputStream))) {

                response = true;

            } else {
                logger.error("Invalid Signature for profile picture.");
                logger.error("Exiting from upload profile picture logic.");
                throw new ApplicationException(msgSrc.getMessage("User.Error", null, Locale.ENGLISH),
                        HttpStatus.BAD_REQUEST);
            }
        } catch (NoSuchMessageException | IOException e) {
            logger.error("Invalid Signature for profile picture.");
            logger.error("Exiting from upload profile picture logic.");
            throw new ApplicationException(msgSrc.getMessage("User.Error", null, Locale.ENGLISH),
                    HttpStatus.BAD_REQUEST);
        }

        return response;
    }

}
