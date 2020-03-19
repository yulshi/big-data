package org.example.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/11 16:53
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private Text outKey = new Text();
    private IntWritable outValue = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // Get the line
        String line = value.toString();

        // Split the line into words
        String[] words = line.split(" ");

        // Set up the output key and value
        for (String word : words) {
            outKey.set(word);
            // Write out the key and value
            context.write(outKey, outValue);
        }

    }
}
