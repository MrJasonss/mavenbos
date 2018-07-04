package cn.itcast.bos.web.action.qp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.qp.NoticeBill;
import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.web.action.base.BaseAction;
import cn.itcast.crm.domain.Customer;

public class NoticeBillAction extends BaseAction implements ModelDriven<NoticeBill>{

	//模型驱动
	private NoticeBill noticeBill = new NoticeBill();
	
	@Override
	public NoticeBill getModel() {
		// TODO Auto-generated method stub
		return noticeBill;
	}
	
	//业务方法  新增工作单
	public String save(){
		//完善model信息  缺少一个当前用户的信息
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		noticeBill.setUser(user);
		
		//调用业务层    完善通知单新增
		noticeBillService.saveNoticeBill(noticeBill);
		return "saveSUCCESS";
	}
	
	
	//业务方法  
	/**
	 * 远程调用crm服务，根据手机号查询客户信息
	 */
	public String findCustomerByTelephone(){
		String telephone = noticeBill.getTelephone();
		List<Customer> customers = customerService.findCustomerByTelephone(telephone);
		ActionContext.getContext().put("customers", customers);
		return "findCustomerByTelephoneSUCCESS";
	}
	//业务方法    ----人工调度  未关联的
	
	public String findnoassociations(){
		List<NoticeBill> nonoticeBills  = noticeBillService.findnoassociations();
		ActionContext.getContext().put("nononoticeBills", nonoticeBills);
		return "findnoassociationsSUCCESS";
	}
	
	
	//业务方法     ----人工调度  设置取派员

	public String diaodu(){
		//String id = noticeBill.getId();
		noticeBillService.saveOrUpdate(noticeBill);
		return "diaoduSUCCESS";
	}
	
	
	//业务方法
	
	public String findassociations(){
		List<NoticeBill> noticeBills  = noticeBillService.findassociations();
		ActionContext.getContext().put("noticeBills", noticeBills);
		return "findassociationsSUCCESS";
	}

}
