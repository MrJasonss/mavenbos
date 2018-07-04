package cn.itcast.bos.web.action.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.bos.annotation.Privilege;
import cn.itcast.bos.domain.auth.Role;
import cn.itcast.bos.domain.bc.Subarea;
import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;
import cn.itcast.bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 用户管理
 * 
 * @author Mr_Jc
 * 
 */
public class UserAction extends BaseAction implements ModelDriven<User> {

	// 模型驱动
	private User user = new User();

	@Override
	public User getModel() {
		return user;
	}

	// 业务方法 --- 修改密码
	public String editpassword() {
		// model 封装 用户新密码
		User loginUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		user.setId(loginUser.getId());

		// 调用业务层
		try {
			userService.editPassword(user);
			// 修改成功
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", "success");
			map.put("msg", "修改密码成功");
			ActionContext.getContext().put("map", map);
		} catch (Exception e) {
			// 修改失败
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", "failure");
			map.put("msg", "修改密码失败," + e.getMessage());
			ActionContext.getContext().put("map", map);
		}

		return "editpasswordSUCCESS";
	}
	
	
	public String findPassWd(){
		try {
			userService.findPassWd(user);
			// 发送成功
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", "success");
			map.put("msg", "发送邮件成功");
			ActionContext.getContext().put("map", map);
		}catch (Exception e) {
			// 发送失败
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", "failure");
			map.put("msg", "发送失败," + e.getMessage());
			ActionContext.getContext().put("map", map);
		}
		return "findPassWdSUCCESS";
	}
	
	//业务方法    保存用户
	@Privilege("添加用户")
	public String save(){
		//调用业务层   保存用户
		userService.saveUser(user);
		return "saveSUCCESS";
	}
	
	
	//业务方法    删除用户
	
	public String delete(){
		String[] ids = user.getId().split(", ");
		userService.deleteUser(ids);
		return "deleteSUCCESS";
	}
	//调用业务层  显示所有的用户
	 public String list(){
		 //调用业务方法
		 
		 DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
		// 针对QBC添加查询条件
		if (user.getUsername()!= null && user.getUsername() .trim().length() > 0) {
				// 添加关键字条件
			detachedCriteria.add(Restrictions.like("username", "%" + user.getUsername()  + "%"));
		}
		if (user.getGender()!= null && user.getGender() .trim().length() > 0) {
			// 添加关键字条件
		detachedCriteria.add(Restrictions.like("gender", "%" + user.getGender() + "%"));
		}
		if (user.getTelephone()!= null && user.getTelephone().trim().length() > 0) {
			// 添加关键字条件
		detachedCriteria.add(Restrictions.like("telephone", "%" + user.getTelephone()  + "%"));
		}
		PageRequestBean pageRequestBean = initPageRequestBean(detachedCriteria);

		// 调用业务层，查询PageResponseBean对象
		PageResponseBean pageResponseBean = userService.pageQuery(pageRequestBean);
		ActionContext.getContext().put("pageResponseBean", pageResponseBean);

		// 将查询结果 保存到Session
		ServletActionContext.getRequest().getSession().setAttribute("pageResponseBean", pageResponseBean);
//		 List<User> users = userService.listAll();
//		 //放入值栈
//		 ActionContext.getContext().put("users", users);
		 return "listSUCCESS";
	 }
	//业务方法  授予角色
	 public String grantRole(){
		 userService.grantRole(user);
		 
		 return "grantRoleSUCCESS";
	 }
	 

	 private String email;
	


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
