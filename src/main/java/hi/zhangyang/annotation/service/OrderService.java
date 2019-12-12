package hi.zhangyang.annotation.service;

import hi.zhangyang.annotation.annotation.NeedSetFieldValue;
import hi.zhangyang.annotation.dao.OrderDao;
import hi.zhangyang.annotation.model.Order;
import hi.zhangyang.annotation.util.Page;
import hi.zhangyang.annotation.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    @NeedSetFieldValue
    public Page<Order> pageQuery(String customerId, int pageNum, int pageSize) {
        Page<Order> page = PageHelper.startPage(pageNum, pageSize);
        this.orderDao.query(customerId);
        return page;
    }

}
