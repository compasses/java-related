package jet.mq.elastic.document.resp;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by I311352 on 10/17/2016.
 */
public class Hits {
    @SerializedName("_index")
    private String index;
    @SerializedName("_type")
    private String type;
    @SerializedName("_id")
    private String id;
    @SerializedName("_score")
    private String score;
    @SerializedName("_routing")
    private String routing;
    @SerializedName("_source")
    private JsonObject source;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }

    public JsonObject getSource() {
        return source;
    }

    public void setSource(JsonObject source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Hits{" +
                "index='" + index + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", score='" + score + '\'' +
                ", routing='" + routing + '\'' +
                ", source=" + source +
                '}';
    }
}
