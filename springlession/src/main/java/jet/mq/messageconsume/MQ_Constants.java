package jet.mq.messageconsume;

import jet.mq.elastic.document.ProductDocService;
import jet.mq.messageconsume.messageProc.CategoryMsgHandler;
import jet.mq.messageconsume.messageProc.MessageHandler;
import jet.mq.messageconsume.messageProc.ProductMsgHandler;
import jet.mq.messageconsume.messageProc.SKUMsgHandler;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by I311352 on 10/14/2016.
 */
public final class MQ_Constants {
    public      static  final   String STORE_INDEX = "onlinestore";
    public      static  final   String PRODUCT_TYPE = "product";
    public      static  final   String SKU_TYPE = "sku";

    public      static  final   String ELASTICQUEUENAME = "elasticQueue";
    public      static  final   String EXCHANGE_NAME = "SharedExchange";
    public      static final    Pattern ROUTINGKEY_PATTERN = Pattern.compile("([\\w\\.]+)\\.(\\w+)\\.(\\d+)");

    public static void InitialRoutingHandler(ApplicationContext context) {
        // for product related
        ProductMsgHandler msgHandler = new ProductMsgHandler();
        msgHandler.setDocService(context.getBean(ProductDocService.class));
        routingMsg.put("Product.#", msgHandler);

        // for sku related
        SKUMsgHandler skuMsgHandler = new SKUMsgHandler();
        skuMsgHandler.setDocService(context.getBean(ProductDocService.class));
        routingMsg.put("SKU.#", skuMsgHandler);

        // for category
        //CategoryMsgHandler categoryMsgHandler = new CategoryMsgHandler();

    }

    public static final Map<String, MessageHandler> routingMsg = new HashMap<String, MessageHandler>() {
    };

    public static final String[] keys = {"Product.#", "Category.#", "SKUPriceList.#", "SKU.#"};
}
