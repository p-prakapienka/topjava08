package ru.javawebinar.topjava.repository.jdbc.hsqldb;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepositoryImpl;

import javax.sql.DataSource;

/**
 * Created by Restrictor on 09.11.2016.
 */
@Repository
@Profile(Profiles.HSQLDB)
public class HsqldbJdbcMealRepositoryImpl extends JdbcMealRepositoryImpl {
    public HsqldbJdbcMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }
}
