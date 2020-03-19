package org.example.join.reduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/14 11:15
 */
public class TableMapper extends Mapper<LongWritable, Text, Text, TableBean> {

    private String fileName;
    private final Text outKey = new Text();
    private final TableBean outValue = new TableBean();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        fileName = fileSplit.getPath().getName();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();

        if (fileName.startsWith("order")) {
            String[] fields = line.split("\t");
            outKey.set(fields[1]);
            outValue.setOrderId(fields[0]);
            outValue.setProductId(fields[1]);
            outValue.setAmount(Integer.parseInt(fields[2]));
            outValue.setProductName("");
            outValue.setFlag("order");
        } else {
            String[] fields = line.split("\t");
            outKey.set(fields[0]);
            outValue.setOrderId("");
            outValue.setProductId(fields[0]);
            outValue.setAmount(0);
            outValue.setProductName(fields[1]);
            outValue.setFlag("pd");
        }

        context.write(outKey, outValue);

    }
}
