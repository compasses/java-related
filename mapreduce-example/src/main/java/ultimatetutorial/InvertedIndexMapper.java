package ultimatetutorial;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by i311352 on 17/03/2017.
 */
public class InvertedIndexMapper extends Mapper<Object, Text, Text, Text> {
    private static final Log LOG = LogFactory.getLog(InvertedIndexMapper.class);

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        StringTokenizer tokenizer = new StringTokenizer(value.toString(), ".,; \t\n?!\"/()[]$%");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            Text keyOut = new Text(token);
            String fileName = ((FileSplit) context.getInputSplit()).getPath().toString();
            Text valueOut = new Text(fileName);
            LOG.info("Key/Value: " + keyOut + "/" + valueOut);
            context.write(keyOut, valueOut);
        }
    }
}
