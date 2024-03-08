package org.judexmars.tinkoffhwproject.repository;

import org.judexmars.tinkoffhwproject.model.OperationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends MongoRepository<OperationEntity, String> {
    List<OperationEntity> findAllByType(OperationEntity.OperationType type);
}
