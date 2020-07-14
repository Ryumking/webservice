package service;

import dao.UserDao;
import model.User;

import java.util.List;

public class UserService {
    final UserDao userDao = new UserDao();

    public Long createUser(User user) {
        return userDao.create(user);
    }

    public boolean updateUser(User user, Long id) {
        return userDao.update(user, id);
    }

    public boolean delete(Long id) {
        return userDao.delete(id);
    }

    public User findUserById(Long id) {
        return userDao.readById(id);
    }

    public List<User> findAllUsers() {
        return userDao.readAll();
    }
}
