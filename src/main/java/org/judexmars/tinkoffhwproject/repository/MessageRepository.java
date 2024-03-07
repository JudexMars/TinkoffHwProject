package org.judexmars.tinkoffhwproject.repository;

import org.judexmars.tinkoffhwproject.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
