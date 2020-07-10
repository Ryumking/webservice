package controller;

import dao.DAO;
import dao.TaskDao;
import model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

@WebServlet("/update")
public class UpdateTaskServlet extends HttpServlet {
    private DAO<Task,Integer> taskDao;

    @Override
    public void init() {
        taskDao=new TaskDao();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //final int id=Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        final boolean done=req.getParameter("done")!=null;

        Task task=new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDone(done);

        if (taskDao.update(task)){
            req.getRequestDispatcher("tasks").forward(req,resp);
        }resp.setStatus(SC_BAD_REQUEST);
    }
}
