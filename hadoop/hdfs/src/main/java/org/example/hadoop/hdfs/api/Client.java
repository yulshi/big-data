package org.example.hadoop.hdfs.api;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * @author yulshi
 * @create 2020/02/10 12:26
 */
public class Client {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {

        //listFiles();
        //copyFromLocal();
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop602:9000"), conf, "yushi");

        fs.delete(new Path("/yushi"), true);

        fs.close();

    }

    private static void copyFromLocal() throws IOException, InterruptedException, URISyntaxException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop602:9000"), conf, "yushi");

        fs.copyFromLocalFile(new Path("/Users/yushi/ssl.txt"), new Path("/user/yushi"));

        fs.close();
    }

    private static void listFiles() throws IOException, InterruptedException, URISyntaxException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop602:9000"), conf, "yushi");

        RemoteIterator<LocatedFileStatus> iterator = fs.listFiles(new Path("/"), true);
        while (iterator.hasNext()) {
            LocatedFileStatus fileStatus = iterator.next();
            System.out.println("------" + fileStatus.getPath().getName() + "-----------");
            System.out.println(fileStatus.getPermission() + "\t" + fileStatus.getOwner() + "\t" + fileStatus.getGroup());
            Arrays.asList(fileStatus.getBlockLocations()).forEach(blockLocation -> {
                System.out.println("\t" + blockLocation);
            });
        }

        fs.close();
    }
}
