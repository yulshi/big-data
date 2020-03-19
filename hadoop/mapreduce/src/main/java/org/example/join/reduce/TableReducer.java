package org.example.join.reduce;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yulshi
 * @create 2020/02/14 11:27
 */
public class TableReducer extends Reducer<Text, TableBean, TableBean, NullWritable> {

    @Override
    protected void reduce(Text key, Iterable<TableBean> values, Context context) throws IOException, InterruptedException {

        String productName = null;

        List<TableBean> tables = new ArrayList<>();

        for (TableBean tableBean : values) {
            if ("order".equals(tableBean.getFlag())) {

                TableBean tempBean = new TableBean();
                try {
                    BeanUtils.copyProperties(tempBean, tableBean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                tables.add(tempBean);
            } else {
                productName = tableBean.getProductName();
            }
        }

        final String finalProductName = productName;
        tables.forEach(table -> {
            try {
                table.setProductName(finalProductName);
                context.write(table, NullWritable.get());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

}
