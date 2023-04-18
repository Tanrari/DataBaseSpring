import dao.SingerDao;
import dto.Album;
import dto.Singer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import work.AppConfigMappingSqlQuerry;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

public class AnnotationJdbcTest {
    private GenericApplicationContext ctx;
    private SingerDao singerDao;
    @Before
    public void setUp(){
        ctx = new AnnotationConfigApplicationContext(AppConfigMappingSqlQuerry.class);
        singerDao= ctx.getBean(SingerDao.class);
        assertNotNull(singerDao);

    }
    @Test
    public void selectSingerByFirstName(){
        List<Singer> singers = singerDao.findByFirstName("John");
        assertTrue(singers.size()==2);
        listSingers(singers);
        ctx.close();
    }
    @Test
    public void  testFindAll(){
    List<Singer> singers = singerDao.findAll();
        assertEquals(5, singers.size());
    singers.forEach(singer -> {
        System.out.println(singer);
        if (singer.getAlbums()!=null){
            for (Album album: singer.getAlbums()){
                System.out.println("\t-->"+album);
            }
        }
    });
        ctx.close();
        }
    @Test
    public void testSingerUpdate(){
        Singer singer = new Singer();
        singer.setId(1L);
        singer.setFirstName("John Clayton");
        singer.setLastName("Mayer");
        singer.setBirthDate(new Date(
                (new GregorianCalendar(1977,9,16)).getTime().getTime()));
        singerDao.update(singer);
        List<Singer> singers = singerDao.findAll();
        listSingers(singers);
    }
    @Test
    public void testSingerInsert(){
        Singer singer = new Singer();
        singer.setFirstName("Ed");
        singer.setLastName("Sheeran");
        singer.setBirthDate(new Date((new GregorianCalendar(1991,1,17)).getTime().getTime()));
        singerDao.insert(singer);
        List<Singer> singers = singerDao.findAll();
        listSingers(singers);
    }
    @After
    public void tearDown(){
        ctx.close();
    }

    private void listSingers(List<Singer> singers){
        singers.forEach(singer -> {
            System.out.println(singer);
            if (singer.getAlbums()!=null){
                for (Album album: singer.getAlbums()){
                    System.out.println("\t-->"+album);
                }
            }
        });
    }


}


