package org.example.sorting;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;


/**
 * @author yulshi
 * @create 2020/02/13 09:20
 */
public class NumPrefixPartitioner extends Partitioner<FlowBean, Text> {

    @Override
    public int getPartition(FlowBean key, Text value, int numPartitions) {

        int partition = 2;
        String prefix = value.toString().substring(0, 3);
        if ("136".equals(prefix)) {
            partition = 0;
        } else if ("138".equals(prefix)) {
            partition = 1;
        }

        return partition;

    }
}
