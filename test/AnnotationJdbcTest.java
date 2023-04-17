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
    @After
    public void tearDown(){
        ctx.close();
    }
}


