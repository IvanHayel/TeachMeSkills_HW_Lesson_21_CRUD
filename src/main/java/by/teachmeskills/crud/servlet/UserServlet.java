package by.teachmeskills.crud.servlet;

import by.teachmeskills.crud.model.User;
import by.teachmeskills.crud.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class UserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    public void destroy() {
        userService.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        switch (action) {
            case "/new":
                showNewForm(req, resp);
                break;
            case "/insert":
                insertUser(req, resp);
                break;
            case "/delete":
                deleteUser(req, resp);
                break;
            case "/delete-all":
                deleteAllUsers(req, resp);
                break;
            case "/edit":
                showEditForm(req, resp);
                break;
            case "/update":
                updateUser(req, resp);
                break;
            default:
                listUser(req, resp);
                break;
        }
    }

    @SneakyThrows({IOException.class, ServletException.class})
    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher dispatcher = req.getRequestDispatcher("pages/user-form.jsp");
        dispatcher.forward(req, resp);
    }

    @SneakyThrows(IOException.class)
    private void insertUser(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        User newUser = new User(name, email);
        userService.create(newUser);
        resp.sendRedirect("list");
    }

    @SneakyThrows(IOException.class)
    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.parseInt(req.getParameter("id"));
        userService.deleteById(id);
        resp.sendRedirect("list");
    }

    @SneakyThrows(IOException.class)
    private void deleteAllUsers(HttpServletRequest req, HttpServletResponse resp) {
        userService.deleteAll();
        resp.sendRedirect("list");
    }

    @SneakyThrows({IOException.class, ServletException.class})
    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        User existingUser = userService.findById(id);
        RequestDispatcher dispatcher = req.getRequestDispatcher("pages/user-form.jsp");
        req.setAttribute("user", existingUser);
        dispatcher.forward(req, resp);
    }

    @SneakyThrows(IOException.class)
    private void updateUser(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        User user = new User(id, name, email);
        userService.update(user);
        resp.sendRedirect("list");
    }

    @SneakyThrows({IOException.class, ServletException.class})
    private void listUser(HttpServletRequest req, HttpServletResponse resp) {
        List<User> listUser = userService.findAll();
        req.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = req.getRequestDispatcher("pages/user-list.jsp");
        dispatcher.forward(req, resp);
    }
}