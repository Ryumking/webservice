package dao;

import model.Goal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoalDAO implements DAO<Goal, Long> {
    @Override
    public long create(Goal goal) {
        long goalId = 0;

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GoalDAO.SQLGoal.INSERT.QUERY)) {
            statement.setLong(1, goal.getUserId());
            statement.setString(2, goal.getTitle());
            statement.setString(3, goal.getDescription());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                goalId = resultSet.getLong("goalId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return goalId;
    }

    @Override
    public Goal readById(Long id) {
        final Goal result = new Goal();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLGoal.GET_BY_ID.QUERY)) {
            statement.setLong(1, id);
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result.setId(id);
                result.setTitle(resultSet.getString("title"));
                result.setDescription(resultSet.getString("description"));
                result.setUserId(resultSet.getLong("userId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Goal> readAll() {
       final List<Goal> result=new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLGoal.GET_ALL.QUERY)) {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Goal goal = new Goal();
                goal.setId(resultSet.getInt("id"));
                goal.setTitle(resultSet.getString("title"));
                goal.setDescription(resultSet.getString("description"));
                goal.setUserId(resultSet.getLong("userId"));
                result.add(goal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
       return result;
    }

    @Override
    public boolean update(Goal goal, Long id) {
        boolean result;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLGoal.UPDATE.QUERY)) {
            statement.setString(1, goal.getTitle());
            statement.setString(2, goal.getDescription());
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
             PreparedStatement statement = connection.prepareStatement(SQLGoal.DELETE.QUERY)) {
            statement.setLong(1, id);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    enum SQLGoal {
        INSERT("INSERT INTO goals (userId, title, description) VALUES((?),(?),(?))" +
                "RETURNING id"),
        GET_BY_ID("SELECT * FROM goals WHERE id = (?)"),
        GET_ALL("SELECT * FROM goals"),
        UPDATE("UPDATE goals SET title = (?), description = (?) WHERE id = (?)"),
        DELETE("DELETE FROM goals WHERE id = (?)");

        String QUERY;

        SQLGoal(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
