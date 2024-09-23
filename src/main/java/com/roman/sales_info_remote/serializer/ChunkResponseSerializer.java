package com.roman.sales_info_remote.serializer;

import org.apache.kafka.common.serialization.Serializer;
import org.springframework.batch.integration.chunk.ChunkResponse;
import org.springframework.util.SerializationUtils;

public class ChunkResponseSerializer implements Serializer<ChunkResponse> {
    @Override
    public byte[] serialize(String s, ChunkResponse chunkResponse) {
        if(chunkResponse == null)
            return new byte[0];

        return SerializationUtils.serialize(chunkResponse);
    }
}
