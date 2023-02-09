package org.chat.gpt.database.dao.repository;

import org.chat.gpt.database.entity.Message;

import java.util.Optional;

public class MessageRepositoryImpl implements MessageRepository{
    @Override
    public void delete(Message entity) {

    }

    @Override
    public <S extends Message> S save(S entity) {
        return null;
    }

    @Override
    public Optional<Message> findById(Long primaryKey) {
        return Optional.empty();
    }

    @Override
    public Iterable<Message> findAll() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public boolean existsById(Long primaryKey) {
        return false;
    }
}
