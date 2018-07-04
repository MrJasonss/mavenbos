package cn.itcast.bos.web.action.auth;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.auth.Function;
import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.web.action.base.BaseAction;

public class FunctionAction extends BaseAction implements ModelDriven<Function>{
	//模型驱动
	private Function function = new Function();
	@Override
	public Function getModel() {
		// TODO Auto-generated method stub
		return function;
	}
	
	//业务方法   查询ajax   json列表
	public String ajaxlist(){
		//调用业务层   查询所有的function
		List<Function> functions = functionService.listAll();
		//将结果数据转换为json返回
		ActionContext.getContext().put("functions", functions);
		return "ajaxlistSUCCESS";
	}
	//业务方法    save   保存权限信息
	public String save(){
		functionService.saveFunction(function);
		return "saveSUCCESS";
	}
	
	//显示所有的权限    不分页
	public String list(){
		List<Function> functions = functionService.listAll();
		//放入值栈
		ActionContext.getContext().put("functions", functions);
		return "listSUCCESS";
	}
	//查询并且显示授权树   
	public String treedata(){
		//查询树  进行排序
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
		//按照zindex排序呢  增序
		detachedCriteria.addOrder(Order.asc("zindex"));
		
		//调用业务层
		List<Function> functions = functionService.findTreeData(detachedCriteria);
		//放入值栈
		ActionContext.getContext().put("functions", functions);
		return "treedataSUCCESS";
	}
	
	//业务方法   返回当前用户权限对应的菜单  动态菜单    多条件查询  QBC
	public String menu(){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
		//查询当前用户当前具有的菜单
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if (!user.getUsername().equals("admin")) {//如果用户是admin  那么就需要显示所有的功能
			//多表关联    每次关联一个表   就创建一个别名
			detachedCriteria.createAlias("roles", "r");
			detachedCriteria.createAlias("r.users","u");
			detachedCriteria.add(Restrictions.eq("u.id", user.getId()));
		}
		//查询  generatemenu  为1 的功能
		detachedCriteria.add(Restrictions.eq("generateMenu", "1"));
		//按照zindex排序
		detachedCriteria.addOrder(Order.asc("zindex"));
		
		//调用业务层
		List<Function> functions = functionService.findTreeData(detachedCriteria);
		ActionContext.getContext().put("functions", functions);
		return  "menuSUCCESS";
	}
	

}
