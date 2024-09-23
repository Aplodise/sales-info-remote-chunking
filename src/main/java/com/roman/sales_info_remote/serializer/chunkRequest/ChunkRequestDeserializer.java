package com.roman.sales_info_remote.serializer.chunkRequest;

import com.roman.sales_info_remote.dto.SalesInfoDTO;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.batch.integration.chunk.ChunkRequest;
import org.springframework.util.SerializationUtils;

public class ChunkRequestDeserializer implements Deserializer<ChunkRequest<SalesInfoDTO>> {
    @Override
    public ChunkRequest<SalesInfoDTO> deserialize(String s, byte[] bytes) {
        if(bytes == null)
            return null;

        return (ChunkRequest<SalesInfoDTO>) SerializationUtils.deserialize(bytes);
    }
}
