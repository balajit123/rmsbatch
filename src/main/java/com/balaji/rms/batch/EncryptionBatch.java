package com.balaji.rms.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class EncryptionBatch extends JobExecutionListenerSupport {

    Logger logger = LoggerFactory.getLogger(EncryptionBatch.class);

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobProcessor processor;

    @Value("${rms.batch.inputFilepath}")
    String inputFilePath;

    @Value("${rms.batch.outputfilepath}")
    String outpuFilePath;

    @Bean(name="encryptionJob")
    public Job fileEncryptionJob(){

        FileItemReader itemReader = new FileItemReader(inputFilePath);

        ItemWriter itemWriter  = new FileItemWriter(outpuFilePath);

        Step step =stepBuilderFactory.get("step1").chunk(1).reader(itemReader).processor(processor).writer(itemWriter).build();
        Job job = jobBuilderFactory.get("encryptionJob")
                .listener(this)
                .start(step)
                .build();
        return job;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("BATCH JOB COMPLETED SUCCESSFULLY");
            jobExecution.stop();
        }
    }
}
