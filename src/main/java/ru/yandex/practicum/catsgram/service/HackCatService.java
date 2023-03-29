package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static org.postgresql.core.SqlCommandType.SELECT;

@Service
public class HackCatService {

    private Logger log = LoggerFactory.getLogger(getClass());

    public static final String JDBC_URL="jdbc:postgresql://127.0.0.1:5432/cats";
    public static final String JDBC_USERNAME="kitty";
    public static final String JDBC_DRIVER="org.postgresql.Driver";

    public void tryPassword(String jdbcPassword) {
        DriverManagerDataSource dataSourceConst = new DriverManagerDataSource();
        // проверьте подключение с паролем и параметрами БД
        dataSourceConst.setDriverClassName(JDBC_DRIVER);
        dataSourceConst.setUrl(JDBC_URL);
        dataSourceConst.setUsername(JDBC_USERNAME);
        dataSourceConst.setPassword(jdbcPassword);
        //dataSourceConst.setPassword("purrrrr");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceConst);
        jdbcTemplate.execute("SELECT * FROM cat_user;");
    }

    public void doHackNow(){
        List<String> catWordList = Arrays.asList("meow", "purr", "purrrrrr", "zzz");
        // проверьте пароли из списка
        for(String password : catWordList) {
            try {
                log.info("Проверяем пароль: " + password);
                tryPassword(password);
                break;
            } catch (Exception e) {
                log.info("DataAccessException : " + e.getMessage());
            }
        }
    }
}
