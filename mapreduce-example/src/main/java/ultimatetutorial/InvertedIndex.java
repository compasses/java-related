package ultimatetutorial;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


import java.io.IOException;

/**
 * Hello world!
 *
 */
public class InvertedIndex {
    public static void main(String[] args) throws IOException, ClassNotFoundException,
    InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "inverted index");
        job.setJarByClass(InvertedIndex.class);
        job.setMapperClass(InvertedIndexMapper.class);
        job.setCombinerClass(InvertedIndexReducer.class);
        job.setReducerClass(InvertedIndexReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        String dfsPath = "hdfs://localhost:9000";
        FileInputFormat.addInputPath(job, new Path(dfsPath+"/input/resources"));
        FileOutputFormat.setOutputPath(job, new Path(dfsPath+"/output4"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}