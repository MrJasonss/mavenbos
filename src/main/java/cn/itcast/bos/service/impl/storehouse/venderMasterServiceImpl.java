package cn.itcast.bos.service.impl.storehouse;

import cn.itcast.bos.domain.storehouse.Vendermaster;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;
import cn.itcast.bos.service.base.BaseService;
import cn.itcast.bos.service.storehouse.venderMasterService;

public class venderMasterServiceImpl extends BaseService implements venderMasterService {

	@Override
	public void savevendermaster(Vendermaster vendermaster) {
		// TODO Auto-generated method stub
		System.out.println("service");
		venderMasterDAO.saveOrUpdate(vendermaster);
	}

	@Override
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean) {
		// TODO Auto-generated method stub
		return pageQuery(pageRequestBean, venderDAO);
	}
	

}
