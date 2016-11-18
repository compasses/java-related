package elastic.transport;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by I311352 on 11/10/2016.
 */
public class TestTransportClient {

    public static void main(String args[]) throws UnknownHostException{
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.128.165.206"), 9300));
        String[] indexList = client.admin().cluster().prepareState().execute().actionGet().getState().getMetaData().getConcreteAllClosedIndices();
        for (String index : indexList) {
            System.out.println("Index name " + index);
        }
    }


// on shutdown

}
