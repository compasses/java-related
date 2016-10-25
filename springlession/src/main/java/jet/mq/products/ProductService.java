package jet.mq.products;

import com.rabbitmq.client.*;
import jet.mq.elastic.document.DocumentService;
import jet.mq.elastic.index.IndexService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * Created by I311352 on 9/30/2016.
 */
@Service
public class ProductService {
    private static Logger logger = Logger.getLogger(ProductService.class);
    public static final String INDEX = "stores";
    private static final String TYPE = "product";
    public static final String ELASTICQUEUENAME = "elasticQueue";

    private final IndexService indexService;
    private final DocumentService documentService;

    @Autowired
    public ProductService(IndexService indexService, DocumentService documentService) {
        this.indexService = indexService;
        this.documentService = documentService;
    }

    public void createIndex() {
        try {
             Resource resource = new ClassPathResource("/elastic/store-product-mapping.json");
             File file = resource.getFile();

            //File file = ResourceUtils.getFile("classpath:elastic/store-product-mapping.json");
            FileReader fileReader = new FileReader(file);
            String body = FileCopyUtils.copyToString(fileReader);
            fileReader.close();
            indexService.createIndex(INDEX, body);
        } catch (IOException e) {
            logger.warn("Could not read mapping file for creating index", e);
        }
    }

    public String count(Long tenantId) {
        return documentService.count(INDEX, TYPE, tenantId);
    }

    public String query(Long tenantId, String body) {
        return documentService.doQuery(INDEX, TYPE, tenantId, body).toString();
    }

    public String loadProductById(Long productId, Long tenantId) {
        return documentService.loadProductById(INDEX, TYPE, productId, tenantId);
    }


}
