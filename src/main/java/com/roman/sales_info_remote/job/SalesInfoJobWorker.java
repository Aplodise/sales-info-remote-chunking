package com.roman.sales_info_remote.job;


import com.roman.sales_info_remote.dto.SalesInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.integration.chunk.RemoteChunkingWorkerBuilder;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Profile("worker")
@Configuration
@EnableBatchIntegration
@RequiredArgsConstructor
@Slf4j
@EnableBatchProcessing
public class SalesInfoJobWorker {

    private final RemoteChunkingWorkerBuilder<SalesInfoDTO, SalesInfoDTO> remoteChunkingWorkerBuilder;
    private final KafkaTemplate<String, SalesInfoDTO> kafkaTemplate;

    @Bean
    public IntegrationFlow salesWorker(){
        return this.remoteChunkingWorkerBuilder
                .inputChannel(inboundChannel())
                .itemProcessor(salesInfo -> {
                    log.info("processing item: {}", salesInfo);
                    return salesInfo;
                })
                .itemWriter(items -> log.info("item writing: {}", items))
                .outputChannel(outboundChannel())
                .build();
    }

    @Bean
    public QueueChannel inboundChannel(){
        return new QueueChannel();
    }
    @Bean
    public IntegrationFlow inboundFlow(ConsumerFactory<String, SalesInfoDTO> consumerFactory){
        return IntegrationFlow.from(Kafka.messageDrivenChannelAdapter(consumerFactory, "sales-chunkRequests"))
                .log(LoggingHandler.Level.WARN)
                .channel(inboundChannel())
                .get();
    }
    @Bean
    public DirectChannel outboundChannel(){
        return new DirectChannel();
    }
    @Bean
    public IntegrationFlow outboundFlow(){
        var producerMessageHandler = new KafkaProducerMessageHandler<String, SalesInfoDTO>(kafkaTemplate);
        producerMessageHandler.setTopicExpression(new LiteralExpression("sales-chunkReplies"));
        return IntegrationFlow.from(outboundChannel())
                .log(LoggingHandler.Level.WARN)
                .handle(producerMessageHandler)
                .get();
    }
}
