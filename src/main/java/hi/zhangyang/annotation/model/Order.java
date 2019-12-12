package hi.zhangyang.annotation.model;


import hi.zhangyang.annotation.annotation.NeedSetValue;
import hi.zhangyang.annotation.dao.UserDao;
import lombok.Data;

import java.io.Serializable;

@Data
public class Order implements Serializable {

    private String id;
    private String customerId;
    @NeedSetValue(beanClass = UserDao.class, param = "customerId", method = "find", targetField = "name")
    private String customerName;
}
