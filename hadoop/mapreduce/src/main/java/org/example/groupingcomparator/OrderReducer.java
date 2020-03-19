package org.example.groupingcomparator;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/13 17:10
 */
public class OrderReducer extends Reducer<OrderBean, NullWritable, OrderBean, NullWritable> {

    @Override
    protected void reduce(OrderBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {

        // print the top 2 highest price for each order id
        int i = 0;
        for (NullWritable v : values) {
            if(i >=2) break;
            context.write(key, NullWritable.get());
            i++;
        }
    }
}
