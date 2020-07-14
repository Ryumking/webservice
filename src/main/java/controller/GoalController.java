package controller;

import model.Goal;
import service.GoalService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static javax.servlet.http.HttpServletResponse.*;

public class GoalController extends HttpServlet {
    private final ControllerHelper<Goal> goalControllerHelper = new ControllerHelper<>(Goal.class);
    private final GoalService goalService = new GoalService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        final long goalId = goalControllerHelper.getIdFromPath(req);
        if (goalId == 0L) {
            goalControllerHelper.writeToJson(resp, goalService.findAllGoals());
            return;
        }
        goalControllerHelper.writeToJson(resp, goalService.findGoalById(goalId));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Goal goal = goalControllerHelper.writeToObject(req);
        final long userId = Long.parseLong(req.getParameter("userId"));
        final long goalId = goalService.createGoal(goal, userId);
        if (goalId == 0) {
            resp.setContentType("text/plain; charset=UTF-8");
            PrintWriter printWriter = resp.getWriter();
            printWriter.write("Bad parameters");
            resp.setStatus(SC_BAD_REQUEST);
            return;
        }
        resp.addHeader("Location", "/goals/" + goalId);
        resp.setStatus(SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Goal goal = goalControllerHelper.writeToObject(req);
        final long goalId = Long.parseLong(req.getParameter("goalId"));
        final boolean updateGoal = goalService.updateGoal(goal, goalId);
        if (updateGoal) {
            resp.setStatus(SC_CREATED);
        } else {
            resp.setContentType("text/plain; charset=UTF-8");
            PrintWriter printWriter = resp.getWriter();
            printWriter.write("Bad parameters");
            resp.setStatus(SC_BAD_REQUEST);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final long goalId = goalControllerHelper.getIdFromPath(req);
        if (!goalService.delete(goalId)) {
            resp.setContentType("text/plain; charset=UTF-8");
            PrintWriter printWriter = resp.getWriter();
            printWriter.write("Bad goalId");
            resp.setStatus(SC_NOT_FOUND);
        }
        resp.setStatus(SC_NO_CONTENT);
    }
}
