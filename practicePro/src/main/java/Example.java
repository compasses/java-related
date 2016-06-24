import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;

/**
 * Created by I311352 on 6/21/2016.
 */

@RestController
@EnableAutoConfiguration
public class Example {
    @RequestMapping("/")
    String home() {
        return "Hello world";
    }
    public static void main(String[] args) throws Exception{
        SpringApplication springApplication = new SpringApplication(Example.class);
        springApplication.setBannerMode(Banner.Mode.OFF);

        springApplication.run(args);
    }
}
