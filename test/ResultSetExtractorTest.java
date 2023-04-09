import config.EmbeddedJdbcConfig;
import dao.JdbcSingerDao;
import dao.SingerDao;
import dto.Album;
import dto.Singer;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ResultSetExtractorTest {
    @Test
    public void testResultSetExtractor(){
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(EmbeddedJdbcConfig.class);
        SingerDao singerDao = ctx.getBean(JdbcSingerDao.class);
        assertNotNull(singerDao);
        List<Singer> singers = singerDao.findAllWithAlbum();
        assertTrue(singers.size()==3);
        singers.forEach(singer -> {
            System.out.println(singer);
            if (singer.getAlbums() !=null){
                for (Album album: singer.getAlbums()){
                    System.out.println("\t-->" + album);

                }
            }
        });

    }
}
