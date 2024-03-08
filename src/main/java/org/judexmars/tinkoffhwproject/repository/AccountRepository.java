package org.judexmars.tinkoffhwproject.repository;

import org.judexmars.tinkoffhwproject.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {


    Optional<AccountEntity> findByUsername(String username);
}
