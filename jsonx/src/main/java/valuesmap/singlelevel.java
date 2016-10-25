package valuesmap;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by i311352 on 6/30/16.
 */

class TestData {
    private String desc;
    private BigDecimal bigDecimal;
    private int intValue;

    @Override
    public String toString() {
        return "TestData{" +
                "desc='" + desc + '\'' +
                ", bigDecimal=" + bigDecimal +
                ", intValue=" + intValue +
                '}';
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }
}

public class singlelevel {

    public static void main(String[] args) {
        TestData testData = new TestData();
        testData.setBigDecimal(new BigDecimal(1));

        ObjectMapper objectMapper = new ObjectMapper();
        String s = null;
        try {
            s = objectMapper.writeValueAsString(testData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Json value " + s);

        TestData testData1;
        try {
            testData1 = objectMapper.readValue(s, TestData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Object from JSON " + testData);

    }
}
