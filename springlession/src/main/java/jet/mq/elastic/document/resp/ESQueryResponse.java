package jet.mq.elastic.document.resp;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by I311352 on 10/17/2016.
 */
public class ESQueryResponse {
    private Long took;

    @SerializedName("timed_out")
    private Boolean timeOut;

    @SerializedName("_shards")
    private JsonObject shards;

    private Hit hits;

    public Long getTook() {
        return took;
    }

    public void setTook(Long took) {
        this.took = took;
    }

    public Boolean getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Boolean timeOut) {
        this.timeOut = timeOut;
    }

    public JsonObject getShards() {
        return shards;
    }

    public void setShards(JsonObject shards) {
        this.shards = shards;
    }

    public Hit getHits() {
        return hits;
    }

    public void setHits(Hit hits) {
        this.hits = hits;
    }

    @Override
    public String toString() {
        return "ESQueryResponse{" +
                "took=" + took +
                ", timeOut=" + timeOut +
                ", shards=" + shards +
                ", hits=" + hits +
                '}';
    }
}
