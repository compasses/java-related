package exportimport;


import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * Created by I311352 on 4/24/2017.
 */
@SpringBootApplication
public class MainCtrl {
    private static final Logger logger = Logger.getLogger(MainCtrl.class);

    public static void main(String args[]) {
        SpringApplication.run(MainCtrl.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
//            String fileName = "export.json";
//            String eshost = "127.0.0.1:9200";
//            String buket = "s3_buket";
//            Long tenantId = -1L;//35401674516640L;
//
//            LoadData loadData = new LoadData();
//            logger.info("going to save data....");
//            //loadData.saveData(fileName, tenantId, buket, eshost);
           // loadData.testPutData(eshost);
            if (System.getProperty("config") != null && Boolean.parseBoolean(System.getProperty("export")) == true) {
                logger.info("Use user config " + System.getProperty("config"));
                try {
                    File file = new File(System.getProperty("config"));
                    if (file.exists()) {
                        FileInputStream inputStream = new FileInputStream(file);
                        Properties props = new Properties();
                        props.load(inputStream);

                        String fileName = props.getProperty("FILE");
                        String eshost = props.getProperty("ES_HOST");
                        String buket = props.getProperty("S3_BUKET");
                        Long tenantId = Long.parseLong(props.getProperty("TENANTID"));

                        logger.info("going to export data fileName=" + fileName +" ESHOST="+eshost + " buket="+buket
                        +" tenantId=" +tenantId);

                        LoadData loadData = new LoadData();
                        loadData.saveData(fileName, tenantId, buket, eshost);
                    } else {
                        logger.info("cannot find file " + System.getProperty("config"));
                    }
                } catch (IOException e) {
                    logger.error("file load fail "+e);
                }
            } else if (System.getProperty("config") != null && Boolean.parseBoolean(System.getProperty("export")) == false){
                File file = new File(System.getProperty("config"));
                if (!file.exists()) {
                    logger.error("file not exist.." + file);
                }

                FileInputStream inputStream = new FileInputStream(file);
                Properties props = new Properties();
                props.load(inputStream);

                String fileName = props.getProperty("FILE");
                String eshost = props.getProperty("ES_HOST");
                LoadData loadData = new LoadData();
                loadData.PutData(eshost, fileName);
            } else {
                logger.error("cannot get properties " + System.getProperty("config") + System.getProperty("export"));
            }

            System.exit(0);
        };
    }
}
