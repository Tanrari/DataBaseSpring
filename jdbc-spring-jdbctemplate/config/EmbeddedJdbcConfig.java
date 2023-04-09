package config;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import dao.SingerDao;
import dao.JdbcSingerDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
@Configuration
public class EmbeddedJdbcConfig {
    private  static Logger logger  = LoggerFactory.getLogger(EmbeddedJdbcConfig.class);
    @Bean
    public DataSource dataSource(){
        try {
            EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
            return dbBuilder.setType(EmbeddedDatabaseType.H2)
                    .addScripts("classpath:schema.sql",
                            "classpath:data.sql").build();
        }catch (Exception e){
            logger.error("Embedded DataSource bean cannot be created",e);
            return null;
        }
    }
    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate(){
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource());
//        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }
    @Bean
    public SingerDao singerDao(){
        JdbcSingerDao dao = new JdbcSingerDao();
        dao.setTemplate(jdbcTemplate());
        return dao;
    }
    @Bean
    public JdbcSingerDaoWithRowMapper singerDaoWithRowMapper(){
        JdbcSingerDaoWithRowMapper daoWithRowMapper = new JdbcSingerDaoWithRowMapper();
        daoWithRowMapper.setTemplate(jdbcTemplate());
        return daoWithRowMapper;
    }
}
