package org.example.outputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Cluster;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/13 22:39
 */
public class WebSiteDriver {

    public static void main(String[] args) {

        String inputDir = "/opt/modules/hadoop-2.10.0/work/website/input";
        String outputDir = "/opt/modules/hadoop-2.10.0/work/website/output";

        if (args.length == 2) {
            inputDir = args[0];
            outputDir = args[1];
        }

        FileUtil.fullyDelete(new File(outputDir));

        Configuration conf = new Configuration();
        try(Job job = Job.getInstance(conf)) {

            job.setJarByClass(WebSiteDriver.class);
            job.setMapperClass(WebSiteMapper.class);
            job.setReducerClass(WebSiteReducer.class);

            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(NullWritable.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(NullWritable.class);

            FileInputFormat.addInputPath(job, new Path(inputDir));
            FileOutputFormat.setOutputPath(job, new Path(outputDir));

            job.setOutputFormatClass(WebSiteOutputFormat.class);

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
