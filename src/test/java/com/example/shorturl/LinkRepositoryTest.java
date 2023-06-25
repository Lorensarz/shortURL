package com.example.shorturl;

import com.example.shorturl.dto.Link;
import com.example.shorturl.model.LinkEntity;
import com.example.shorturl.repository.LinkRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class LinkRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LinkRepository linkRepository;

    @Test
    public void testSaveLinkEntity() {
        // Создаем объект LinkEntity
        LinkEntity linkEntity = new LinkEntity("http://www.example.com", "abc123");

        // Сохраняем объект в базу данных
        linkRepository.save(linkEntity);

        // Извлекаем объект из базы данных по идентификатору
        LinkEntity savedLinkEntity = entityManager.find(LinkEntity.class, linkEntity.getId());

        // Проверяем, что сохраненный объект совпадает с исходным объектом
        Assertions.assertNotNull(savedLinkEntity);
        Assertions.assertEquals(linkEntity.getOriginalLink(), savedLinkEntity.getOriginalLink());
        Assertions.assertEquals(linkEntity.getShortLink(), savedLinkEntity.getShortLink());
    }

    @Test
    public void testFindByShortLink() {
        // Создаем объект LinkEntity
        LinkEntity linkEntity = new LinkEntity("http://www.example.com", "abc123");

        // Сохраняем объект в базу данных
        linkRepository.save(linkEntity);

        // Ищем объект по короткой ссылке
        Link foundLinkEntity = linkRepository.findByShortLink(linkEntity.getShortLink());

        // Проверяем, что найденный объект совпадает с исходным объектом
        Assertions.assertNotNull(foundLinkEntity);
        Assertions.assertEquals(linkEntity.getOriginalLink(), foundLinkEntity.getOriginalLink());
        Assertions.assertEquals(linkEntity.getShortLink(), foundLinkEntity.getShortLink());
    }
}

