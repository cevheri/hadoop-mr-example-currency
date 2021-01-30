import org.apache.hadoop.io.*;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MinMaxValue
        implements Writable {
    private Double minVal;
    private Double maxVal;
    public MinMaxValue(){
        minVal= Double.valueOf(0);
        maxVal= Double.valueOf(0);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeDouble(minVal);
        dataOutput.writeDouble(maxVal);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        minVal = dataInput.readDouble();
        maxVal = dataInput.readDouble();
    }


    public Double getMinVal() {
        return minVal;
    }

    public void setMinVal(Double minVal) {
        this.minVal = minVal;
    }

    public Double getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(Double maxVal) {
        this.maxVal = maxVal;
    }

    public String toString() {
        return minVal + "\t" + maxVal;
    }
}
