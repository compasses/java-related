package transport;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.google.gson.JsonObject;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by I311352 on 11/10/2016.
 */
public class TestTransportClient {

    public static void main(String args[]) throws UnknownHostException{
        Settings settings = Settings.builder()
                                    .put("cluster.name", "SAP-AnyWhere-ES-EShop")
                                    .put("client.transport.sniff", true).build();

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.128.165.206"), 9300))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.128.165.206"), 9301));
//        String[] indexList = client.admin().cluster().prepareState().execute().actionGet().getState().getMetaData().getConcreteAllClosedIndices();
//        for (String index : indexList) {
//            System.out.println("Index name " + index);
//        }

//        //Sample Query
//        String queryString = "{\"query\":{\"query_string\":{\"query\":\"field:value\"}},\"fields\": [\"fieldname\"]}";
//
//        //Sample Query - JSONObject
//        //We convert the raw query string to JSONObject to avoid query parser error in Elasticsearch
//
//        //Elasticsearch Response
//        SearchResponse response = client.prepareSearch("indexName").setTypes("typeName").setSource(queryString).execute().actionGet();

        String index = "stores_v1";
        List<String> typeList = new ArrayList<String>();
        try {
            GetMappingsResponse res = client.admin().indices().getMappings(new GetMappingsRequest().indices(index)).get();
            ImmutableOpenMap<String, MappingMetaData> mapping = res.mappings().get(index);
            for (ObjectObjectCursor<String, MappingMetaData> c : mapping) {
                typeList.add(c.key);
            }
            TimeUnit.SECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("Type List:");
        for (String type : typeList) {
            System.out.println(type);
        }
    }


// on shutdown

}
