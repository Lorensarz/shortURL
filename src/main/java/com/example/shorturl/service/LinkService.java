package com.example.shorturl.service;

public interface LinkService {
    String shortenLink(String originalLink);
    String getOriginalLink(String shortLink);
}
