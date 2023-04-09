import config.EmbeddedJdbcConfig;
import dao.SingerDao;
import dto.Album;
import dto.Singer;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

public class RowMapperTest {
    @Test
    public void testRowMapper(){
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(EmbeddedJdbcConfig.class);
        SingerDao dao = ctx.getBean(JdbcSingerDaoWithRowMapper.class);
        assertNotNull(dao);
        List<Singer> singers = dao.findAll();
        assertEquals(3, singers.size());
        singers.forEach(singer -> {
            System.out.println(singer);
            if (singer.getAlbums()!=null){
                for (Album album: singer.getAlbums()){
                    System.out.println("---"+album);
                }
            }
        });
        ctx.close();
    }
}
