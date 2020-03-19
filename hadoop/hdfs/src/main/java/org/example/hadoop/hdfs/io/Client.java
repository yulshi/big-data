package org.example.hadoop.hdfs.io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author yulshi
 * @create 2020/02/10 13:04
 */
public class Client {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop602:9000"), conf, "yushi");

        long offset = 0;
        RemoteIterator<LocatedFileStatus> iter = fs.listFiles(new Path("/"), true);
        while (iter.hasNext()) {
            LocatedFileStatus fileStatus = iter.next();
            if (fileStatus.getPath().getName().startsWith("hadoop")) {
                System.out.println("Found: " + fileStatus.getPath().getName());
                BlockLocation[] blockLocations = fileStatus.getBlockLocations();
                BlockLocation last = blockLocations[blockLocations.length - 1];
                offset = last.getOffset();
            }
        }

        FSDataInputStream fis = fs.open(new Path("/hadoop-2.9.2.tar"));
        FileOutputStream fos = new FileOutputStream("/Users/yushi/temp/hd");
        byte[] buff = new byte[1024];
        int len = -1;
        fis.seek(offset);
        while ((len = fis.read(buff)) != -1) {
            fos.write(buff, 0, len);
        }
        fis.close();
        fos.close();
        fs.close();


    }
}
