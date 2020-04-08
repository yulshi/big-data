package org.example.groupingcomparator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;

/**
 *
 * order.txt
 *
 * 00001   Washing Machine  2000
 * 00005   Fredg            5000
 * 00002   Chair            88
 * 00003   Table            200
 * 00002   Clothes          370
 * 00007   Television       2000
 * 00001   Flower           40
 * 00002   Oven             300
 * 00007   Telephoe         280
 * 00003   Toys             290
 * 00004   Picture          300
 * 00003   Printer          490
 * 00004   Glasses          500
 * 00003   Cups             209
 *
 * output:
 *
 * print the top 2 highest price for each order id
 *
 *
 * @author yulshi
 * @create 2020/02/13 17:14
 */
public class OrderDriver {

  public static void main(String[] args) {

    String inputDir = "/opt/modules/hadoop-2.10.0/work/order/input";
    String outputDir = "/opt/modules/hadoop-2.10.0/work/order/output";

    if (args.length == 2) {
      inputDir = args[0];
      outputDir = args[1];
    }

    FileUtil.fullyDelete(new File(outputDir));

    Configuration conf = new Configuration();
    try (Job job = Job.getInstance(conf)) {

      job.setJarByClass(OrderDriver.class);
      job.setMapperClass(OrderMapper.class);
      job.setReducerClass(OrderReducer.class);

      job.setMapOutputKeyClass(OrderBean.class);
      job.setMapOutputValueClass(NullWritable.class);

      job.setOutputKeyClass(OrderBean.class);
      job.setOutputValueClass(NullWritable.class);

      FileInputFormat.setInputPaths(job, new Path(inputDir));
      FileOutputFormat.setOutputPath(job, new Path(outputDir));

      job.setGroupingComparatorClass(OrderGroupingComparator.class);

      boolean completion = job.waitForCompletion(true);
      System.exit(completion ? 0 : 1);

    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
