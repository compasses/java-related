package jet.mq.elastic;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by I311352 on 9/30/2016.
 */
@Component
public class ClusterFailureListener extends RestClient.FailureListener {
    private static final Logger logger = LoggerFactory.getLogger(ClusterFailureListener.class);

    @Override
    public void onFailure(HttpHost host) {
        logger.warn("Node down! {}:{}", host.getHostName(), host.getPort());
    }
}
