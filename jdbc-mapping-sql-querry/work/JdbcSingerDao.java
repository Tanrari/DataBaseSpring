package work;

import dao.SingerDao;
import dto.Singer;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
@Repository("singerDao")
public class JdbcSingerDao implements SingerDao {
    private DataSource dataSource;
    private SelectAllSingers selectAllSingers;

    @Resource(name = "dataSource")
    public void setDataSource (DataSource dataSource){
        this.dataSource= dataSource;
        this.selectAllSingers = new SelectAllSingers(dataSource);
    }
    @Override
    public List<Singer> findAll() {
        return selectAllSingers.execute();
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
        return null;
    }
}
