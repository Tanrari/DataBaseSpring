package work;

import dao.SingerDao;
import dto.Album;
import dto.Singer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("singerDao")
public class JdbcSingerDao implements SingerDao {
    private DataSource dataSource;
    private SelectAllSingers selectAllSingers;
    private SelectSingerByFirstName selectSingerByFirstName;
    private InsertSinger insertSinger;
    private  UpdateSinger updateSinger;
    private InsertSingerAlbum insertSingerAlbum;

    @Resource(name = "dataSource")
    public void setDataSource (DataSource dataSource){
        this.dataSource= dataSource;
        this.selectAllSingers = new SelectAllSingers(dataSource);
        this.selectSingerByFirstName = new SelectSingerByFirstName(dataSource);
        this.updateSinger = new UpdateSinger(dataSource);
        this.insertSinger = new InsertSinger(dataSource);

    }
    @Override
    public List<Singer> findAll() {
        return selectAllSingers.execute();
    }

    @Override
    public List<Singer> findByFirstName(String firstName) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("first_name",firstName);

        return selectSingerByFirstName.executeByNamedParam(paramMap);
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
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("first_name",singer.getFirstName());
        paramMap.put("last_name",singer.getLastName());
        paramMap.put("birth_date",singer.getBirthDate());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertSinger.updateByNamedParam(paramMap,keyHolder);
        singer.setId(keyHolder.getKey().longValue());



    }

    @Override
    public void update(Singer singer) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("first_name",singer.getFirstName());
        paramMap.put("last_name",singer.getLastName());
        paramMap.put("birth_date",singer.getBirthDate());
        paramMap.put("id",singer.getId());
        updateSinger.updateByNamedParam(paramMap);


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
        JdbcTemplate template = new JdbcTemplate(dataSource);
        String sql ="select s.id, s.first_name, s.last_name, s.birth_date" +
                ", a.id as album_id, a.title, a.release_date from singer s " +
                "left join album a on s.id = a.singer_id";
        return template.query(sql, new dao.JdbcSingerDao.SingerWithDetailExtractor());
    }

    @Override
    public String findNameById(Long id) {
        return null;
    }

    @Override
    public void insertWithAlbum(Singer singer) {
     this.insertSingerAlbum = new InsertSingerAlbum(dataSource);
     Map<String,Object> paramMap = new HashMap<>();
     paramMap.put("first_name",singer.getFirstName());
     paramMap.put("last_name",singer.getLastName());
     paramMap.put("birth_date", singer.getBirthDate());
     KeyHolder keyHolder = new GeneratedKeyHolder();
     insertSinger.updateByNamedParam(paramMap,keyHolder);
     singer.setId(keyHolder.getKey().longValue());
     List<Album> albums = singer.getAlbums();
     if (albums!=null){
         for (Album album: albums){
             paramMap = new HashMap<>();
             paramMap.put("singer_id",singer.getId());
             paramMap.put("title",album.getTitle());
             paramMap.put("release_date",album.getReleaseDate());
             insertSingerAlbum.updateByNamedParam(paramMap);

         }
     }
    insertSingerAlbum.flush();

    }
}
