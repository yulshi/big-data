package org.example.hbase.mapred.migration;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.NullWritable;

import java.io.IOException;

/**
 @author yulshi
 @create 2020/03/28 13:27
 */
public class FruitReducer extends TableReducer<ImmutableBytesWritable, Put, NullWritable> {

  @Override
  protected void reduce(ImmutableBytesWritable key, Iterable<Put> values, Context context) throws IOException, InterruptedException {

    for (Put value : values) {
      context.write(NullWritable.get(), value);
    }

  }
}
