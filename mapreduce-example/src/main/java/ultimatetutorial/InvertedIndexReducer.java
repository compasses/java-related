package ultimatetutorial;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by i311352 on 17/03/2017.
 */
public class InvertedIndexReducer extends Reducer<Text,Text,Text,Text>{
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        List<String> valueList = new LinkedList<String>();
        for (Text val: values) {
            String valueAsString = new String(val.getBytes(), Charset.forName("UTF-8"));
            if (!valueList.contains(valueAsString)) {
                valueList.add(valueAsString);
            }
        }
        StringBuilder builder = new StringBuilder();
        for (String value:valueList) {
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(value);
        }
        context.write(key, new Text(builder.toString()));
    }
}
