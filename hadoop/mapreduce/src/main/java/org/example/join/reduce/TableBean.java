package org.example.join.reduce;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/14 11:15
 */
public class TableBean implements Writable {

    private String orderId;
    private String productId;
    private int amount;
    private String productName;
    private String flag;

    public TableBean() {

    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(orderId);
        out.writeUTF(productId);
        out.writeInt(amount);
        out.writeUTF(productName);
        out.writeUTF(flag);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        orderId = in.readUTF();
        productId = in.readUTF();
        amount = in.readInt();
        productName = in.readUTF();
        flag = in.readUTF();
    }

    @Override
    public String toString() {
        return orderId + "\t" + productName + "\t" + amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
