package com.example.shorturl.service;

import com.example.shorturl.dto.Link;
import com.example.shorturl.exceptions.notFoundException;
import com.example.shorturl.model.LinkEntity;
import com.example.shorturl.repository.LinkRepository;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class LinkServiceImpl implements LinkService {

    private static final int HASH_LENGTH = 6;
    private static final String[] ALLOWED_SCHEMES = {"http", "https"};

    private final LinkRepository linkRepository;


    public LinkServiceImpl(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    @Transactional
    public String shortenLink(String originalLink) {
        log.info("Original Link: {}", originalLink);
        String domain = extractDomain(originalLink);
        String hash = generateHash(originalLink);

        String shortLink = domain + "/" + hash;

        LinkEntity linkEntity = new LinkEntity(originalLink, shortLink);

        linkRepository.save(linkEntity);

        log.info("Short Link: {}", shortLink);

        return shortLink;
    }


    private String extractDomain(String originalLink) {
        UrlValidator urlValidator = new UrlValidator(ALLOWED_SCHEMES);
        if (urlValidator.isValid(originalLink)) {
            try {
                URL url = new URL(originalLink);
                String protocol = url.getProtocol();
                String domain = url.getHost();

                return protocol + "://" + domain;
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Недопустимый URL");
            }
        }
        throw new IllegalArgumentException("Недопустимый URL");
    }


    @Override
    public String getOriginalLink(String shortLink) {
        log.info("Short Link: {}", shortLink);
        Link link = linkRepository.findByShortLink(shortLink);
        if (link != null) {
            log.info("Original Link: {}", link.getOriginalLink());
            return link.getOriginalLink();
        } else {
            throw new notFoundException("Ссылка не найдена");
        }
    }

    private String generateHash(String originalLink) {
        String hash = Hashing.sha256().hashString(originalLink, StandardCharsets.UTF_8).toString();
        return hash.substring(0, HASH_LENGTH);

    }
}
