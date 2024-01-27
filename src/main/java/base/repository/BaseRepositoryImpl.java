package base.repository;

import base.model.BaseEntity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseRepositoryImpl<ID extends Serializable,
        T extends BaseEntity<ID>> implements BaseRepository<ID, T> {
    protected final Connection connection;

    public BaseRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(T entity) throws SQLException {
        // todo for example: INSERT INTO users (firstname, lastname, username, password) VALUES (?, ?, ?, ?)
        String sql = "INSERT INTO " +
                getTableName() + " " +
                getFields() + " VALUES " +
                getQuestionMarks();
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            setFields(ps, entity, false); // todo I don't know what is the 3rd parameter
            ps.executeUpdate();
        }
    }

    @Override
    public T findById(ID id) throws SQLException {
        // todo for example: SELECT * FROM users WHERE id = ?
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);){
            ps.setInt(1, (Integer) id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToEntity(resultSet);
            }
        }
        return null;
    }

    @Override
    public void update(T entity) throws SQLException {
        // todo for example: UPDATE users SET firstname = ?, lastname = ?, username = ?, password = ? WHERE id = ?
        String sql = "UPDATE " + getTableName() + " SET " + getUpdateFields() + " WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            setFields(ps, entity, true);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(ID id) throws SQLException {
        // todo for example: DELETE FROM users WHERE id = ?
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);){
            ps.setInt(1, (Integer) id);
            ps.executeUpdate();
        }
    }
    public abstract String getTableName();

    public abstract String getFields();

    public abstract String getQuestionMarks();
    public abstract void setFields(PreparedStatement ps, T entity, boolean isCountOnly) throws SQLException;
    public abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;
    public abstract String getUpdateFields();

}
