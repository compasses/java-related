package jet.mq.products;

import jet.mq.elastic.index.IndexService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by I311352 on 9/30/2016.
 */
@Service
public class ProductService {
    private Logger logger = Logger.getLogger(ProductService.class);
    public static final String INDEX = "onlinestore";
    private static final String TYPE = "product";

    private final IndexService indexService;

    @Autowired
    public ProductService(IndexService indexService) {
        this.indexService = indexService;
    }

    public void createIndex() {
        try {
            File file = ResourceUtils.getFile("classpath:elastic/store-product-mapping.json");
            FileReader fileReader = new FileReader(file);
            String body = FileCopyUtils.copyToString(fileReader);
            fileReader.close();
            indexService.createIndex(INDEX, body);
        } catch (IOException e) {
            logger.warn("Could not read mapping file for creating index", e);
        }
    }

}
