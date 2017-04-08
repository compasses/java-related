package concurrency.produceconsum;

/**
 * Created by i311352 on 06/04/2017.
 */
public final class ConsumeData {
    Integer integer;

    ConsumeData(Integer integer) {
        this.integer = integer;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    @Override
    public String toString() {
        return "ConsumeData{" +
                "integer=" + integer +
                '}';
    }

}
