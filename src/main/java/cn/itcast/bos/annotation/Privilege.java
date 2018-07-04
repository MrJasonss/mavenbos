package cn.itcast.bos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * 自定义注解
 * @author Mr_Jc
 *
 */
@Retention(RetentionPolicy.RUNTIME)
//运行时使用
@Target(value={ElementType.METHOD})
//修改方法
@Inherited
//使应用注解有继承性
public @interface Privilege {
	String value(); //这个属性表示业务访问业务方法  需要权限
}
