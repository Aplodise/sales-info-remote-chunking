package com.roman.sales_info_remote.serializer.chunkRequest;

import com.roman.sales_info_remote.dto.SalesInfoDTO;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.batch.integration.chunk.ChunkRequest;
import org.springframework.util.SerializationUtils;

public class ChunkRequestSerializer implements Serializer<ChunkRequest<SalesInfoDTO>> {
    @Override
    public byte[] serialize(String s, ChunkRequest<SalesInfoDTO> chunkRequest) {
        if(chunkRequest == null)
            return new byte[0];

        return SerializationUtils.serialize(chunkRequest);
    }
}
