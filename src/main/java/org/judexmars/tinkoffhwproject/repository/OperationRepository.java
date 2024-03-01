package org.judexmars.tinkoffhwproject.repository;

import org.judexmars.tinkoffhwproject.model.Operation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends MongoRepository<Operation, String> {
    List<Operation> findAllByType(Operation.OperationType type);
}
