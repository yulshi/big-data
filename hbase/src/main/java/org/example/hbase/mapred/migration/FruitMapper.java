package org.example.hbase.mapred.migration;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 @author yulshi
 @create 2020/03/28 12:07
 */
public class FruitMapper extends TableMapper<ImmutableBytesWritable, Put> {

  @Override
  protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {


    Put put = new Put(value.getRow());

    for (Cell cell : value.rawCells()) {
      if ("name".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
        put.add(cell);
      }
    }

    context.write(key, put);

  }
}
