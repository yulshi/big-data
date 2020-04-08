package org.example.hbase.mapred.hdfs;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 @author yulshi
 @create 2020/03/28 13:58
 */
public class HdfsMapper extends Mapper<LongWritable, Text, NullWritable, Put> {

  @Override
  protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

    String line = value.toString();
    String[] split = line.split("\t");

    String rowKey = split[0];
    String name = split[1];
    String color = split[2];

    Put put = new Put(Bytes.toBytes(rowKey));
    put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes(name));
    put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("color"), Bytes.toBytes(color));

    context.write(NullWritable.get(), put);

  }
}
