public class TestJdbc {
    public static void main(String[] args) {
        SingerDao dao = new PlainSingerDao();
        System.out.println(dao.findAll());
    }
}
