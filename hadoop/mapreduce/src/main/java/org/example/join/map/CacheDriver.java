package org.example.join.map;

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
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author yulshi
 * @create 2020/02/14 11:36
 */
public class CacheDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        String inputDir = "/opt/modules/hadoop-2.10.0/work/cache/input";
        String outputDir = "/opt/modules/hadoop-2.10.0/work/cache/output";
        String cacheDir = "/opt/modules/hadoop-2.10.0/work/cache/cache";

        if (args.length == 2) {
            inputDir = args[0];
            outputDir = args[1];
        }

        FileUtil.fullyDelete(new File(outputDir));

        // 1. Create the job object
        Configuration conf = new Configuration();
        try(Job job = Job.getInstance(conf)) {

            // 2. add jar location
            job.setJarByClass(CacheDriver.class);

            // 3. connect Mapper and Reducer
            job.setMapperClass(CacheMapper.class);

            // Do not need reduce task, so setting the number to 0
            job.setNumReduceTasks(0);

            job.addCacheFile(new URI(cacheDir + "/pd.txt"));

            // 5. sey the output type
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(NullWritable.class);

            FileInputFormat.addInputPath(job, new Path(inputDir));
            FileOutputFormat.setOutputPath(job, new Path(outputDir));

            // 7. submit the job
            boolean completion = job.waitForCompletion(true);
            System.exit(completion ? 0 : 1);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
