package org.example.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/11 16:59
 */
public class WordCountReduder extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable outValue = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        // summerize the the word count
        int sum = 0;
        for (IntWritable value : values) {
            sum += value.get();
        }

        // Write out the output
        outValue.set(sum);
        context.write(key, outValue);
    }
}
