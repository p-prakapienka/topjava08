package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Controller
public class MealController {

    @Autowired
    private MealService service;

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String getAll(Model model) {
        int userId = AuthorizedUser.id();
        model.addAttribute("meals", MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

    @RequestMapping(value = "/meals/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request) {
        int id = getId(request);
        final Meal meal = new Meal(
                id == 0 ? null : id,
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        if (meal.isNew()) {
            service.save(meal, AuthorizedUser.id());
        } else {
            service.update(meal, AuthorizedUser.id());
        }
        return "redirect:/meals";
    }

    @RequestMapping(value = "/meals/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request) {
        int id = getId(request);
        service.delete(id, AuthorizedUser.id());
        return "redirect:/meals";
    }

    @RequestMapping(value = "/meals/create", method = RequestMethod.GET)
    public String create(Model model) {
        final Meal meal = new Meal(0, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000);
        model.addAttribute("meal", meal);
        model.addAttribute("action", "Create meal");
        return "meal";
    }

    @RequestMapping(value = "/meals/update", method = RequestMethod.GET)
    public String create(Model model, HttpServletRequest request) {
        int id = getId(request);
        final Meal meal = service.get(id, AuthorizedUser.id());
        model.addAttribute("meal", meal);
        model.addAttribute("action", "Edit meal");
        return "meal";
    }

    @RequestMapping(value = "/meals/filter", method = RequestMethod.POST)
    public String getBetween(Model model, HttpServletRequest request) {
        LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", model, request));
        LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", model, request));
        LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", model, request));
        LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", model, request));
        model.addAttribute("meals", MealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(startDate != null ? startDate : TimeUtil.MIN_DATE, endDate != null ? endDate : TimeUtil.MAX_DATE, AuthorizedUser.id()),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    private String resetParam(String param, Model model, HttpServletRequest request) {
        String value = request.getParameter(param);
        model.addAttribute(param, value);
        return value;
    }
}
