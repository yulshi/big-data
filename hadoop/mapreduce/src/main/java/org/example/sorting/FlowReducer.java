package org.example.sorting;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/13 08:50
 */
public class FlowReducer extends Reducer<FlowBean, Text, Text, FlowBean> {

    @Override
    protected void reduce(FlowBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        for (Text mobile : values) {
            context.write(mobile, key);
        }
    }
}
