package com.balaji.rms.batch;

import com.balaji.rms.processor.CaesarEncryption;
import com.balaji.rms.processor.FileProcessor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class JobProcessor implements ItemProcessor {

    @Autowired
    FileProcessor fileProcessor;

    @Value("${rms.batch.outputfilepath}")
    String outputFilePath;

    @Override
    public Object process(Object o) throws Exception {
        String encryptedString = new CaesarEncryption().encrypt((String) o, 2);
        return encryptedString;
    }
}