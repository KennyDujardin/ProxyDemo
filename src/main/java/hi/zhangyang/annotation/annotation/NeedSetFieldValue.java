package hi.zhangyang.annotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//如此以来，所有带这个注解的方法都是切面点
public @interface NeedSetFieldValue {


}
