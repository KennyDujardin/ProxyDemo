package hi.zhangyang.annotation.util;

import hi.zhangyang.annotation.model.Order;

public class PageHelper {
    public static Page<Order> startPage(int pageNum, int pageSize) {
        return new Page<>();
    }
}
