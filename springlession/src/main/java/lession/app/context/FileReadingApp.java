package lession.app.context;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by i311352 on 7/9/16.
 */
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
@EnableAutoConfiguration
public class FileReadingApp {
    @Bean
    static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    static CrimeReportDataResult crimeReportDataResultAsc() {
        return new CrimeReportDataResult("Asc");
    }

    @Bean
    static CrimeReportDataResult crimeReportDataResultDsc() {
        return new CrimeReportDataResult("Dsc");
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FileReadingApp.class, args);
        System.out.println("applicate context" + context.toString());

        CrimeReportData crimeReportData = (CrimeReportData) context.getBean("crimeReportData");
        crimeReportData.load();
        for (int i = 0; i < 10; ++i) {
            CrimeReportData cr = (CrimeReportData) context.getBean(CrimeReportData.class);
            CrimeReportData c = new CrimeReportData();
        }
    }
}
