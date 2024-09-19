package com.roman.sales_info_remote.job;

import com.roman.sales_info_remote.dto.SalesInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.integration.chunk.RemoteChunkingManagerStepBuilderFactory;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchIntegration
@RequiredArgsConstructor
public class SalesInfoJobManager {

    private final RemoteChunkingManagerStepBuilderFactory remoteChunkingManagerStepBuilderFactory;

    @Bean
    public Job salesManagerJob(JobRepository jobRepository, Step salesInfoStepManager){
        return new JobBuilder("sales-info-manager-job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(salesInfoStepManager)
                .build();
    }

    // TODO outbound channel for remote chunking
    @Bean
    public TaskletStep salesInfoStepManager(){
        return this.remoteChunkingManagerStepBuilderFactory.get("Reader-Manager-Step")
                .<SalesInfoDTO, SalesInfoDTO>chunk(10)
                .reader(salesInfoReader())
                .build();
    }

    public FlatFileItemReader<SalesInfoDTO> salesInfoReader(){
        return new FlatFileItemReaderBuilder<SalesInfoDTO>()
                .resource(new ClassPathResource("/data/bad_records.csv"))
                .name("sales info reader")
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .names("product", "seller", "sellerId", "price", "city", "category")
                .targetType(SalesInfoDTO.class)
                .build();
    }
}
