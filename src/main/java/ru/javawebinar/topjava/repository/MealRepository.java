package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by Restrictor on 15.09.2016.
 */
public interface MealRepository {
    Meal get(int id);

    Meal save(Meal meal);

    boolean delete(int id);

    List<Meal> getAll();
}
