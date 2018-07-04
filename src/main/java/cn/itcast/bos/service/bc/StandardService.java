package cn.itcast.bos.service.bc;

import java.util.List;

import cn.itcast.bos.domain.bc.Staff;
import cn.itcast.bos.domain.bc.Standard;
import cn.itcast.bos.page.PageQuery;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;

/**
 * 收派标准 业务接口
 * 
 * @author Mr_Jc
 * 
 */
public interface StandardService extends PageQuery {

	/**
	 * 保存标准
	 * 
	 * @param standard
	 */
	public void saveStandard(Standard standard);

	/**
	 * 分页查询
	 * 
	 * @param pageRequestBean
	 * @return
	 */
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 */
	public void delBatch(String[] ids);

	/**
	 * 获取标准列表 （查询可以使用标准 deltag=0）
	 * 
	 * @return
	 */
	public List<Standard> ajaxlist();

	

}
