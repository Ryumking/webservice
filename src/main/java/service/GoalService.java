package service;

import dao.GoalDAO;
import model.Goal;
import model.Task;

import java.util.List;

public class GoalService {
    final GoalDAO goalDAO = new GoalDAO();

    public Long createGoal(Goal goal, Long id) {
        return goalDAO.create(goal);
    }

    public boolean updateGoal(Goal goal, Long id) {
        return goalDAO.update(goal, id);
    }

    public boolean delete(Long id) {
        return goalDAO.delete(id);
    }

    public Goal findGoalById(Long id) {
        return goalDAO.readById(id);
    }

    public List<Goal> findAllGoals() {
        return goalDAO.readAll();
    }
}
