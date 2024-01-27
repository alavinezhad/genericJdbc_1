package base.repository;

import base.model.BaseEntity;

import java.io.Serializable;
import java.sql.SQLException;

public interface BaseRepository<ID extends Serializable, T extends BaseEntity<ID>> {
    void save(T entity) throws SQLException;
    T findById(ID id) throws SQLException;
    void update(T entity) throws SQLException;
    void delete(ID id) throws SQLException;
}
