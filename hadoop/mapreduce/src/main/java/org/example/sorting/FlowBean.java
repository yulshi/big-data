package org.example.sorting;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/13 08:41
 */
public class FlowBean implements WritableComparable<FlowBean> {

    private long up;
    private long down;
    private long sum;

    public FlowBean() {
    }

    @Override
    public int compareTo(FlowBean o) {
        return (int) (o.sum - this.sum);
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(up);
        out.writeLong(down);
        out.writeLong(sum);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        up = in.readLong();
        down = in.readLong();
        sum = in.readLong();
    }

    @Override
    public String toString() {
        return up + "\t" + down + "\t" + sum;
    }

    public long getUp() {
        return up;
    }

    public void setUp(long up) {
        this.up = up;
    }

    public long getDown() {
        return down;
    }

    public void setDown(long down) {
        this.down = down;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }
}
