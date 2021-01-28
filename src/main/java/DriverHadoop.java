import java.io.IOException;
import java.sql.Driver;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

public class DriverHadoop {
    public static void main(String[] args)
            throws IOException {
        String inputFolder = "input";
        String outputFolder = "output";
        if (args.length == 2) {
            inputFolder = args[0];
            outputFolder = args[1];
        }

        JobConf conf = new JobConf(DriverHadoop.class);
        conf.setJobName("ForeignCurrencyMinValue");
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
        conf.setMapperClass(MapperHadoop.class);
        conf.setCombinerClass(ReducerHadoop.class);
        conf.setReducerClass(ReducerHadoop.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);
        FileInputFormat.setInputPaths(conf, new Path(inputFolder));
        FileOutputFormat.setOutputPath(conf, new Path(outputFolder));
        JobClient.runJob(conf);
    }
}