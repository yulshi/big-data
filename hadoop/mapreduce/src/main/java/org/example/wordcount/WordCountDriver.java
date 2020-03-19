package org.example.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author yulshi
 * @create 2020/02/11 17:03
 */
public class WordCountDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        String inputDir = "/opt/modules/hadoop-2.10.0/work/wordcount/input";
        String outputDir = "/opt/modules/hadoop-2.10.0/work/wordcount/output";

        if (args.length == 2) {
            inputDir = args[0];
            outputDir = args[1];
        }

        FileUtil.fullyDelete(new File(outputDir));

        // 1. Create the job object
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2. add jar location
        job.setJarByClass(WordCountDriver.class);

        // 3. connect Mapper and Reducer
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReduder.class);

        // 4. set the output type of mapper
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 5. sey the output type
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(inputDir));
        FileOutputFormat.setOutputPath(job, new Path(outputDir));

        // Compress
//        FileOutputFormat.setCompressOutput(job, true);
//        FileOutputFormat.setOutputCompressorClass(job, BZip2Codec.class);

        // 7. submit the job
        boolean completion = job.waitForCompletion(true);
        System.exit(completion ? 0 : 1);
    }

}
