package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        ExceptionUtil.checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public List<MealWithExceed> getAll(int userId) {
        return MealsUtil.getFilteredWithExceeded(
                repository.getAll(userId),
                LocalTime.MIN,
                LocalTime.MAX,
                userRepository.get(userId).getCaloriesPerDay());
    }

    @Override
    public List<MealWithExceed> getBetween(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Meal> meals = repository.getAll(userId).stream()
                .filter(m -> (m.getDateTime().toLocalDate().isAfter(startDateTime.toLocalDate()) ||
                        m.getDateTime().toLocalDate().equals(startDateTime.toLocalDate())) &&
                        (m.getDateTime().toLocalDate().isBefore(endDateTime.toLocalDate()) ||
                        m.getDateTime().toLocalDate().equals(endDateTime.toLocalDate())))
                .collect(Collectors.toList());

        return MealsUtil.getFilteredWithExceeded(
                meals,
                startDateTime.toLocalTime(),
                endDateTime.toLocalTime(),
                userRepository.get(userId).getCaloriesPerDay());
    }
}
