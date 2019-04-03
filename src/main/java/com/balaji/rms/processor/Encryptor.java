package com.balaji.rms.processor;

import org.springframework.util.StringUtils;

import java.util.concurrent.Callable;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

public class Encryptor implements Callable<String> {

    private String contentToEncrypt;

    public Encryptor(String contentToEncrypt) {
        this.contentToEncrypt = contentToEncrypt;
    }

    @Override
    public String call() {
        return new CaesarEncryption().encrypt(this.contentToEncrypt, 2);
    }



}
