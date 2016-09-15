package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by Restrictor on 15.09.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    public MealService service = new MealServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("do");
        if (action != null) {
            if (action.equals("get")) {
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("meal", service.get(id));
            } else if (action.equals("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                service.delete(id);
            }
        } else {
            request.setAttribute("meal", new Meal(null, LocalDateTime.now(), "Description", 0));
        }
        List<MealWithExceed> meals = service.getAll();
        request.setAttribute("meals", meals);
        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("do");
        if (action.equals("save")) {
            Meal meal = new Meal(
                    LocalDateTime.parse(req.getParameter(("date"))),
                    req.getParameter("description"),
                    Integer.parseInt(req.getParameter("calories"))
            );
            service.save(meal);
        } else if (action.equals("update")) {
            Meal meal = new Meal(
                    Integer.parseInt(req.getParameter("id")),
                    LocalDateTime.parse(req.getParameter(("date"))),
                    req.getParameter("description"),
                    Integer.parseInt(req.getParameter("calories"))
            );
            service.save(meal);
        }
        resp.sendRedirect("meals");
    }
}
