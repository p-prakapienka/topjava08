package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * gkislin
 * 02.10.2016
 */
@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Override
    @Transactional
    Meal save(Meal meal);

    @Transactional
    @Modifying
    int delete(@Param("id") int id, @Param("userId") int userId);

    Meal get(@Param("id") int id, @Param("userId") int userId);

    List<Meal> getAll(@Param("userId") int userId);

    List<Meal> getBetween(@Param("startDate") LocalDateTime startDate,
                          @Param("endDate") LocalDateTime endDate,
                          @Param("userId") int userId);
}
