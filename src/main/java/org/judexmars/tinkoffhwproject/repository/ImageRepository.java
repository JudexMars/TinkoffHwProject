package org.judexmars.tinkoffhwproject.repository;

import org.judexmars.tinkoffhwproject.model.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    boolean existsImageByIdIn(List<Long> ids);

    List<ImageEntity> findAllByIdIn(List<Long> ids);

    boolean existsImageByLink(String link);
}
