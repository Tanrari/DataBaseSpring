import dao.SingerDao;
import dto.Singer;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcSingerDaoWithRowMapper implements SingerDao, InitializingBean {

    private NamedParameterJdbcTemplate template;

    public void setTemplate(NamedParameterJdbcTemplate template) {
        this.template = template;
    }
//without lambda
//    @Override
//    public List<Singer> findAll() {
//        String sql = "select id,first_name, last_name, birth_date from singer";
//        return template.query(sql,new SingerMapper());
//
//    }

//with lambda


    @Override
    public List<Singer> findAll() {
        String sql = "select id,first_name, last_name, birth_date from singer";
        return  template.query(sql,(rs,rowNum)->{
            Singer singer = new Singer();
            singer.setId(rs.getLong("id"));
            singer.setFirstName(rs.getString("first_name"));
            singer.setLastName(rs.getString("last_name"));
            singer.setBirthDate(rs.getDate("birth_date"));
            return singer;

        });
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
    public List<Singer> findAllWithAlbum() {
        return null;
    }

    @Override
    public String findNameById(Long id) {
        String sql = "Select first_name ||' '|| last_name FROM singer WHERE id=:singerID";
        Map<String,Object> namedParameters = new HashMap<>();
        namedParameters.put("singerID",id);
        return template.queryForObject(
                sql,namedParameters,String.class);
    }

    @Override
    public void insertWithAlbum(Singer singer) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (template==null){
            throw new BeanCreationException("Null" + "NamedParametrJdbcTemplate on SingerDao");

        }

    }

    private static final class SingerMapper implements RowMapper<Singer> {


        @Override
        public Singer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Singer singer = new Singer();
            singer.setId(rs.getLong("id"));
            singer.setFirstName(rs.getString("first_name"));
            singer.setLastName(rs.getString("last_name"));
            singer.setBirthDate(rs.getDate("birth_date"));
            return singer;
        }
    }
}
//    String sql = "SELECT first_name ||' '|| last_name FROM singer WHERE id = :singerId";
//    Map<String, Object> namedParameters = new HashMap<>();
//		namedParameters.put("singerId", id);
//                return namedParameterJdbcTemplate.queryForObject(sql,
//                namedParameters, String.class);