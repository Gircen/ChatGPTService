package org.chat.gpt.database.dao.repository;

import org.chat.gpt.database.entity.MessageInboxImpl;

import java.util.Optional;
import java.util.UUID;

public interface MessageRepositoryInbox extends CrudRepository<MessageInboxImpl, Long> {

    @Override
    <S extends MessageInboxImpl> S save(S entity);

    @Override
    Optional<MessageInboxImpl> findById(Long primaryKey);

    @Override
    Optional<MessageInboxImpl> findByUuid(UUID uuid);

    @Override
    Iterable<MessageInboxImpl> findAll();

    @Override
    long count();

    @Override
    boolean existsById(Long primaryKey);
}
