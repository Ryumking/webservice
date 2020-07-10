package controller;

import dao.DAO;
import dao.TaskDao;
import dao.UserDao;
import model.Task;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;

@WebServlet("/add")
public class AddTaskServlet extends HttpServlet {
    private DAO<Task, Integer> taskDao;


    @Override
    public void init() {
        taskDao = new TaskDao();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //User user=(User)(req.getSession().getAttribute("login"));
        final String title = req.getParameter("title");
        final String description = req.getParameter("description");
        final Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDone(false);

        if (taskDao.create(task)) {
            resp.setStatus(SC_CREATED);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("tasks");
            requestDispatcher.forward(req, resp);
        }
        resp.setStatus(SC_BAD_REQUEST);
    }
}
