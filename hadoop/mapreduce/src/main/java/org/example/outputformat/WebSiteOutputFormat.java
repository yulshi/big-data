package org.example.outputformat;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author yulshi
 * @create 2020/02/13 22:51
 */
public class WebSiteOutputFormat extends FileOutputFormat<Text, NullWritable> {
    @Override
    public RecordWriter<Text, NullWritable> getRecordWriter(TaskAttemptContext job) throws IOException, InterruptedException {

        return new WebSiteRecordWriter(job);
    }

    private class WebSiteRecordWriter extends RecordWriter<Text, NullWritable> {

        private FSDataOutputStream fosOracle;
        private FSDataOutputStream fosOther;

        public WebSiteRecordWriter(TaskAttemptContext job) {

            try (FileSystem fs = FileSystem.get(job.getConfiguration())) {
                fosOracle = fs.create(new Path("/Users/yushi/temp/oracle.txt"));
                fosOther = fs.create(new Path("/Users/yushi/temp/other.txt"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void write(Text key, NullWritable value) throws IOException, InterruptedException {

            String line = key.toString();
            if (line.contains("oracle")) {
                fosOracle.write((line + "\r\n").getBytes(StandardCharsets.UTF_8));
            } else {
                fosOther.write((line + "\r\n").getBytes(Charset.forName("utf-8")));
            }
        }

        @Override
        public void close(TaskAttemptContext context) throws IOException, InterruptedException {
            IOUtils.closeStream(fosOracle);
            IOUtils.closeStream(fosOther);
        }
    }
}
