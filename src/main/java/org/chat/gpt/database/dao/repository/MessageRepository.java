package org.chat.gpt.database.dao.repository;

import org.chat.gpt.database.entity.Message;

import java.util.Optional;

public interface MessageRepository extends CrudRepository<Message, Long> {

    @Override
    <S extends Message> S save(S entity);

    @Override
    Optional<Message> findById(Long primaryKey);

    @Override
    Iterable<Message> findAll();

    @Override
    long count();

    @Override
    boolean existsById(Long primaryKey);
}
