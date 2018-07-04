package cn.itcast.bos.web.action.auth;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.auth.Role;
import cn.itcast.bos.web.action.base.BaseAction;

/**
 * 
 * 角色管理
 * @author Mr_Jc
 *
 */
public class RoleAction extends BaseAction implements ModelDriven<Role>{

	//模型驱动
	private Role role = new Role();
	@Override
	public Role getModel() {
		// TODO Auto-generated method stub
		return role;
	}
	//业务方法
	public String save(){
		//调用业务层  添加角色  完成授权   
		roleService.saveRole(role,functionIds);
		
		return "saveSUCCESS";
	}
	
	//业务方法  显示所有的角色  业务方法    异步获得角色  json列表 
	public String list(){
		//调用业务方法  查询所有的的角色
		List<Role> roles = roleService.listAll();
		//放入值栈  
		ActionContext.getContext().put("roles", roles);
		return "listSUCCESS";
	}
	
	//业务方法    删除角色
	public String delete(){
		String[] ids = role.getId().split(", ");
		roleService.deleteRole(ids,role,functionIds);
		return "roledeleteSUCCESS";
	}
	
	
	
	
	
	//属性驱动    functionIds  获取前台选中的权限id
	private String functionIds;
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}
}
