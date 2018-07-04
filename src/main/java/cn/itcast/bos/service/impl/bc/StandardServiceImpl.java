package cn.itcast.bos.service.impl.bc;

import java.util.List;

import cn.itcast.bos.domain.bc.Standard;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;
import cn.itcast.bos.service.base.BaseService;
import cn.itcast.bos.service.bc.StandardService;

public class StandardServiceImpl extends BaseService implements StandardService {

	@Override
	public void saveStandard(Standard standard) {
		standardDAO.saveOrUpdate(standard);
	}

	@Override
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean) {
		PageResponseBean pageResponseBean = new PageResponseBean();

		// 满足当前条件，记录总条数
		long total = standardDAO.findTotalCount(pageRequestBean.getDetachedCriteria());
		pageResponseBean.setTotal(total);

		// 查询当前页显示数据
		int firstResult = (pageRequestBean.getPage() - 1) * pageRequestBean.getRows(); // 　从哪条开始
		int maxResults = pageRequestBean.getRows(); // 返回记录数
		pageRequestBean.getDetachedCriteria().setProjection(null); // 清除之前 rowCount的投影效果
		List<Standard> data = standardDAO.pageQuery(pageRequestBean.getDetachedCriteria(), firstResult, maxResults);
		pageResponseBean.setRows(data);

		return pageResponseBean;
	}

	@Override
	public void delBatch(String[] ids) {
		// 将数据 deltag 改为 i
		for (String id : ids) {
			Standard standard = standardDAO.findById(id);
			standard.setDeltag("1");
		}
	}

	@Override
	public List<Standard> ajaxlist() {
		return standardDAO.findByNamedQuery("Standard.ajaxlist");
	}

}
