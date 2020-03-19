package org.example.join.reduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/14 11:36
 */
public class TableDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        String inputDir = "/opt/modules/hadoop-2.10.0/work/table/input";
        String outputDir = "/opt/modules/hadoop-2.10.0/work/table/output";

        if (args.length == 2) {
            inputDir = args[0];
            outputDir = args[1];
        }

        FileUtil.fullyDelete(new File(outputDir));

        // 1. Create the job object
        Configuration conf = new Configuration();
        try(Job job = Job.getInstance(conf)) {

            // 2. add jar location
            job.setJarByClass(TableDriver.class);

            // 3. connect Mapper and Reducer
            job.setMapperClass(TableMapper.class);
            job.setReducerClass(TableReducer.class);

            // 4. set the output type of mapper
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(TableBean.class);

            // 5. sey the output type
            job.setOutputKeyClass(TableBean.class);
            job.setOutputValueClass(NullWritable.class);


            FileInputFormat.addInputPath(job, new Path(inputDir));
            FileOutputFormat.setOutputPath(job, new Path(outputDir));

            // 7. submit the job
            boolean completion = job.waitForCompletion(true);
            System.exit(completion ? 0 : 1);
        }
    }

}
