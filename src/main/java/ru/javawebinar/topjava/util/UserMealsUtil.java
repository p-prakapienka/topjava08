package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000).forEach(System.out::println);
        getStreamedWithExceed(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000).forEach(System.out::println);
    }

    public static List<UserMealWithExceed> getStreamedWithExceed(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> calories = mealList
                .stream()
                .collect(Collectors.groupingBy((um) -> um.getDateTime().toLocalDate(),
                        Collectors.mapping(um -> um.getCalories(),
                                Collectors.summingInt(c -> c))));

        return mealList
                .stream()
                .map(um -> new UserMealWithExceed(
                        um.getDateTime(),
                        um.getDescription(),
                        um.getCalories(),
                        calories.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> calories = new HashMap<>();
        for (UserMeal meal : mealList) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            if (calories.containsKey(mealDate)) {
                calories.put(mealDate, calories.get(mealDate) + meal.getCalories());
            } else {
                calories.put(mealDate, meal.getCalories());
            }
        }

        List<UserMealWithExceed> exceedsBetween = new LinkedList<>();
        for (UserMeal meal : mealList) {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                if (calories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay) {
                    exceedsBetween.add(new UserMealWithExceed(
                            meal.getDateTime(),
                            meal.getDescription(),
                            meal.getCalories(),
                            true
                    ));
                } else {
                    exceedsBetween.add(new UserMealWithExceed(
                            meal.getDateTime(),
                            meal.getDescription(),
                            meal.getCalories(),
                            false
                    ));
                }
            }
        }
        return exceedsBetween;
    }
}
