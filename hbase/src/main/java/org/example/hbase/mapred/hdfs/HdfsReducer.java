package org.example.hbase.mapred.hdfs;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.NullWritable;

import java.io.IOException;

/**
 @author yulshi
 @create 2020/03/28 15:46
 */
public class HdfsReducer extends TableReducer<NullWritable, Put, NullWritable> {

  @Override
  protected void reduce(NullWritable key, Iterable<Put> values, Context context)
          throws IOException, InterruptedException {
    for (Put put : values) {
      context.write(NullWritable.get(), put);
    }
  }

}
