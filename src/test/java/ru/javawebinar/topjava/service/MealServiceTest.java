package ru.javawebinar.topjava.service;

/**
 * Created by Restrictor on 23.10.2016.
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    protected MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "Test meal", 520);
        Meal created = service.save(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(newMeal, ADMIN_MEAL2, ADMIN_MEAL1), service.getAll(ADMIN_ID));
    }

    @Test(expected = DataAccessException.class)
    public void testDuplicateDateTimeSave() throws Exception {
        service.save(new Meal(null, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Test meal", 300), ADMIN_ID);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(ADMIN_MEAL1_ID, ADMIN_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(ADMIN_MEAL2), service.getAll(ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(ADMIN_MEAL1_ID, USER_ID);
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(MEAL1_ID, USER_ID);
        MATCHER.assertEquals(MEAL1, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("UpdatedDesc");
        updated.setCalories(330);
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(MEAL1_ID, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("UpdatedDesc");
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Meal> all = service.getAll(ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL2, ADMIN_MEAL1), all);
    }

    @Test
    public void testGetBetween() throws Exception {
        Collection<Meal> between = service.getBetweenDateTimes(
                LocalDateTime.of(2015, Month.MAY, 30, 7, 0),
                LocalDateTime.of(2015, Month.MAY, 30, 14, 0),
                USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL2, MEAL1), between);
    }
}
