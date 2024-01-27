package repository.user;

import base.repository.BaseRepositoryImpl;
import model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryImpl extends BaseRepositoryImpl<Integer, User> implements UserRepository{
    public UserRepositoryImpl(Connection connection) {
        super(connection);
    }

    @Override
    public String getTableName() {
        return "users";
    }

    @Override
    public String getFields() {
        return "(firstname, lastname, username, password";
    }

    @Override
    public String getQuestionMarks() {
        return "(?, ?, ?, ?";
    }

    @Override
    public void setFields(PreparedStatement ps, User entity, boolean isCountOnly) throws SQLException {
        ps.setString(1, entity.getFirstname());
        ps.setString(2, entity.getLastname());
        ps.setString(3, entity.getUsername());
        ps.setString(4, entity.getPassword());
    }

    @Override
    public User mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String firstname = resultSet.getString(2);
        String lastname = resultSet.getString(3);
        String username = resultSet.getString(4);
        String password = resultSet.getString(5);
        return new User(id, firstname, lastname, username, password);
    }

    @Override
    public String getUpdateFields() {
        return "(firstname = ?, lastname = ?, username = ?, password = ?";
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                return mapResultSetToEntity(resultSet);
        }
        return null;
    }
}
