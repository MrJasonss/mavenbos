package cn.itcast.bos.service.qp;

import java.util.List;

import cn.itcast.bos.domain.qp.WorkBill;
import cn.itcast.bos.page.PageQuery;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;

public interface WorkBillService extends PageQuery {

	/**
	 * 查询所有的工单
	 * @return
	 */
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean);

	/**
	 *  消单 
	 * @param ids
	 */
	public void delBatch(String[] ids);

	/**
	 * 追单
	 * @param ids
	 */
	public void doRestore(String[] ids);

}
