package jet.mq.elastic.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jet.mq.elastic.common.ElasticAPIException;
import jet.mq.elastic.common.ElasticQueryException;
import jet.mq.elastic.document.resp.ESQueryResponse;
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

/**
 * Created by I311352 on 10/3/2016.
 */

@Service
public class DocumentService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentService.class);

    private final RestClient client;
    private final Gson gson;

    @Autowired
    public DocumentService(RestClient client, Gson gson) {
        this.client = client;
        this.gson = gson;
    }

    public String Delete(String index, String type, Long sourceId, HashMap param) {
        try {
            Response response = client.performRequest(
                    "DELETE",
                    index + "/" + type + "/" + sourceId,
                    param,
                    new StringEntity(""));

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode > 299) {
                logger.warn("Problem while indexing a document: {}", response.getStatusLine().getReasonPhrase());
                throw new ElasticAPIException("Could not index a document, status code is " + statusCode);
            }

            Long length = response.getEntity().getContentLength();
            byte[] res = new byte[length.intValue()];

            response.getEntity().getContent().read(res);
            return new String(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String Store(String index, String type, Long sourceId, HashMap param, HttpEntity requestBody) {
        try {
            Response response = client.performRequest(
                    "POST",
                    index + "/" + type + "/" + sourceId,
                    param,
                    requestBody);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode > 299) {
                logger.warn("Problem while indexing a document: {}", response.getStatusLine().getReasonPhrase());
                throw new ElasticAPIException("Could not index a document, status code is " + statusCode);
            }

            Long length = response.getEntity().getContentLength();
            byte[] res = new byte[length.intValue()];

            response.getEntity().getContent().read(res);
            return new String(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String count(String index, String type, Long tenantId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("routing", tenantId.toString());
        try {
            Response response = client.performRequest(
                    "GET",
                    index + "/" + type + "/_count",
                    params,
                    new StringEntity(""));

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode > 299) {
                logger.warn("Problem while indexing a document: {}", response.getStatusLine().getReasonPhrase());
                throw new ElasticAPIException("Could not index a document, status code is " + statusCode);
            }
            logger.info("Got response :{}", response.getEntity().toString());
            Long length = response.getEntity().getContentLength();

            byte[] res = new byte[length.intValue()];
            response.getEntity().getContent().read(res);
            return new String(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error happen";
    }

    public String loadProductById(String index, String type, Long productId, Long tenantId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("routing", tenantId.toString());
        JsonObject query = new JsonObject();
        JsonArray array  = new JsonArray();
        array.add("series");
        array.add("id");

        query.add("_source", array);

        try {
            HttpEntity body = new StringEntity(query.toString());

            Response response = client.performRequest(
                    "GET",
                    index + "/" + type + "/" + productId + "/_source",
                    params, body);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode > 299) {
                logger.warn("Problem while indexing a document: {}", response.getStatusLine().getReasonPhrase());
                throw new ElasticAPIException("Could not index a document, status code is " + statusCode);
            }
            logger.info("Got response :{}", response.getEntity().toString());
            Long length = response.getEntity().getContentLength();
            byte[] res = new byte[length.intValue()];
            response.getEntity().getContent().read(res);
            return new String(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error happen";
    }

    public void testQuery() {
//http://10.128.161.107:9200/stores/product/_search?routing=&size=100&from=314&fields=id
        HashMap<String, String> params = new HashMap<>();
        params.put("routing", "19279769849216");
        params.put("size", "100");
        params.put("from", "314");
        params.put("fields", "id");
        try {
            Response response = client.performRequest(
                    "GET",
                    "stores/product/_search",
                    params);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode > 299) {
                logger.warn("Problem while indexing a document: {}", response.getStatusLine().getReasonPhrase());
                throw new ElasticAPIException("Could not index a document, status code is " + statusCode);
            }
            logger.info("Got response :{}", response.getEntity().toString());
            Long length = response.getEntity().getContentLength();
            byte[] res = new byte[length.intValue()];
            response.getEntity().getContent().read(res);
            String result = new String(res);
            ESQueryResponse esQueryResponse = gson.fromJson(result, ESQueryResponse.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String doQuery(String index, String type, Long tenantId, String body) {
        HashMap<String, String> params = new HashMap<>();
        params.put("routing", tenantId.toString());
        HttpEntity requestBody = new StringEntity(new String(body), Charset.defaultCharset());
        try {
            Response response = client.performRequest(
                    "GET",
                    index + "/" + type + "/_search",
                    params,
                    requestBody);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode > 299) {
                logger.warn("Problem while indexing a document: {}", response.getStatusLine().getReasonPhrase());
                throw new ElasticAPIException("Could not index a document, status code is " + statusCode);
            }
            logger.info("Got response :{}", response.getEntity().toString());
            Long length = response.getEntity().getContentLength();
            byte[] res = new byte[length.intValue()];
            response.getEntity().getContent().read(res);
            String result = new String(res);
            ESQueryResponse esQueryResponse = gson.fromJson(result, ESQueryResponse.class);

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
