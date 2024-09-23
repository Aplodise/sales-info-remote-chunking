package com.roman.sales_info_remote.dto;

import java.io.Serializable;

public record SalesInfoDTO(String product,
                           String seller,
                           Long sellerId,
                           Double price,
                           String city,
                           String category) implements Serializable {
}
