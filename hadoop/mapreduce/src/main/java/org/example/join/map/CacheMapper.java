package org.example.join.map;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yulshi
 * @create 2020/02/14 12:11
 */
public class CacheMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    private final Map<String, String> cache = new HashMap<>();
    private final Text outKey = new Text();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

        URI[] cacheFiles = context.getCacheFiles();
        if (cacheFiles != null && cacheFiles.length > 0) {
            String filePath = cacheFiles[0].toString();
            FSDataInputStream inputStream = FileSystem.get(context.getConfiguration()).open(new Path(filePath));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("\t");
                cache.put(split[0], split[1]);
            }
        }

    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();
        String[] fields = line.split("\t");

        String pid = fields[1];
        String oid = fields[0];
        String amt = fields[2];

        outKey.set(oid + "\t" + cache.get(pid) + "\t" + amt);

        context.write(outKey, NullWritable.get());

    }
}
