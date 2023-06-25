package com.example.shorturl;

import com.example.shorturl.model.LinkEntity;
import com.example.shorturl.repository.LinkRepository;
import com.example.shorturl.service.LinkServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LinkServiceImplTest {

    @Mock
    private LinkRepository linkRepository;

    @InjectMocks
    private LinkServiceImpl linkService;

    @Test
    public void testShortenLink() {
        linkService = new LinkServiceImpl(linkRepository);

        String originalLink = "http://www.example.com";

        String expectedShortLink = "http://www.example.com/2108db";

        when(linkRepository.save(Mockito.any(LinkEntity.class))).thenReturn(null);

        String actualShortLink = linkService.shortenLink(originalLink);

        assertEquals(expectedShortLink, actualShortLink);
    }
}

