package com.balaji.rms.batch;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileItemWriter implements ItemWriter<String> {

    private String outpuFilePath;

    public FileItemWriter(String outpuFilePath) {
        this.outpuFilePath = outpuFilePath;
    }

    @Override
    public void write(List<? extends String> list) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append((String)list.get(i));
        }
        //Write the encrypted content to file.
        Path outputFile = Paths.get(outpuFilePath);
        try(BufferedWriter writer = Files.newBufferedWriter(outputFile)){
            writer.write(sb.toString());
        }
    }
}
