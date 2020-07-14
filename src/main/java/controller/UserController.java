package controller;

import model.User;
import service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static javax.servlet.http.HttpServletResponse.*;

public class UserController extends HttpServlet {
    private final ControllerHelper<User> userControllerHelper = new ControllerHelper<>(User.class);
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        final long id = userControllerHelper.getIdFromPath(req);
        if (id == 0L) {
            userControllerHelper.writeToJson(resp, userService.findAllUsers());
            return;
        }
        userControllerHelper.writeToJson(resp, userService.findUserById(id));
        resp.setStatus(SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = userControllerHelper.writeToObject(req);
        final long userId = userService.createUser(user);
        if (userId == 0) {
            resp.setContentType("text/plain; charset=UTF-8");
            PrintWriter writer = resp.getWriter();
            writer.write("Bad parameters");
            resp.setStatus(SC_BAD_REQUEST);
        }
        resp.addHeader("Location", "/users/" + userId);
        resp.setStatus(SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = userControllerHelper.writeToObject(req);
        final long userId = Long.parseLong(req.getParameter("userId"));
        final boolean updateUser = userService.updateUser(user, userId);
        if (updateUser) {
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
        final long idFromPath = userControllerHelper.getIdFromPath(req);
        if (!userService.delete(idFromPath)) {
            resp.setContentType("text/plain; charset=UTF-8");
            PrintWriter printWriter = resp.getWriter();
            printWriter.write("Bad userId");
            resp.setStatus(SC_NOT_FOUND);
        }
        resp.setStatus(SC_NO_CONTENT);
    }
}
