package cn.itcast.bos.web.action.bc;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.bos.domain.bc.Region;
import cn.itcast.bos.domain.bc.Staff;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;
import cn.itcast.bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 取派员 信息管理
 * 
 * @author Mr_Jc
 * 
 */
public class StaffAction extends BaseAction implements ModelDriven<Staff> {

	// 模型驱动
	private Staff staff = new Staff();

	@Override
	public Staff getModel() {
		return staff;
	}

	// 业务方法 --- 保存或修改 取派员
	public String saveOrUpdate() {
		// staff对象中 关联 游离状态 Standard对象 （只有id）
		// 调用业务层
		staffService.saveOrUpdate(staff);

		return "saveOrUpdateSUCCESS";
	}

	// 业务方法 --- 分页列表查询
	public String pageQuery() {
		// 条件对象
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		if (staff.getId()!=null&&staff.getId().trim().length()>0) {
			detachedCriteria.add(Restrictions.like("id", "%"+staff.getId()+"%"));
		}
		if (staff.getName()!=null&&staff.getName().trim().length()>0) {
			detachedCriteria.add(Restrictions.like("name", "%"+staff.getName()+"%"));
		}
		if (staff.getTelephone()!=null&&staff.getTelephone().trim().length()>0) {
			detachedCriteria.add(Restrictions.like("telephone", "%"+staff.getTelephone()+"%"));
		}
		if (staff.getStation()!=null&&staff.getStation().trim().length()>0) {
			detachedCriteria.add(Restrictions.like("station", "%"+staff.getStation()+"%"));
		}
		if (staff.getStandard()!=null) {
			detachedCriteria.createAlias("standard", "s");
			if (staff.getStandard().getId()!=null&&staff.getStandard().getId().trim().length()>0) {
				detachedCriteria.add(Restrictions.like("s.id","%"+staff.getStandard().getId()+"%"));
			}
		}
		
		PageRequestBean pageRequestBean = initPageRequestBean(detachedCriteria);

		// 调用业务层，进行查询 结果PageResponseBean
		PageResponseBean pageResponseBean = staffService.pageQuery(pageRequestBean);

		// 将结果 转换 json
		ActionContext.getContext().put("pageResponseBean", pageResponseBean);

		return "pageQuerySUCCESS";
	}

	// 业务方法 --- 批量删除
	public String delBatch() {
		// 获得要作废 取派员 id
		String[] ids = staff.getId().split(", ");

		// 调用业务层，作废
		staffService.delBatch(ids);

		return "delBatchSUCCESS";
	}
	//业务方法     ---批量  还原
	public String doRestore(){
		String[] ids = staff.getId().split(", ");
		staffService.doRestore(ids);
		return "doRestoreSUCCESS";
	}
	
	
	
	// 业务方法 ---- 查询所有取派员，转换json列表
	public String ajaxlist() {
		// 调用业务层，查询所有区域信息
		List<Staff> staffs = staffService.findNoDeleteStaffs();

		// 将查询结果 转换 json格式
		ActionContext.getContext().put("staffs", staffs);// 压入struts2 值栈

		return "ajaxlistSUCCESS";	
	}

}
