package dao;

import model.User;

import java.util.List;

public class UserDao implements DAO<User,Integer>{
    @Override
    public boolean create(User user) {
        return false;
    }

    @Override
    public User readById(Integer integer) {
        return null;
    }

    @Override
    public List<User> readAll() {
        return null;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    enum SQLTask {
        INSERT("INSERT INTO users (login, password) VALUES((?),(?))"),
        GET_BY_ID("SELECT * FROM users WHERE id = (?)"),
        GET_ALL("SELECT * FROM users"),
        UPDATE("UPDATE users SET login = (?), password = (?) WHERE id = (?)"),
        DELETE("DELETE  FROM users WHERE id = (?)");

        String QUERY;

        SQLTask(String QUERY) {
            this.QUERY = QUERY;
        }
    }

}
