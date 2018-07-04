package cn.itcast.bos.service.impl.bc;

import java.util.List;

import cn.itcast.bos.domain.bc.Region;
import cn.itcast.bos.domain.bc.Staff;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;
import cn.itcast.bos.service.base.BaseService;
import cn.itcast.bos.service.bc.RegionService;

public class RegionServiceImpl extends BaseService implements RegionService {

	@Override
	public void saveRegion(Region region) {
		regionDAO.save(region);
	}

	@Override
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean) {
		return pageQuery(pageRequestBean, regionDAO);
	}

	@Override
	public List<Region> findAllRegions() {
		return regionDAO.findAll();
	}

	@Override
	public void saveOrUpdate(Region region) {
		// TODO Auto-generated method stub
		regionDAO.saveOrUpdate(region);
	}

	@Override
	public void delBatch(String[] ids) {
		// 直接删除选中的id
			for (String id : ids) {
				Region region = regionDAO.findById(id);
				regionDAO.delete(region);
			}
	}

}
