package org.judexmars.tinkoffhwproject.repository;

import org.judexmars.tinkoffhwproject.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    boolean existsImageByIdIn(List<Long> ids);

    List<Image> findAllByIdIn(List<Long> ids);

    boolean existsImageByLink(String link);
}
