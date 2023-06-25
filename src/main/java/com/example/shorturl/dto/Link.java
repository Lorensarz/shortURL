package com.example.shorturl.dto;

import lombok.Data;

@Data
public class Link {
    private final String originalLink;
    private final String shortLink;

}
