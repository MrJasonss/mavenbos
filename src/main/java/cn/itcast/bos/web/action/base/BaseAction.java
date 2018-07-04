package cn.itcast.bos.web.action.base;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.jbpm.api.ProcessEngine;

import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.service.auth.FunctionService;
import cn.itcast.bos.service.auth.RoleService;
import cn.itcast.bos.service.bc.DecidedZoneService;
import cn.itcast.bos.service.bc.RegionService;
import cn.itcast.bos.service.bc.StaffService;
import cn.itcast.bos.service.bc.StandardService;
import cn.itcast.bos.service.bc.SubareaService;
import cn.itcast.bos.service.qp.NoticeBillService;
import cn.itcast.bos.service.qp.WorkBillService;
import cn.itcast.bos.service.qp.WorkOrderManageService;
import cn.itcast.bos.service.storehouse.ShipvinServicve;
import cn.itcast.bos.service.storehouse.vendTypeService;
import cn.itcast.bos.service.storehouse.venderMasterService;
import cn.itcast.bos.service.user.UserService;
import cn.itcast.bos.service.workflow.BosTaskService;
import cn.itcast.crm.service.CustomerService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 公共 抽象Action （实现代码复用）
 * 
 * @author Mr_Jc
 * 
 */
public abstract class BaseAction extends ActionSupport {
	@Resource(name = "userService")
	protected UserService userService;

	@Resource(name = "standardService")
	protected StandardService standardService;

	@Resource(name = "staffService")
	protected StaffService staffService;

	@Resource(name = "regionService")
	protected RegionService regionService;

	@Resource(name = "subareaService")
	protected SubareaService subareaService;

	@Resource(name = "decidedZoneService")
	protected DecidedZoneService decidedZoneService;
	
	@Resource(name = "customerService")
	protected CustomerService customerService;
	
	@Resource(name = "noticeBillService")
	protected NoticeBillService noticeBillService;

	@Resource(name = "workOrderManageService")
	protected WorkOrderManageService workOrderManageService;
	
	@Resource(name = "functionService")
	protected FunctionService functionService;
	
	@Resource(name = "roleService")
	protected RoleService roleService;
	
	@Resource(name = "processEngine")
	protected ProcessEngine processEngine;
	
	@Resource(name = "bosTaskService")
	protected BosTaskService bosTaskService;
	
	@Resource(name = "workBillService")
	protected WorkBillService workBillService;
	
	@Resource(name = "venderMasterService")
	protected venderMasterService venderMasterService;
	
	@Resource(name = "vendTypeService")
	protected vendTypeService vendTypeService;
	
	@Resource(name = "shipVinService")
	protected ShipvinServicve shipVinService ;
	
	
	
	// 属性驱动，封装分页参数
	protected int page;
	protected int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	// 封装PageRequestBean
	public PageRequestBean initPageRequestBean(DetachedCriteria detachedCriteria) {
		// 将分页查询参数 ，封装 PageRequestBean 对象中
		PageRequestBean pageRequestBean = new PageRequestBean();
		pageRequestBean.setPage(page);
		pageRequestBean.setRows(rows);

		pageRequestBean.setDetachedCriteria(detachedCriteria);
		return pageRequestBean;
	}
}
