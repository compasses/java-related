package lession.app.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by i311352 on 7/9/16.
 */
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class FileReadingApp {
    @Bean
    static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(FileReadingApp.class);

        CrimeReportData crimeReportData = (CrimeReportData) context.getBean("crimeReportData");
        crimeReportData.load();
        for (int i = 0; i < 10; ++i) {
            CrimeReportData cr = (CrimeReportData) context.getBean(CrimeReportData.class);
            CrimeReportData c = new CrimeReportData();
        }
    }
}
