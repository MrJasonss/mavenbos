package cn.itcast.bos.web.interceptor;

import java.lang.reflect.Method;

import org.apache.struts2.ServletActionContext;

import cn.itcast.bos.annotation.Privilege;
import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.utils.PrivilegeUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 权限控制的拦截器
 * @author Mr_Jc
 *
 */
@SuppressWarnings("all")
public class PrivilegeInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		//假设用户已经登录   
		//1.判断目标action的业务方法上是否有privilege注解
		
		Class c = invocation.getAction().getClass();//目标action的class对象
		String methodName = invocation.getProxy().getMethod();//目标业务方法名称
		Method method = c.getDeclaredMethod(methodName);
		if(method.isAnnotationPresent(Privilege.class)){
			//有注解   需要权限
			//2.获得注解中需要的权限
			Privilege privilege = method.getAnnotation(Privilege.class);
			String 	needPrivileg = privilege.value();
			//3.判断当前用户是否具有该权限
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");	
			if(PrivilegeUtils.checkHasPrivilege(user,needPrivileg)){
				//有权限
				return invocation.invoke();
			}else {
				//没有权限  返回没有权限的界面
				return "noprivilege";
			}
		}else {
			//无注解  不需要权限
			return invocation.invoke();
		}
	}
	
}
