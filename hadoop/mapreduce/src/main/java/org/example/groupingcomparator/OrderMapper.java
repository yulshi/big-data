package org.example.groupingcomparator;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/13 17:05
 */
public class OrderMapper extends Mapper<LongWritable, Text, OrderBean, NullWritable> {

    private final OrderBean outKey = new OrderBean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();

        String[] fields = line.split("\t");
        outKey.setOrderId(fields[0]);
        outKey.setPrice(Double.parseDouble(fields[2]));

        context.write(outKey, NullWritable.get());

    }
}
