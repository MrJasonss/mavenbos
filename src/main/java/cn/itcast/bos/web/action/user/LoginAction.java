package cn.itcast.bos.web.action.user;

import org.apache.struts2.ServletActionContext;

import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 登陆功能
 * 
 * @author Mr_Jc
 * 
 */
public class LoginAction extends BaseAction implements ModelDriven<User> {

	// 模型驱动
	private User user = new User();

	@Override
	public User getModel() {
		return user;
	}

	// 业务方法 --- 登陆逻辑
	public String execute() {
		// 判断验证码是否 正确(比较用户输入的验证码和Session中验证码 是否一致)
		// 获得Session验证码
		String checkCodeSession = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if (checkCodeSession == null || !checkCodeSession.equals(checkcode)) {
			// 验证码失败
			
			this.addActionError("验证码错误");
			return INPUT;
		}
		// 验证码成功，比较用户名和密码 ,调用业务层
		User loginUser = userService.login(user);
		if (loginUser == null) {
			// 登陆失败
			this.addActionError("用户名或者密码错误");
			return INPUT;
		} else {
			// 登陆成功
			// 将用户信息存入Session
			ServletActionContext.getRequest().getSession().setAttribute("user", loginUser);
			return SUCCESS;
		}
	}

	// 接收验证码 （属性驱动 ）
	private String checkcode; // 用户输入验证码

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

}
