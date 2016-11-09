package ru.javawebinar.topjava.repository.jdbc.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepositoryImpl;

import javax.sql.DataSource;

/**
 * Created by Restrictor on 09.11.2016.
 */
@Repository
@Profile(Profiles.POSTGRES)
public class PostgresJdbcMealRepositoryImpl extends JdbcMealRepositoryImpl {

    public PostgresJdbcMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }


}
