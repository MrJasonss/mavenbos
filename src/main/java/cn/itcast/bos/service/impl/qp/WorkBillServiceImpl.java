package cn.itcast.bos.service.impl.qp;

import java.util.List;

import cn.itcast.bos.domain.bc.Subarea;
import cn.itcast.bos.domain.qp.WorkBill;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;
import cn.itcast.bos.service.base.BaseService;
import cn.itcast.bos.service.qp.WorkBillService;

public class WorkBillServiceImpl extends BaseService implements WorkBillService {

	

	@Override
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean) {
		// TODO Auto-generated method stub
		return pageQuery(pageRequestBean, workBillDAO);
	}

	@Override
	public void delBatch(String[] ids) {
		// TODO Auto-generated method stub
		for (String id : ids) {
			WorkBill workBill = workBillDAO.findById(id);
			workBill.setType("销");
			workBill.setPickstate("已取消");
		}
	}

	@Override
	public void doRestore(String[] ids) {
		// TODO Auto-generated method stub
		for (String id : ids) {
			WorkBill workBill = workBillDAO.findById(id);
			workBill.setAttachbilltimes(workBill.getAttachbilltimes()+1);
		}
	}
	
}
