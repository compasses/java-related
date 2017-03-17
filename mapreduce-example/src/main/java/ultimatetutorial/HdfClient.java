package ultimatetutorial;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import java.io.IOException;

/**
 * Created by i311352 on 16/03/2017.
 */
public class HdfClient {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Please provide the HDFS path (hdfs://hosthdfs:port/path)");
        }
        String dfsPath = "hdfs://localhost:9000/input/resources";
        String hadoopConf = "/Users/i311352/hadoop-2.7.3/etc/hadoop";//System.getProperty("hadoop.conf");
        if (hadoopConf == null) {
            System.err.println("Please provide the system property hadoop.conf");
            //return;
        }
        Configuration conf = new Configuration();
        conf.addResource(new Path(hadoopConf + "/core-site.xml"));
        conf.addResource(new Path(hadoopConf + "/hdfs-site.xml"));
        conf.addResource(new Path(hadoopConf + "/mapred-site.xml"));
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        FileSystem fileSystem = FileSystem.get(conf);
        RemoteIterator<LocatedFileStatus> iterator = fileSystem.listFiles(new Path(dfsPath), false);
        while (iterator.hasNext()) {
            LocatedFileStatus fileStatus = iterator.next();
            Path path = fileStatus.getPath();
            System.out.println("Path is " + path);
        }
    }
}
