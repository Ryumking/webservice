package controller;

import dao.DAO;
import dao.TaskDao;
import model.Task;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;

@WebServlet("/delete")
public class DeleteTaskServlet extends HttpServlet {
    private DAO<Task, Integer> taskDao;

    @Override
    public void init() {
        taskDao = new TaskDao();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int id = Integer.parseInt(req.getParameter("id"));
        if (taskDao.delete(id)) {
            resp.setStatus(SC_CREATED);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("tasks");
            requestDispatcher.forward(req, resp);
        }
        resp.setStatus(SC_BAD_REQUEST);
    }
}
