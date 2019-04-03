package com.balaji.rms;

import com.balaji.rms.processor.FileProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableAutoConfiguration
@EnableBatchProcessing
public class RmsBatchApplication implements CommandLineRunner {

    @Value("${rms.batch.concurrencyOutputFilePath}")
    private String concurrencyOutputFilePath;

    @Autowired
    private FileProcessor fileProcessor;

    @Autowired
    private JobLauncher launcher;


    @Qualifier("encryptionJob")
    @Autowired
    private Job encryptionJob;

    public static void main(String[] args) {
        SpringApplication.run(RmsBatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*The below line is to call the Multi threading approach to read the files and encrypt.
         *Mandatory arguments FileName, NoOfThreads
         */
        fileProcessor.processFile(args[0],Integer.valueOf(args[1]),concurrencyOutputFilePath);

        /*The below line of code is to call the spring-batch framework to read the file, encrypt and write into outputfile         *
         */
        JobParameters jobParameters = new JobParametersBuilder().
                addString("source","RMS Batch Application").
                toJobParameters();
        launcher.run(encryptionJob,jobParameters);
    }
}
