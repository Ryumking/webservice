package dao;

import model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDao implements DAO<Task, Integer> {

    public TaskDao() {
    }

    @Override
    public boolean create(Task task) {
        boolean result = false;

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLTask.INSERT.QUERY)) {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setDate(3, task.getDate());
            statement.setBoolean(4, task.isDone());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Task readById(Integer integer) {
        final Task result = new Task();
        result.setId(-1);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLTask.GET_BY_ID.QUERY)) {
            statement.setInt(1, integer);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.setId(integer);
                result.setTitle(resultSet.getString("title"));
                result.setDescription(resultSet.getString("description"));
                result.setDate(resultSet.getDate("date"));
                result.setDone(resultSet.getBoolean("done"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Task> readAll() {
        final List<Task> result = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLTask.GET_ALL.QUERY)) {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setTitle(resultSet.getString("title"));
                task.setDescription(resultSet.getString("description"));
                task.setDate(resultSet.getDate("date"));
                task.setDone(resultSet.getBoolean("done"));
                result.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Task task) {
        boolean result = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLTask.UPDATE.QUERY)) {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setDate(3, task.getDate());
            statement.setBoolean(4, task.isDone());
            statement.setInt(5, task.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(Integer id) {
        boolean result = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLTask.DELETE.QUERY)) {
            statement.setInt(1, id);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    enum SQLTask {
        INSERT("INSERT INTO tasks (title, description,date,is_done) VALUES((?),(?),(?),(?))"),
        GET_BY_ID("SELECT * FROM tasks WHERE id = (?)"),
        GET_ALL("SELECT * FROM tasks"),
        UPDATE("UPDATE tasks SET title = (?), description = (?), date = (?), is_done = (?) WHERE id = (?)"),
        DELETE("DELETE  FROM tasks WHERE id = (?)");

        String QUERY;

        SQLTask(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
