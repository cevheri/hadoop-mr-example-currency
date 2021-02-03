import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;
import org.apache.hadoop.io.Text;

public class MinValueHadoop {


    public static void main(String[] args)
            throws IOException, ClassNotFoundException, InterruptedException {

        // hadoop file system
        String inputFolder = "hdfs://localhost:9000/user/cevher/input";
        String outputFolder = "hdfs://localhost:9000/user/cevher/output";

        // linux file system
        // String inputFolder_linux = "/home/cevher/apps/hadoop/input";
        // String outputFolder_linux = "/home/cevher/apps/hadoop/output";

        if (args.length == 2) {
            inputFolder = args[0];
            outputFolder = args[1];
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Currency MinValue and Max Value");
        job.setJarByClass(DriverHadoop.class);
        job.setMapperClass(MapperHadoop.class);
        job.setCombinerClass(ReducerHadoop.class);
        job.setReducerClass(ReducerHadoop.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Double.class);
        FileInputFormat.addInputPath(job, new Path(inputFolder));
        FileOutputFormat.setOutputPath(job, new Path(outputFolder));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }


    public static class MapperHadoop
            extends Mapper<LongWritable, Text, IntWritable, DoubleWritable> {

        public void map(LongWritable key, Text value, Context context
        ) throws  InterruptedException {

            String[] line = value.toString().split(",");
            int year = Integer.parseInt(line[0].substring(6, 10));
            double val = Double.parseDouble(line[1].trim());

            try {
                context.write(new IntWritable(year), new DoubleWritable(val));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ReducerHadoop
            extends Reducer<IntWritable, Double, IntWritable, DoubleWritable> {

        public void reduce(IntWritable key, Iterable<Double> values,
                           Context context
        ) throws IOException, InterruptedException {
            double minVal = Double.MAX_VALUE;
            for (double val : values) {
                if (val < minVal) {
                    minVal = val;
                }
            }
            context.write(key, new DoubleWritable(minVal));
        }
    }
}
