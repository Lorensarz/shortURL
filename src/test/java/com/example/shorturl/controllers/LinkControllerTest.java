package com.example.shorturl.controllers;


import com.example.shorturl.dto.Link;
import com.example.shorturl.service.LinkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LinkController.class)
public class LinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LinkService linkService;

    @Test
    public void createShortenLink_ShouldReturnShortLink() throws Exception {
        // Arrange
        String originalLink = "http://www.example.com";
        String shortLink = "http://localhost:8080/abc123";
        given(linkService.shortenLink(anyString())).willReturn(shortLink);

        // Act and Assert
        mockMvc.perform(post("/api/links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"originalLink\":\"" + originalLink + "\"}"))
                 .andExpect(status().isOk())
                .andExpect(content().string(shortLink));
    }

    @Test
    public void redirectToOriginLink_WithValidShortLink_ShouldRedirectToOriginalLink() throws Exception {
        // Arrange
        String shortLink = "abc123";
        String originalLink = "http://www.example.com";
        given(linkService.getOriginalLink(shortLink)).willReturn(originalLink);

        // Act and Assert
        mockMvc.perform(get("/api/links/{shortenLink}", shortLink))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", originalLink));
    }

    @Test
    public void redirectToOriginLink_WithInvalidShortLink_ShouldReturnNotFound() throws Exception {
        // Arrange
        String shortLink = "invalid";
        given(linkService.getOriginalLink(shortLink)).willReturn(null);

        // Act and Assert
        mockMvc.perform(get("/api/links/{shortenLink}", shortLink))
                .andExpect(status().isNotFound());
    }
}
