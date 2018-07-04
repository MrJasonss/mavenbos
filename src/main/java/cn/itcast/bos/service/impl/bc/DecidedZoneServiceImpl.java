package cn.itcast.bos.service.impl.bc;

import cn.itcast.bos.domain.bc.DecidedZone;
import cn.itcast.bos.domain.bc.Region;
import cn.itcast.bos.domain.bc.Subarea;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;
import cn.itcast.bos.service.base.BaseService;
import cn.itcast.bos.service.bc.DecidedZoneService;

public class DecidedZoneServiceImpl extends BaseService implements DecidedZoneService {

	@Override
	public void saveOrUpdate(DecidedZone decidedZone, String[] subareaIds) {
		// 保存定区数据
		decidedZoneDAO.save(decidedZone);

		// 实现定区和分区关联（用分区 对象 关联 定区 多方关联一方）
		for (String id : subareaIds) {
			Subarea subarea = subareaDAO.findById(id); // 分区持久对象
			subarea.setDecidedZone(decidedZone);
		}
	}

	@Override
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean) {
		return pageQuery(pageRequestBean, decidedZoneDAO);
	}

	@Override
	public void delBatch(String[] ids) {
		// 直接删除选中的id
			for (String id : ids) {
					DecidedZone decidedZone = decidedZoneDAO.findById(id);
					decidedZoneDAO.delete(decidedZone);
			}
		
	}

}
