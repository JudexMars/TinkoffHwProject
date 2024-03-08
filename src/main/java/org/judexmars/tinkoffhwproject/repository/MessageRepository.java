package org.judexmars.tinkoffhwproject.repository;

import org.judexmars.tinkoffhwproject.model.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
}
