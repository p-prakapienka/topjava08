package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        String[] configLocations = {"spring/spring-app.xml", "spring/spring-db.xml"};
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext(configLocations, false);
        appCtx.getEnvironment().addActiveProfile(Profiles.ACTIVE_DB);
        appCtx.getEnvironment().addActiveProfile(Profiles.DATAJPA);
        appCtx.refresh();

        System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
        AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
        adminUserController.delete(UserTestData.USER_ID);
        adminUserController.create(UserTestData.USER);
        System.out.println();

        MealRestController mealController = appCtx.getBean(MealRestController.class);
        List<MealWithExceed> filteredMealsWithExceeded =
                mealController.getBetween(
                        LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                        LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
        filteredMealsWithExceeded.forEach(System.out::println);

        appCtx.close();
    }
}
