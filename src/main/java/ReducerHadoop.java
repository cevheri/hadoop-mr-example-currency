import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ReducerHadoop
        extends Reducer<IntWritable, Double, IntWritable, DoubleWritable> {

    public void reduce(IntWritable key, Iterable<Double> values,
                       Context context
    ) throws IOException, InterruptedException {
        Double minVal = Double.MAX_VALUE;
        for (Double val : values) {
            if (val < minVal) {
                minVal = val;
            }
        }
        context.write(key, new DoubleWritable(minVal));
    }
}