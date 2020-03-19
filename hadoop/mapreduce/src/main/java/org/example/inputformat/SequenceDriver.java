package org.example.inputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Cluster;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.File;
import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/12 11:57
 */
public class SequenceDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        String inputDir = "/opt/modules/hadoop-2.10.0/work/wordcount/input";
        String outputDir = "/opt/modules/hadoop-2.10.0/work/wordcount/output";

        if (args.length == 2) {
            inputDir = args[0];
            outputDir = args[1];
        }

        FileUtil.fullyDelete(new File(outputDir));

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(SequenceDriver.class);
        job.setMapperClass(SequenceMapper.class);
        job.setReducerClass(SequenceReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(BytesWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(BytesWritable.class);

        job.setInputFormatClass(WholeFileInputFormat.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        FileInputFormat.setInputPaths(job, new Path(inputDir));
        FileOutputFormat.setOutputPath(job, new Path(outputDir));

        boolean completion = job.waitForCompletion(true);
        System.exit(completion ? 0 : 1);

    }
}
