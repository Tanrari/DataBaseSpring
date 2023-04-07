package jdbcTemplate.dao;

import jdbcTemplate.dto.Singer;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcSingerDao implements SingerDao {

    private JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Singer> findAll() {
        return null;
    }

    @Override
    public List<Singer> findByFirstName(String firstName) {
        return null;
    }

    @Override
    public String findLastNameById(Long id) {
        return null;

    }

    @Override
    public String findFirstNameById(Long id) {
        return null;
    }

    @Override
    public void insert(Singer singer) {

    }

    @Override
    public void update(Singer singer) {

    }

    @Override
    public void delete(Long singerId) {

    }

    @Override
    public List<Singer> findAllWithDetail() {
        return null;
    }

    @Override
    public void insertWithDetail(Singer singer) {

    }

    @Override
    public String findNameById(Long id) {
        return template.queryForObject(
                "select first_name || ' ' || last_name from singer where id = ?",
                new Object[]{id}, String.class);
    }
}
