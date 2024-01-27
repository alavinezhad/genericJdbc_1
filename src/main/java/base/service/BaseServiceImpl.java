package base.service;

import base.model.BaseEntity;
import base.repository.BaseRepository;

import java.io.Serializable;
import java.sql.SQLException;

public class BaseServiceImpl<
        ID extends Serializable,
        T extends BaseEntity<ID>,
        R extends BaseRepository<ID, T>
        > implements BaseService<ID, T> {

    protected final R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public void save(T entity) throws SQLException {
        repository.save(entity);
    }

    @Override
    public T findById(ID id) throws SQLException {
        return repository.findById(id);
    }

    @Override
    public void update(T entity) throws SQLException {
        repository.update(entity);
    }

    @Override
    public void delete(ID id) throws SQLException {
        repository.delete(id);
    }
}
