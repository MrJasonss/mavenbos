package cn.itcast.bos.web.action.storehouse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.bos.domain.bc.Region;
import cn.itcast.bos.domain.storehouse.Vendermaster;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;
import cn.itcast.bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class venderMasterAction extends BaseAction implements ModelDriven<Vendermaster> {

	
	private Vendermaster vendermaster = new Vendermaster();
	
	@Override
	public Vendermaster getModel() {
		// TODO Auto-generated method stub
		return vendermaster;
	}

	//业务方法  
	public String save(){
		System.out.println("action");
		venderMasterService.savevendermaster(vendermaster);
		return "saveSUCCESS";
	}
	
	public String pageQuery(){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Vendermaster.class);
		if (vendermaster.getVendtype()!=null&&vendermaster.getVendtype().getVendtypeId().trim().length()>0) {
			detachedCriteria.createAlias("vendtype", "v");
			detachedCriteria.add(Restrictions.like("v.vendtypeDesc", "%"+vendermaster.getVendtype().getVendtypeDesc()+"%"));
		}
		if (vendermaster.getShipvin()!=null&&vendermaster.getShipvin().getShipviaDesc().trim().length()>0) {
			detachedCriteria.createAlias("shipvin", "s");
			detachedCriteria.add(Restrictions.like("s.shipviaDesc", "%"+vendermaster.getShipvin().getShipviaDesc()+"%"));
		}
		PageRequestBean pageRequestBean = initPageRequestBean(detachedCriteria);
		// 调用业务层 ，查询PageResponseBean
		PageResponseBean pageResponseBean = venderMasterService.pageQuery(pageRequestBean);
		// 压入值栈返回
		ActionContext.getContext().put("pageResponseBean", pageResponseBean);
		return "pageQuerySUCCESS";
	}
}
