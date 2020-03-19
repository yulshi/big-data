package org.example.serializer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/11 18:37
 */
public class NetFlowMapper extends Mapper<LongWritable, Text, Text, FlowBean> {

    private final Text outKey = new Text();
    private final FlowBean flowBean = new FlowBean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();

        String[] fields = line.split("\t");
        String mobile = fields[1];
        outKey.set(mobile);
        long up = Long.parseLong(fields[fields.length - 3]);
        long down = Long.parseLong(fields[fields.length - 2]);
        flowBean.set(up, down);

        context.write(outKey, flowBean);
    }
}
