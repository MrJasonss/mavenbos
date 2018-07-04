package cn.itcast.bos.web.action.qp;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.qp.WorkBill;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;
import cn.itcast.bos.web.action.base.BaseAction;

public class WorkBillAction extends BaseAction implements ModelDriven<WorkBill> {

	private WorkBill workBill = new WorkBill();
	@Override
	public WorkBill getModel() {
		// TODO Auto-generated method stub
		return workBill;
	}
	
	
	//业务方法    工单操作   查询工单
	public String pageQuery(){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(WorkBill.class);
		
		
		if (workBill.getNoticeBill()!=null) {
			detachedCriteria.createAlias("noticebill", "n");
			detachedCriteria.add(Restrictions.like("n.id", "%"+workBill.getNoticeBill().getId()+"%"));
		}
		if (workBill.getId()!=null&&workBill.getId().trim().length()>0) {
			detachedCriteria.add(Restrictions.like("id", "%"+workBill.getId()+"%"));
		}
		if (workBill.getType()!=null&&workBill.getType().trim().length()>0) {
			detachedCriteria.add(Restrictions.like("type", "%"+workBill.getType()+"%"));
		}
		if (workBill.getPickstate()!=null&&workBill.getPickstate().trim().length()>0) {
			detachedCriteria.add(Restrictions.like("pickstate", "%"+workBill.getPickstate()+"%"));
		}
		if (workBill.getBuildtime()!=null) {
			detachedCriteria.add(Restrictions.like("buildtime", "%"+workBill.getBuildtime()+"%"));
		}
		if (workBill.getAttachbilltimes()!=null) {
			detachedCriteria.add(Restrictions.like("attachbilltimes", "%"+workBill.getAttachbilltimes()+"%"));
		}
		if (workBill.getRemark()!=null&&workBill.getRemark().trim().length()>0) {
			detachedCriteria.add(Restrictions.like("remark", "%"+workBill.getRemark()+"%"));
		}
		
		
		if (workBill.getStaff()!=null) {
			detachedCriteria.createAlias("staff", "f");
			if (workBill.getStaff().getId()!=null&&workBill.getStaff().getId().trim().length()>0) {
				detachedCriteria.add(Restrictions.like("f.id","%"+workBill.getStaff().getId()+"%" ));
			}
			if (workBill.getStaff().getName()!=null&&workBill.getStaff().getName().trim().length()>0) {
				detachedCriteria.add(Restrictions.like("f.name","%"+workBill.getStaff().getName()+"%" ));
			}
			if (workBill.getStaff().getTelephone()!=null&&workBill.getStaff().getTelephone().trim().length()>0) {
				detachedCriteria.add(Restrictions.like("f.telephone","%"+workBill.getStaff().getTelephone()+"%" ));
			}
		}
	
		
		
		PageRequestBean pageRequestBean = initPageRequestBean(detachedCriteria);
		PageResponseBean pageResponseBean = workBillService.pageQuery(pageRequestBean);
		ActionContext.getContext().put("pageResponseBean", pageResponseBean);
		return "pageQuerySUCCESS";
	}
	
	
	
	// 业务方法 --- 批量消单
	public String delBatch() {
				// 获得区域 id
		String[] ids = workBill.getId().split(", ");
				// 调用业务层，作废
		workBillService.delBatch(ids);

		return "delBatchSUCCESS";
	}
		
		//业务方法     ---批量  追单
	public String doRestore(){
		String[] ids = workBill.getId().split(", ");
		workBillService.doRestore(ids);
		return "doRestoreSUCCESS";
	}

}
