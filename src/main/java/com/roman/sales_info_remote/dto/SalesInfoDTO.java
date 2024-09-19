package com.roman.sales_info_remote.dto;

public record SalesInfoDTO(String product,
                           String seller,
                           Long sellerId,
                           Double price,
                           String city,
                           String category) {
}
