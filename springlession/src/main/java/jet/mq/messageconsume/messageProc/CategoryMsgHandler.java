package jet.mq.messageconsume.messageProc;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jet.mq.elastic.document.CategoryDocService;
import org.apache.http.impl.cookie.PublicSuffixDomainFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by I311352 on 10/17/2016.
 */

public class CategoryMsgHandler implements MessageHandler {
    public static Logger logger = Logger.getLogger(ProductMsgHandler.class);

    private final CategoryDocService docService;

    @Autowired
    public CategoryMsgHandler(CategoryDocService docService) {
        this.docService = docService;
    }

    public  void handleMsg(String action, Long tenantId, byte[] body) {
        logger.info("Handle category msg :" + action);

        if (action.equals("ASSOCIATE")) {
            JsonObject object = new JsonParser().parse(new String(body)).getAsJsonObject();
            Long categoryId = object.get("id").getAsLong();
            JsonArray productIds = object.get("productIds").getAsJsonArray();
            if (categoryId != null && productIds != null) {
               // docService.associateProduct(categoryId, productIds);
            }

            return;
        }
        logger.info("Not support action " + action);
    }

}
