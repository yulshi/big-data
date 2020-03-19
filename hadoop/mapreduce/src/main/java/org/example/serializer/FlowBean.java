package org.example.serializer;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/11 18:37
 */
public class FlowBean implements Writable {

    private long up;
    private long down;
    private long sum;

    public FlowBean() {
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

    public void set(long up, long down) {
        this.up = up;
        this.down = down;
        this.sum = up + down;
    }

    @Override
    public String toString() {
        return up + "\t" + down + "\t" + sum;
    }

    public long getUp() {
        return up;
    }

    public long getDown() {
        return down;
    }

    public long getSum() {
        return sum;
    }
}
