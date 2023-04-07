import jdbcTemplate.dao.SingerDao;
import jdbcTemplate.config.EmbeddedJdbcConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import static org.junit.Assert.*;


public class JdbcCfgTest {
    @Test
    public void testH2Db(){
        GenericApplicationContext ctx =new AnnotationConfigApplicationContext(EmbeddedJdbcConfig.class);

//        ctx.refresh();
        testDao(ctx.getBean(SingerDao.class));
        ctx.close();

    }

    public void testDao(SingerDao dao){
        assertNotNull(dao);
        String singerName = dao.findNameById(1L);
        System.out.println(singerName);
        assertEquals("John Mayer", singerName);

    }
}
