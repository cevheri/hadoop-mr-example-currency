import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapperHadoop
        extends Mapper<Object, Text, Text, MinMaxValue> {

    private Text year = new Text();
    private MinMaxValue outPut = new MinMaxValue();

    public void map(Object key, Text value, Context context
    ) throws IOException, InterruptedException {

        String[] line = value.toString().split(",");
        year.set(line[0].substring(6, 10));
        Double minVal = Double.parseDouble(line[1].trim());
        Double maxVal = Double.parseDouble(line[1].trim());

        try {
            outPut.setMinVal(minVal);
            outPut.setMaxVal(maxVal);
            context.write(year, outPut);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}