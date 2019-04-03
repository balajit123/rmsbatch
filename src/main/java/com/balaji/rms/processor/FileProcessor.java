package com.balaji.rms.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Component
public class FileProcessor {

    Logger logger = LoggerFactory.getLogger(FileProcessor.class);

    public void processFile(String inputFilePath,int noOfThreads, String outputFilePath) throws IOException {

        Path outputFile = Paths.get(outputFilePath);
        StringBuilder sb = new StringBuilder();

        //Create the threads
        ExecutorService executor = Executors.newFixedThreadPool(noOfThreads);
        Path path = Paths.get(inputFilePath);
        try(Stream<String> lines = Files.lines(path)){
            lines.forEach(new Consumer<String>() {
                @Override
                public void accept(String line){
                    //Submit each line to the thread to encrypt
                    Future<String> result = executor.submit(new Encryptor(line));
                    try {
                        String encryptedLine = result.get();
                        sb.append(encryptedLine);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage());
                    } catch (ExecutionException e) {
                        logger.error(e.getMessage());
                    }
                }
            });

        }finally {
            executor.shutdown();
        }
        //Write the encrypted content to file.
        try(BufferedWriter writer = Files.newBufferedWriter(outputFile)){
            writer.write(sb.toString());
        }
        logger.info("Encrypted file generated successfuly");
    }
}
