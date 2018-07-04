package cn.itcast.bos.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.struts2.ServletActionContext;

import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.utils.PrivilegeUtils;


/**
 * 自定义标签类
 * @author Mr_Jc
 *
 */
public class PrivilegeTag extends SimpleTagSupport {
	//接受属性
		private String value;  //显示内容需要的权限
		public void setValue(String value) {
			this.value = value;
		}
		@Override
		public void doTag() throws JspException, IOException {
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
			if(PrivilegeUtils.checkHasPrivilege(user, value)){
				//具有权限  显示标签体内容
				this.getJspBody().invoke(null);
			}
		}
}
