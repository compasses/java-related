package jet.mq.elastic.document;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import jet.mq.messageconsume.MQ_Constants;
import org.apache.log4j.Logger;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by I311352 on 10/17/2016.
 */

@Service
public class CategoryDocService extends DocumentService {
    private static Logger logger = Logger.getLogger(CategoryDocService.class);

    @Autowired
    public CategoryDocService(RestClient restClient, Gson gson) {
        super(restClient, gson);
    }

    public void associateProduct(Long categoryId, Long tenantId, JsonArray productIds) {
        logger.info("Going to associate product " + productIds.toString() + " with category " + categoryId);
        for (int i = 0; i < productIds.size(); i++ ) {
            Long productId = productIds.get(0).getAsLong();

            //doQuery(MQ_Constants.STORE_INDEX, MQ_Constants.PRODUCT_TYPE, tenantId, );
        }
    }
}
