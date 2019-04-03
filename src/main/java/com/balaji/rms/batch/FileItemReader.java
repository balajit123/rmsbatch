package com.balaji.rms.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FileItemReader implements ItemReader<String> {

    Logger logger = LoggerFactory.getLogger(FileItemReader.class);
    private String filePath;

    public FileItemReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        StringBuilder sb = new StringBuilder();
        Path path = Paths.get(filePath);

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(new Consumer<String>() {
                @Override
                public void accept(String line) {
                    sb.append(line);
                }
            });
        }
        return sb.toString();
    }
}
