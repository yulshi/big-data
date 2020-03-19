package org.example.serializer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/11 18:48
 */
public class NetFlowReducer extends Reducer<Text, FlowBean, Text, FlowBean> {

    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {

        long sumUp = 0;
        long sumDown = 0;
        for (FlowBean flowBean : values) {
            sumUp += flowBean.getUp();
            sumDown += flowBean.getDown();
        }

        FlowBean sum = new FlowBean();
        sum.set(sumUp, sumDown);

        context.write(key, sum);

    }
}
