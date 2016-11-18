package jet.mq.elastic.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jet.mq.messageconsume.MQ_Constants;
import jet.mq.products.ProductService;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Created by I311352 on 10/14/2016.
 */
@Service
public class ProductDocService extends DocumentService implements ExtractUpdateFields<JsonObject, JsonObject> {
    public static final Logger logger = Logger.getLogger(ProductDocService.class);
    JsonParser jsonParser = new JsonParser();

    @Autowired
    public ProductDocService(RestClient restClient, Gson gson) {
        super(restClient, gson);
    }

    @Override
    public JsonObject getUpdateFields(JsonObject jsonObject) {
        return null;
    }

    public void RemoveProudct(Long tenantId, byte[] body) {
        logger.info("Handle Delete Product msg.");
        Long productId = jsonParser.parse(new String(body)).getAsLong();
        HashMap<String, String> params = new HashMap<>();
        params.put("routing", tenantId.toString());
        Delete(MQ_Constants.STORE_INDEX, MQ_Constants.PRODUCT_TYPE, productId, params);
    }

    public void StoreSKU(String action, Long tenantId, byte[] body) {
        //
        logger.info("Handle Product message " + action);
        JsonObject object = jsonParser.parse(new String(body)).getAsJsonObject();
        Long skuId = object.get("id").getAsLong();
        Long productId = 0L;

        //sku body still contains product info, need delete
        if (object.has("product")) {
            JsonObject product = object.get("product").getAsJsonObject();
            productId = product.get("id").getAsLong();
            //
            object.remove("product");
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("routing", tenantId.toString());
        params.put("parent", productId.toString());

        HttpEntity requestBody = new StringEntity(object.toString(), Charset.defaultCharset());
        String resp = Store(MQ_Constants.STORE_INDEX, MQ_Constants.SKU_TYPE, skuId, params, requestBody);
        logger.info("Response is " + resp);
    }

    public void StoreProduct(String action, Long tenantId, byte[] body) {
        // do not care about the action now
        logger.info("Handle Product message " + action);
        JsonObject object = jsonParser.parse(new String(body)).getAsJsonObject();
        Long productId = object.get("id").getAsLong();
        JsonArray skus = object.getAsJsonArray("skus");

        // remove skus on product doc
        object.remove("skus");
        HashMap<String, String> params = new HashMap<>();
        params.put("routing", tenantId.toString());
        HttpEntity requestBody = new StringEntity(object.toString(), Charset.defaultCharset());
        String resp = Store(MQ_Constants.STORE_INDEX, MQ_Constants.PRODUCT_TYPE, productId, params, requestBody);
        logger.info("Response is " + resp);
        if (resp != null) {
            StoreSKU(skus, productId, tenantId);
        }
    }

    public void StoreSKU(JsonArray skus, Long parentId, Long tenantId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("routing", tenantId.toString());
        params.put("parent", parentId.toString());

        for (int i = 0; i < skus.size(); ++i) {
            JsonObject o = skus.get(i).getAsJsonObject();
            Long skuId = o.get("id").getAsLong();
            HttpEntity requestBody = new StringEntity(o.toString(), Charset.defaultCharset());
            Store(MQ_Constants.STORE_INDEX, MQ_Constants.SKU_TYPE, skuId, params, requestBody);
        }
    }
}
