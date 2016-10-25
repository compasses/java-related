package jet.mq.messageconsume.messageProc;

import jet.mq.elastic.document.ProductDocService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by I311352 on 10/14/2016.
 */

@Service
public class ProductMsgHandler implements MessageHandler {
    public static Logger logger = Logger.getLogger(ProductMsgHandler.class);

    public ProductDocService getDocService() {
        return productDocService;
    }

    public void setDocService(ProductDocService productDocService) {
        this.productDocService = productDocService;
    }

    @Autowired
    public ProductDocService productDocService;

    public  void handleMsg(String action, Long tenantId, byte[] body) {
        logger.info("Handle product msg :" + action);
        if (StringUtils.pathEquals(action, "DELETE")) {
            productDocService.RemoveProudct(tenantId, body);
            return;
        }

        productDocService.StoreProduct(action, tenantId, body);
    }
}
