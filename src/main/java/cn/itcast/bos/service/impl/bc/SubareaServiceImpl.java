package cn.itcast.bos.service.impl.bc;

import java.util.List;

import cn.itcast.bos.domain.bc.Region;
import cn.itcast.bos.domain.bc.Subarea;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;
import cn.itcast.bos.service.base.BaseService;
import cn.itcast.bos.service.bc.SubareaService;

public class SubareaServiceImpl extends BaseService implements SubareaService {

	@Override
	public void saveOrUpdate(Subarea subarea) {
		// 分区对象中， 关联区域的游离对象，只有id
		subareaDAO.saveOrUpdate(subarea);
	}

	@Override
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean) {
		return pageQuery(pageRequestBean, subareaDAO);
	}

	@Override
	public void saveSubarea(Subarea subarea) {
		// TODO Auto-generated method stub
		subareaDAO.save(subarea);
	}

	@Override
	public void delBatch(String[] ids) {
		// 直接删除选中的id
			for (String id : ids) {
				Subarea subarea = subareaDAO.findById(id);
				subareaDAO.delete(subarea);
			}
		
	}

	@Override
	public List<Subarea> findnoassociations() {
		
		return subareaDAO.findByNamedQuery("Subarea.findassociations");
	}

	@Override
	public List<Object> findSubareasGroupByProvince() {
		return subareaDAO.findSubareasGroupByProvince();
	}

}
