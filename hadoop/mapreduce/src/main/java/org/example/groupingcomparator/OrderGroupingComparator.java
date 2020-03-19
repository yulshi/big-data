package org.example.groupingcomparator;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author yulshi
 * @create 2020/02/13 20:20
 */
public class OrderGroupingComparator extends WritableComparator {

    public OrderGroupingComparator() {
        super(OrderBean.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {

        OrderBean aBean = (OrderBean) a;
        OrderBean bBean = (OrderBean) b;

        if (aBean.getOrderId().equals(bBean.getOrderId())) {
            return 0;
        } else {
            return aBean.compareTo(bBean);
        }

    }
}
