package work;

import dao.SingerDao;
import dto.Singer;
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
    private  UpdateSinger updateSinger;

    @Resource(name = "dataSource")
    public void setDataSource (DataSource dataSource){
        this.dataSource= dataSource;
        this.selectAllSingers = new SelectAllSingers(dataSource);
        this.selectSingerByFirstName = new SelectSingerByFirstName(dataSource);
        this.updateSinger = new UpdateSinger(dataSource);
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
        return null;
    }

    @Override
    public String findNameById(Long id) {
        return null;
    }
}
