package jet.mq.messageconsume.messageProc;

import com.google.gson.JsonObject;
import jet.mq.elastic.document.ExtractUpdateFields;
import jet.mq.elastic.document.HotDocumentService;
import jet.mq.elastic.document.ProductDocService;
import jet.mq.messageconsume.MQ_Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by I311352 on 10/14/2016.
 */

@Service
public class ProductMsgHandler implements MessageHandler, ExtractUpdateFields<JsonObject, JsonObject> {
    public static Logger logger = Logger.getLogger(ProductMsgHandler.class);

    public ProductDocService getDocService() {
        return productDocService;
    }

    public void setDocService(ProductDocService productDocService) {
        this.productDocService = productDocService;
    }

    @Autowired
    public ProductDocService productDocService;

    public HotDocumentService hotDocumentService;

    @Override
    public JsonObject getUpdateFields(JsonObject jsonObject) {
        return null;
    }

    public  void handleMsg(String action, Long tenantId, byte[] body) {
        logger.info("Handle product msg :" + action);
        if (StringUtils.pathEquals(action, "DELETE")) {
            productDocService.RemoveProudct(tenantId, body);
            hotDocumentService.update(MQ_Constants.STORE_INDEX, MQ_Constants.PRODUCT_TYPE,1L, this);

            return;
        }

        productDocService.StoreProduct(action, tenantId, body);
    }
}
