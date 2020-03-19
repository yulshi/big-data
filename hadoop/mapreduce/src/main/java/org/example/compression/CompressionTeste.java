package org.example.compression;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.*;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.*;

/**
 * @author yulshi
 * @create 2020/02/14 18:19
 */
public class CompressionTeste {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

//        compress("/Users/yushi/temp/ssl.txt");
        decompress("/Users/yushi/temp/ssl.txt.gz");
    }

    private static void compress(String fileName) throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream(new File(fileName));

        String className = "org.apache.hadoop.io.compress.GzipCodec";
        Class<?> codecClass = Class.forName(className);
        CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(codecClass, new Configuration());

//        BZip2Codec codec = new BZip2Codec();
//        DefaultCodec codec = new DefaultCodec();
//        GzipCodec codec = new GzipCodec();
//        codec.setConf(new Configuration());
        CompressionOutputStream cos = codec.createOutputStream(new FileOutputStream(fileName + codec.getDefaultExtension()));

        IOUtils.copyBytes(fis, cos, 1024 * 1024, true);

    }

    private static void decompress(String zippedFileName) throws IOException {

        CompressionCodec codec = new CompressionCodecFactory(new Configuration()).getCodec(new Path(zippedFileName));
        if (codec == null) {
            System.out.println("Can not find the codec for this file " + zippedFileName);
            return;
        }

        CompressionInputStream fis = codec.createInputStream(new FileInputStream(zippedFileName));
        FileOutputStream fos = new FileOutputStream(zippedFileName + ".decoded");

        IOUtils.copyBytes(fis, fos, 1024 * 1024, true);

    }
}
