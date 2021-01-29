
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DriverHadoop {

    public static void main(String[] args)
            throws IOException, ClassNotFoundException, InterruptedException {

        String inputFolder = "input";
        String outputFolder = "output";
        if (args.length == 2) {
            inputFolder = args[0];
            outputFolder = args[1];
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Doviz MinValue and Max Value");
        job.setJarByClass(DriverHadoop.class);
        job.setMapperClass(MapperHadoop.class);
        job.setCombinerClass(ReducerHadoop.class);
        job.setReducerClass(ReducerHadoop.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(MinMaxDuration.class);
        FileInputFormat.addInputPath(job, new Path(inputFolder));
        FileOutputFormat.setOutputPath(job, new Path(outputFolder));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}