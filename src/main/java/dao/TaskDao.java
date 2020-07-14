package dao;

import model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDao implements DAO<Task, Long> {

    public TaskDao() {
    }

    @Override
    public long create(Task task) {
        long taskId = 0;

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLTask.INSERT.QUERY)) {
            statement.setLong(1, task.getUserId());
            statement.setString(2, task.getTitle());
            statement.setString(3, task.getDescription());
            statement.setDate(4, task.getDate());
            statement.setBoolean(5, task.isDone());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                taskId = resultSet.getLong("taskId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return taskId;
    }

    @Override
    public Task readById(Long id) {
        final Task result = new Task();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLTask.GET_BY_ID.QUERY)) {
            statement.setLong(1, id);
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result.setId(id);
                result.setTitle(resultSet.getString("title"));
                result.setDescription(resultSet.getString("description"));
                result.setDate(resultSet.getDate("date"));
                result.setDone(resultSet.getBoolean("done"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Task> readAll() {
        final List<Task> result = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
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
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean update(Task task, Long id) {
        boolean result;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLTask.UPDATE.QUERY)) {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setDate(3, task.getDate());
            statement.setBoolean(4, task.isDone());
            statement.setLong(5, id);
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
             PreparedStatement statement = connection.prepareStatement(SQLTask.DELETE.QUERY)) {
            statement.setLong(1, id);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    enum SQLTask {
        INSERT("INSERT INTO tasks (userId, title, description,date,isDone) VALUES((?),(?),(?),(?),(?))" +
                "RETURNING id"),
        GET_BY_ID("SELECT * FROM tasks WHERE id = (?)"),
        GET_ALL("SELECT * FROM tasks"),
        UPDATE("UPDATE tasks SET title = (?), description = (?), date = (?), is_done = (?) WHERE id = (?)"),
        DELETE("DELETE FROM tasks WHERE id = (?)");

        String QUERY;

        SQLTask(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
