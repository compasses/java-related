package jet.mq.elastic;

import org.apache.http.HttpHost;
import org.apache.log4j.Logger;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.sniff.Sniffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by I311352 on 9/30/2016.
 */
@Component
public class ElasticRestClient extends AbstractFactoryBean<RestClient> {
    private static Logger logger = Logger.getLogger(ElasticRestClient.class);

    private ClusterFailureListener clusterFailureListener;
    private String[] hosts;
    private Sniffer sniffer;

    @Autowired
    ElasticRestClient(ClusterFailureListener clusterFailureListener) {
        this.clusterFailureListener = clusterFailureListener;
    }
    @Override
    public Class<?> getObjectType() {
        return RestClient.class;
    }

    @Override
    protected RestClient createInstance() throws Exception {
        HttpHost[] addresses = new HttpHost[hosts.length];
        for (int i = 0; i < hosts.length; i++) {
            addresses[i] = HttpHost.create(hosts[i]);
        }
        RestClient restClient = RestClient
                .builder(addresses)
                .setFailureListener(clusterFailureListener)
                .build();

        this.sniffer = Sniffer.builder(restClient).build();
        return restClient;
    }

    @Override
    protected void destroyInstance(RestClient instance) throws Exception {
        try {
            logger.info("Closing sniffer");
            instance.close();
            this.sniffer.close();
        } catch (IOException e) {
            logger.warn("Error during close sniffer", e);
        }
    }

    @Value("${elastic.hostnames}")
    public void setHostnames(String[] hostnames) {
        this.hosts = hostnames;
    }
}
