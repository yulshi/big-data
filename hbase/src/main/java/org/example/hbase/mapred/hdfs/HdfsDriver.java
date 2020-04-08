package org.example.hbase.mapred.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 @author yulshi
 @create 2020/03/28 15:47
 */
public class HdfsDriver extends Configuration implements Tool {

  private Configuration conf;

  @Override
  public int run(String[] args) throws Exception {

    Job job = Job.getInstance(conf);
    job.setJarByClass(HdfsDriver.class);

    job.setMapperClass(HdfsMapper.class);
    job.setMapOutputKeyClass(NullWritable.class);
    job.setMapOutputValueClass(Put.class);

    TableMapReduceUtil.initTableReducerJob("fruit", HdfsReducer.class, job);

    FileInputFormat.addInputPath(job, new Path("/opt/modules/data/hbase/fruit.tsv"));

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

    Configuration configuration = HBaseConfiguration.create();
    ToolRunner.run(configuration, new HdfsDriver(), args);

  }

}
