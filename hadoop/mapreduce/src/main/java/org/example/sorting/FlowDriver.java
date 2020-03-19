package org.example.sorting;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/13 08:53
 */
public class FlowDriver {

    public static void main(String[] args) {

        String inputDir = "/opt/modules/hadoop-2.10.0/work/flow/output";
        String outputDir = "/opt/modules/hadoop-2.10.0/work/flow/output2";

        if (args.length == 2) {
            inputDir = args[0];
            outputDir = args[1];
        }

        FileUtil.fullyDelete(new File(outputDir));

        Configuration conf = new Configuration();
        try (Job job = Job.getInstance(conf)) {

            job.setJarByClass(FlowDriver.class);
            job.setMapperClass(FlowMapper.class);
            job.setReducerClass(FlowReducer.class);

            job.setMapOutputKeyClass(FlowBean.class);
            job.setMapOutputValueClass(Text.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(FlowBean.class);

            FileInputFormat.setInputPaths(job, new Path(inputDir));
            FileOutputFormat.setOutputPath(job, new Path(outputDir));

            // For partition
            job.setPartitionerClass(NumPrefixPartitioner.class);
            job.setNumReduceTasks(3);

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
