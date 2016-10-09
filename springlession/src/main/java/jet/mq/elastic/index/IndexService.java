package jet.mq.elastic.index;

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
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

/**
 * Created by I311352 on 9/30/2016.
 */
@Service
public class IndexService {
    private static final Logger logger = LoggerFactory.getLogger(IndexService.class);

    private final RestClient client;
    @Autowired
    public IndexService(RestClient client) {
        this.client = client;
    }

    public Boolean indexExist(String indexName) {
        try {
            Response response = client.performRequest("HEAD", indexName);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                return true;
            } else if (statusCode == 404) {
                return false;
            }
            logger.warn("Error checking index existence: {}", response.getStatusLine().getReasonPhrase());
            throw new ElasticQueryException("Cannot check index existence, code = " + statusCode);

        } catch (IOException e) {
            logger.warn("Failed to verify the index existence ", e);
            throw new ElasticAPIException("Call indexExist HEAD exception:"+e.getMessage());
        }
    }

    public void createIndex(String indexName, String requestBody) {
        try {
            HttpEntity entity = new StringEntity(requestBody);
            Response response = client.performRequest("PUT",
                    indexName,
                    new Hashtable<>(),
                    entity);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode > 299) {
                logger.warn("Error while creating an index: {}", response.getStatusLine().getReasonPhrase());
                throw new ElasticQueryException("Create index failed, status code is " + statusCode);
            }
        } catch (UnsupportedEncodingException e) {
            logger.warn("Failed to converting the request body into an http entity");
            throw new ElasticAPIException("Error of UnsupportedEncodingException ", e);
        } catch (IOException e) {
            logger.warn("Failed to creating new index.");
            throw new ElasticAPIException("Error creating new index", e);
        }
    }

}
