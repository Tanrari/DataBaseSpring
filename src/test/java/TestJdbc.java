
import dao.PlainSingerDao;
import jdbcTemplate.dao.SingerDao;
import jdbcTemplate.dto.Singer;

import java.sql.Date;
import java.util.GregorianCalendar;

public class TestJdbc {
    public static void main(String[] args) {
        SingerDao dao = new PlainSingerDao();
        System.out.println(dao.findAll());
        Singer singer = new Singer();
        singer.setBirthDate(new Date(new GregorianCalendar(1991,2,3)
                .getTime().getTime()));
        singer.setFirstName("Lucas1");
        singer.setLastName("Sheerah");
        dao.insert(singer);
    }
}
