package org.example.hbase.mapred.migration;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 @author yulshi
 @create 2020/03/28 13:32
 */
public class FruitDriver extends Configuration implements Tool {

  private Configuration conf;

  @Override
  public int run(String[] args) throws Exception {

    Job job = Job.getInstance(conf);

    job.setJarByClass(FruitDriver.class);

    TableMapReduceUtil.initTableMapperJob(
            "fruit",
            new Scan(),
            FruitMapper.class,
            ImmutableBytesWritable.class,
            Put.class,
            job);

    TableMapReduceUtil.initTableReducerJob(
            "fruit_mr",
            FruitReducer.class,
            job);

    boolean completion = job.waitForCompletion(true);

    return completion ? 0 : 1;
  }

  @Override
  public void setConf(Configuration conf) {
    this.conf = conf;
  }

  @Override
  public Configuration getConf() {
    return conf;
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = HBaseConfiguration.create();
    ToolRunner.run(conf, new FruitDriver(), args);
  }

}
