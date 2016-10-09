package jet.mq.elastic.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import jet.mq.elastic.common.ElasticAPIException;
import jet.mq.elastic.common.ElasticQueryException;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by I311352 on 10/3/2016.
 */
@Service
public class DocumentService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentService.class);

    private final RestClient client;
    private final ObjectMapper jacksonObjectMapper;

    @Autowired
    public DocumentService(RestClient client, ObjectMapper jacksonObjectMapper) {
        this.client = client;
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    public void StoreProduct(String index, String type, Long tenantId, byte[] bytes) {
        TypeFactory typeFactory = jacksonObjectMapper.getTypeFactory();
        MapType mapType = typeFactory.constructMapType(HashMap.class, String.class, Object.class);
       try {
           Map<String, Object> objects =  jacksonObjectMapper.readValue(bytes, mapType);
           Long id = (Long) objects.get("id");

           HashMap<String, Object> channels = new HashMap<>();
           channels.put("channleId", 2016L);
           channels.put("RMB", 0.0000001*id);
           channels.put("USD", 0.0000002*id);
           Object[] channlesarr = new Object[1];
           channlesarr[0] = channels;

           objects.put("channles", channlesarr);

           HttpEntity requestBody = new StringEntity(jacksonObjectMapper.writeValueAsString(objects),
                   Charset.defaultCharset());

           Response response;
           HashMap<String, String> params = new HashMap<>();
           params.put("routing", tenantId.toString());



           response = client.performRequest(
                   "POST",
                   index + "/" + type + "/" + id,
                   params,
                   requestBody);

           int statusCode = response.getStatusLine().getStatusCode();
           if (statusCode > 299) {
               logger.warn("Problem while indexing a document: {}", response.getStatusLine().getReasonPhrase());
               throw new ElasticAPIException("Could not index a document, status code is " + statusCode);
           }
           logger.info("Got response :{}", response.getEntity().toString());
       } catch (IOException e) {
           e.printStackTrace();
           logger.warn("error response...");
       }
    }


    public void StoreOriginal(String index, String type, byte[] body) {
        try {
            HttpEntity requestBody = new StringEntity(new String(body), Charset.defaultCharset());

            Response response;

            response = client.performRequest(
                    "POST",
                    index + "/" + type,
                    new Hashtable<>(),
                    requestBody);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode > 299) {
                logger.warn("Problem while indexing a document: {}", response.getStatusLine().getReasonPhrase());
                throw new ElasticAPIException("Could not index a document, status code is " + statusCode);
            }
            logger.info("Got response :{}", response.getEntity().toString());
//            HashMap<String, Object> queryResponse = jacksonObjectMapper.readValue(response.getEntity().getContent(), HashMap<String, Object>.class);
//
//            return queryResponse.getId();

        } catch (IOException e) {
            logger.warn("Problem while executing request.", e);
            throw new ElasticQueryException("Error when executing a document");
        }

    }

}
