package org.example.serializer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/11 18:51
 */
public class NetFlowDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        String inputDir = "/opt/modules/hadoop-2.10.0/work/flow/input";
        String outputDir = "/opt/modules/hadoop-2.10.0/work/flow/output";

        if (args.length == 2) {
            inputDir = args[0];
            outputDir = args[1];
        }

        FileUtil.fullyDelete(new File(outputDir));

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(NetFlowDriver.class);
        job.setMapperClass(NetFlowMapper.class);
        job.setReducerClass(NetFlowReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        FileInputFormat.addInputPath(job, new Path(inputDir));
        FileOutputFormat.setOutputPath(job, new Path(outputDir));

        boolean completion = job.waitForCompletion(true);
        System.exit(completion ? 0 : 1);
    }
}
