package com.group308.socialmedia.core.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Random;

/**
 * Created by isaozturk on 5.09.2019
 */
@Slf4j
@Service
public class PasswordService {

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUM = "0123456789";
    private static final String SPL_CHARS = "!@#$%^&*_=+-/";

    private final String key;

    private Blowfish cipher;

    public PasswordService(@Value("${blowfish.secretkey}") String key) {
        this.key = key;
        this.cipher = new Blowfish(key);
    }

    public String encryptPassword(String password) {

        if (password == null) {
            return null;
        }

        return cipher.encryptString(password);
    }

    public String decryptPassword(String password) {

        if (password == null) {
            return null;
        }

        return cipher.decryptString(password);
    }

    public char[] generatePswd(int minLen, int maxLen, int noOfCAPSAlpha, int noOfDigits, int noOfSplChars) {
        if (minLen > maxLen)
            throw new IllegalArgumentException("Min. Length > Max. Length!");
        if ((noOfCAPSAlpha + noOfDigits + noOfSplChars) > minLen)
            throw new IllegalArgumentException("Min. Length should be atleast sum of (CAPS, DIGITS, SPL CHARS) Length!");
        Random rnd = new Random();
        int len = rnd.nextInt(maxLen - minLen + 1) + minLen;
        char[] pswd = new char[len];
        int index = 0;
        for (int i = 0; i < noOfCAPSAlpha; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = ALPHA_CAPS.charAt(rnd.nextInt(ALPHA_CAPS.length()));
        }
        for (int i = 0; i < noOfDigits; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = NUM.charAt(rnd.nextInt(NUM.length()));
        }
        for (int i = 0; i < noOfSplChars; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = SPL_CHARS.charAt(rnd.nextInt(SPL_CHARS.length()));
        }
        for (int i = 0; i < len; i++) {
            if (pswd[i] == 0) {
                pswd[i] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
            }
        }
        return pswd;
    }

    private int getNextIndex(Random rnd, int len, char[] pswd) {
        int index = rnd.nextInt(len);
        while (pswd[index = rnd.nextInt(len)] != 0);
        return index;
    }

    public String digestMD5(String text) {

        try {
            byte[] mdbytes = MessageDigest.getInstance("MD5").digest(text.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }

}
