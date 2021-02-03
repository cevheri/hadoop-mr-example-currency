import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapperHadoop
        extends Mapper<LongWritable, Text, IntWritable, DoubleWritable> {

    public void map(LongWritable key, Text value, Context context
    ) throws  InterruptedException {

        String[] line = value.toString().split(",");
        int year = Integer.parseInt(line[0].substring(6, 10));
        double val = Double.parseDouble(line[1].trim());

        try {
            context.write(new IntWritable(year), new DoubleWritable(val));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}