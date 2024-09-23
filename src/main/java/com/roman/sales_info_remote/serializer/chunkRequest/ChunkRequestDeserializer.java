package com.roman.sales_info_remote.serializer.chunkRequest;

import com.roman.sales_info_remote.dto.SalesInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.batch.integration.chunk.ChunkRequest;
import org.springframework.util.SerializationUtils;

@Slf4j
public class ChunkRequestDeserializer implements Deserializer<ChunkRequest<SalesInfoDTO>> {
    @Override
    public ChunkRequest<SalesInfoDTO> deserialize(String s, byte[] bytes) {
        log.info("-----------------> Deserializing chunk request");
        if(bytes == null)
            return null;

        return (ChunkRequest) SerializationUtils.deserialize(bytes);
    }
}
