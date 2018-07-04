package cn.itcast.bos.web.action.bc;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.bos.domain.bc.DecidedZone;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;
import cn.itcast.bos.web.action.base.BaseAction;
import cn.itcast.crm.domain.Customer;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 定区管理
 * 
 * @author Mr_Jc
 * 
 */
public class DecidedZoneAction extends BaseAction implements ModelDriven<DecidedZone> {

	// 模型驱动
	private DecidedZone decidedZone = new DecidedZone();
	private Customer customer = new Customer();
	
	public Customer getCustomer() {
		return customer;
	}

	@Override
	public DecidedZone getModel() {
		return decidedZone;
	}

	// 业务方法 ---- 添加、修改 定区
	public String saveOrUpdate() {
		// 调用业务层 ，添加定区
		decidedZoneService.saveOrUpdate(decidedZone, subareaId);
		return "saveOrUpdateSUCCESS";
	}

	// 属性驱动 接收关联分区id
	private String[] subareaId;

	public void setSubareaId(String[] subareaId) {
		this.subareaId = subareaId;
	}

	// 业务方法 --- 定区分页查询
	public String pageQuery() {
		// 封装 PageRequestBean
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DecidedZone.class);
		DetachedCriteria detachedCriteria2 = DetachedCriteria.forClass(Customer.class);
		if (decidedZone.getId()!=null&&decidedZone.getId().trim().length()>0) {
			detachedCriteria.add(Restrictions.like("id", "%"+decidedZone.getId()+"%"));
		}
		if (decidedZone.getName()!=null&&decidedZone.getName().trim().length()>0) {
			detachedCriteria.add(Restrictions.like("name", "%"+decidedZone.getName()+"%"));
		}
		if (decidedZone.getStaff()!=null) {
			detachedCriteria.createAlias("staff", "f");
			if (decidedZone.getStaff().getStation()!=null&&decidedZone.getStaff().getStation().trim().length()>0) {
				detachedCriteria.add(Restrictions.like("f.station", "%"+decidedZone.getStaff().getStation()+"%"));
			}
			if(decidedZone.getStaff().getName()!=null&&decidedZone.getStaff().getName().trim().length()>0){
				detachedCriteria.add(Restrictions.like("f.name", "%"+decidedZone.getStaff().getName()+"%"));
			}
		}
		PageRequestBean pageRequestBean = initPageRequestBean(detachedCriteria);
		// 调用业务层 ，查询PageResponseBean
		PageResponseBean pageResponseBean = decidedZoneService.pageQuery(pageRequestBean);
		// 压入值栈返回
		ActionContext.getContext().put("pageResponseBean", pageResponseBean);

		return "pageQuerySUCCESS";
	}
	
	// 业务方法 --- 查询未关联定区的客户
		public String findNoAssociationCustomers() {
			// 调用Hessian 获得远程列表
			List<Customer> customers = customerService.findNoAssociationCustomers();
			// 转换为json
			ActionContext.getContext().put("customers", customers);

			return "findNoAssociationCustomersSUCCESS";
		}

		// 业务方法 -- 查询已经关联定区的客户
		public String findHasAssociationCustomers() {
			String id = customer.getId();
			// 调用Hessian 获得远程列表
			List<Customer> customers = customerService.findHasAssociationCustomers(decidedZone.getId());
			// 转换为json
			ActionContext.getContext().put("customers", customers);

			return "findHasAssociationCustomersSUCCESS";
		}

		// 业务方法 --- 关联客户到定区
		public String assignedCustomerToDecidedZone() {
			// 指定客户到定区
			customerService.assignedCustomerToDecidedZone(customerIds, decidedZone.getId());
			return "assignedCustomerToDecidedZoneSUCCESS";
		}
		
		
		//业务方法   批量删除管理分区
		public String delBatch() {
				// 获得区域 id
			String[] ids = decidedZone.getId().split(", ");

				// 调用业务层，作废
			decidedZoneService.delBatch(ids);

			return "delBatchSUCCESS";
		}
	// 属性驱动接收多个客户id
	private String[] customerIds;

	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}

}
