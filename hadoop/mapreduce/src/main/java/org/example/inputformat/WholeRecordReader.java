package org.example.inputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * @author yulshi
 * @create 2020/02/12 11:39
 */
public class WholeRecordReader extends RecordReader<Text, BytesWritable> {

    private final Text key = new Text();
    private final BytesWritable value = new BytesWritable();

    private Configuration conf = null;
    private Path path = null;
    private long length = 0;

    private boolean procceed = true;

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        FileSplit fileSplit = (FileSplit) split;
        path = fileSplit.getPath();
        length = fileSplit.getLength();
        conf = context.getConfiguration();
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {

        if (procceed) {
            key.set(path.toString());

            byte[] buf = new byte[(int) length];
            FSDataInputStream fis = path.getFileSystem(conf).open(path);

            IOUtils.readFully(fis, buf, 0, buf.length);
            value.set(buf, 0, buf.length);

            procceed = false;
            return true;
        }
        return false;
    }

    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {
        return key;
    }

    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    @Override
    public void close() throws IOException {

    }
}
