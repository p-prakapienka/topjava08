package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        return service.save(meal, AuthorizedUser.id());
    }

    public void delete(int id) {
        service.delete(id, AuthorizedUser.id());
    }

    public void update(Meal meal) {
        service.save(meal, AuthorizedUser.id());
    }

    public Meal get(int id) {
        return service.get(id, AuthorizedUser.id());
    }

    public List<MealWithExceed> getAll() {
        return service.getAll(AuthorizedUser.id());
    }

    public List<MealWithExceed> getBetween() {
        return service.getBetween(AuthorizedUser.id(), LocalDateTime.of(2015, Month.MAY, 31, 0, 0), LocalDateTime.of(2015, Month.MAY, 31, 23, 0));
    }
}
