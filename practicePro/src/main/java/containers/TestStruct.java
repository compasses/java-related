package containers;

/**
 * Created by i311352 on 5/18/2017.
 */
public class TestStruct {
    private Long skuid;
    private Long uomid;
    private Double price;

    public TestStruct(Long skuid, Long uomid, Double price) {
        this.skuid = skuid;
        this.uomid = uomid;
        this.price = price;
    }

    public Long getSkuid() {
        return skuid;
    }

    public void setSkuid(Long skuid) {
        this.skuid = skuid;
    }

    public Long getUomid() {
        return uomid;
    }

    public void setUomid(Long uomid) {
        this.uomid = uomid;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getId() {
        return this.skuid.toString() + uomid.toString() + price.toString();
    }
}
