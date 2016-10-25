package jet.mq.messageconsume.messageProc;

import jet.mq.elastic.document.ProductDocService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * Created by I311352 on 10/17/2016.
 */
public class SKUMsgHandler implements MessageHandler {
    public static Logger logger = Logger.getLogger(SKUMsgHandler.class);

    public ProductDocService getDocService() {
        return productDocService;
    }

    public void setDocService(ProductDocService productDocService) {
        this.productDocService = productDocService;
    }

    @Autowired
    public ProductDocService productDocService;

    public  void handleMsg(String action, Long tenantId, byte[] body) {
        logger.info("Handle SKU msg :" + action);

        if (StringUtils.pathEquals(action, "DELETE")) {
            productDocService.RemoveProudct(tenantId, body);
            return;
        }

        productDocService.StoreSKU(action, tenantId, body);
    }
}
