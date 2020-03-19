package org.example.groupingcomparator;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/13 16:53
 */
public class OrderBean implements WritableComparable<OrderBean> {

    private String orderId;
    private double price;

    @Override
    public int compareTo(OrderBean o) {

        int result = this.orderId.compareTo(o.orderId);
        if(result == 0) {
            if (this.price > o.price) {
                result = -1;
            } else if (this.price < o.price) {
                result = 1;
            }
        }

        return result;

    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(orderId);
        out.writeDouble(price);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        orderId = in.readUTF();
        price = in.readDouble();
    }

    @Override
    public String toString() {
        return orderId + "\t" + price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
