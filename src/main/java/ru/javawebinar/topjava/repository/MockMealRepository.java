package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by Restrictor on 15.09.2016.
 */
public class MockMealRepository implements MealRepository {
    private final static AtomicInteger generator = new AtomicInteger(0);

    private final static Map<Integer, Meal> repository =
            new ConcurrentHashMap<>();

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(generator.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(repository.values());
    }
}
