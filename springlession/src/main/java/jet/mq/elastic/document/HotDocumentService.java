package jet.mq.elastic.document;

import com.google.gson.JsonObject;

/**
 * Created by I311352 on 10/25/2016.
 */
public class HotDocumentService {

    public boolean update(String index, String type, Long sourceId, ExtractUpdateFields<JsonObject, JsonObject> f) {
        JsonObject object = new JsonObject();
        JsonObject o =  f.getUpdateFields(object);
        return true;
    }
}

