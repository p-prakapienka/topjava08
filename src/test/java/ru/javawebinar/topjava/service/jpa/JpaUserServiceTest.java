package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

/**
 * Created by Restrictor on 09.11.2016.
 */
@ActiveProfiles(Profiles.JPA)
public class JpaUserServiceTest extends UserServiceTest {
}
