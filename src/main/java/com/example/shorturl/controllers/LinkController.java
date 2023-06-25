package com.example.shorturl.controllers;

import com.example.shorturl.dto.Link;
import com.example.shorturl.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/links")
@Slf4j
public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping
    public ResponseEntity<String> createShortenLink(@RequestBody Link link) {
        log.info("Received Link object: {}", link);
        String createLink = linkService.shortenLink(link.getOriginalLink());
        return ResponseEntity.ok(createLink);
    }

    @GetMapping("/{shortenLink}")
    public ResponseEntity<String> redirectToOriginLink(@PathVariable String shortenLink) {
        String originalLink = linkService.getOriginalLink(shortenLink);
        if (originalLink != null) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, originalLink)
                    .build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
