package hi.zhangyang.annotation.aspect;

import hi.zhangyang.annotation.util.BeanUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Aspect //把当前类标识为切面类供容器读取
public class SetFieldValueAspect {

    @Autowired
    BeanUtil beanUtil;

    @Around("@annotation(hi.zhangyang.annotation.annotation.NeedSetFieldValue)")
    public Object doSetValue(ProceedingJoinPoint pjp) throws Throwable {


        Object ret = pjp.proceed();// 在这里执行了service中的方法
        this.beanUtil.setFieldValueForCollection((Collection) ret);
        return null;
    }

}
