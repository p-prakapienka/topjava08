package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MockMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by Restrictor on 15.09.2016.
 */
public class MealServiceImpl implements MealService {
    public MealRepository repository = new MockMealRepository();

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public List<MealWithExceed> getAll() {
        return MealsUtil.getFilteredWithExceeded(
                repository.getAll(),
                LocalTime.MIN,
                LocalTime.MAX,
                2000);
    }
}
