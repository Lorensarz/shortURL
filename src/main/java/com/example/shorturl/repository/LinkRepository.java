package com.example.shorturl.repository;

import com.example.shorturl.dto.Link;
import com.example.shorturl.model.LinkEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<LinkEntity, Long> {

    <S extends LinkEntity> S save(@NonNull S linkEntity);
    Link findByShortLink(String shortLink);
}
