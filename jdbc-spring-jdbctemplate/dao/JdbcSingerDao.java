package dao;
import dto.Album;
import dto.Singer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcSingerDao implements SingerDao {

    private NamedParameterJdbcTemplate template;

    public void setTemplate(NamedParameterJdbcTemplate template) {
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
    public List<Singer> findAllWithAlbum() {
        String sql ="select s.id, s.first_name, s.last_name, s.birth_date" +
        ", a.id as album_id, a.title, a.release_date from singer s " +
                "left join album a on s.id = a.singer_id";
        return template.query(sql, new SingerWithDetailExtractor());
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


    public  static final class SingerWithDetailExtractor implements ResultSetExtractor<List<Singer>>{

        @Override
        public List<Singer> extractData(ResultSet rs) throws SQLException, DataAccessException {

            Map<Long,Singer> map = new HashMap<>();
            Singer singer;
            while(rs.next()){
                Long id = rs.getLong("id");
                singer = map.get(id);
                if (singer ==null ){
                    singer = new Singer();
                    singer.setId(id);
                    singer.setFirstName(rs.getString("first_name"));
                    singer.setLastName(rs.getString("last_name"));
                    singer.setBirthDate(rs.getDate("birth_date"));
                    singer.setAlbums(new ArrayList<>());
                    map.put(id,singer);
                }
                Long albumId = rs.getLong("album_id");
                if (albumId>0){
                    Album album = new Album();
                    album.setId(albumId);
                    album.setSingerId(id);
                    album.setTitle(rs.getString("title"));
                    album.setReleaseDate(rs.getDate("release_date"));
                    singer.addAlbum(album);
                }
            }
            return new ArrayList<>(map.values());
        }
    }
}
//    String sql = "SELECT first_name ||' '|| last_name FROM singer WHERE id = :singerId";
//    Map<String, Object> namedParameters = new HashMap<>();
//		namedParameters.put("singerId", id);
//                return namedParameterJdbcTemplate.queryForObject(sql,
//                namedParameters, String.class);