package repository.user;

import base.repository.BaseRepository;
import model.user.User;

import java.sql.SQLException;

public interface UserRepository extends BaseRepository<Integer, User> {
    User findByUsername(String username) throws SQLException;
}
