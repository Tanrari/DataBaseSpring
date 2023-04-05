import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration
@PropertySource("classpath:jdbc_.properties")
public class DbConfig {
    @Value("${driverClassName}")
    private String driverClassName;
    @Value("${url}")
    private String url;
    @Value("${username}")
    private String userName;
    @Value("${password}")
    private String password;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return  new PropertySourcesPlaceholderConfigurer();
    }
    @Bean
    @Lazy
    public DataSource dataSource(){
        try {
            SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
            Class <? extends Driver> driver = (Class<? extends Driver>) Class.forName(driverClassName);
            dataSource.setDriverClass(driver);
            dataSource.setUrl(url);
            dataSource.setUsername(userName);
            dataSource.setPassword(password);
            return dataSource;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
