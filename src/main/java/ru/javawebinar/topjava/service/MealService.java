package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

/**
 * Created by Restrictor on 15.09.2016.
 */
public interface MealService {
    Meal get(int id);

    Meal save(Meal meal);

    boolean delete(int id);

    List<MealWithExceed> getAll();
}
