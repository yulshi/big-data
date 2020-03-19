package org.example.sorting;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/13 08:45
 */
public class FlowMapper extends Mapper<LongWritable, Text, FlowBean, Text> {

    private final FlowBean outKey = new FlowBean();
    private final Text outValue = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();
        String[] fields = line.split("\t");

        String mobleNum = fields[0];
        long up = Long.parseLong(fields[1]);
        long down = Long.parseLong(fields[2]);
        long sum = Long.parseLong(fields[3]);

        outKey.setUp(up);
        outKey.setDown(down);
        outKey.setSum(sum);

        outValue.set(mobleNum);

        context.write(outKey, outValue);

    }
}
