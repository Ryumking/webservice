package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements DAO<User, Long> {

    @Override
    public long create(User user) {
        long userId = 0;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UserDao.SQLTask.INSERT.QUERY)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return userId;
    }

    @Override
    public User readById(Long id) {
        final User result = new User();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UserDao.SQLTask.GET_BY_ID.QUERY)) {
            statement.setLong(1, id);
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result.setId(resultSet.getLong("id"));
                result.setLogin(resultSet.getString("login"));
                result.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<User> readAll() {
        List<User> result = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UserDao.SQLTask.GET_ALL.QUERY)) {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setLogin(resultSet.getString("login"));
                result.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean update(User user, Long id) {
        boolean result;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UserDao.SQLTask.UPDATE.QUERY)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setLong(3, id);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean delete(Long id) {
        boolean result;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UserDao.SQLTask.DELETE.QUERY)) {
            statement.setLong(1, id);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    enum SQLTask {
        INSERT("INSERT INTO users (login, password) VALUES((?),(?))RETURNING userId"),
        GET_BY_ID("SELECT * FROM users WHERE id = (?)"),
        GET_ALL("SELECT id, login FROM users"),
        UPDATE("UPDATE users SET login = (?), password = (?) WHERE id = (?)"),
        DELETE("DELETE  FROM users WHERE id = (?)");

        String QUERY;

        SQLTask(String QUERY) {
            this.QUERY = QUERY;
        }
    }

}
