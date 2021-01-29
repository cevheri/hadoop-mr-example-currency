import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ReducerHadoop
        extends Reducer<Text, MinMaxDuration, Text, MinMaxDuration> {

    private final MinMaxDuration resultRow = new MinMaxDuration();

    public void reduce(Text key, Iterable<MinMaxDuration> values,
                       Context context
    ) throws IOException, InterruptedException {
        Double minVal = (double) 0;
        Double maxVal = (double) 0;

        resultRow.setMinVal(null);
        resultRow.setMaxVal(null);

        for (MinMaxDuration val : values) {

            minVal = val.getMinVal();
            maxVal = val.getMaxVal();
            // get min score

            if (resultRow.getMinVal() == null || minVal.compareTo(resultRow.getMinVal()) < 0) {
                resultRow.setMinVal(minVal);
            }            // get min bonus
            if (resultRow.getMaxVal() == null || maxVal.compareTo(resultRow.getMaxVal()) > 0) {
                resultRow.setMaxVal(maxVal);
            }
        }

        context.write(key, resultRow);
    }
}