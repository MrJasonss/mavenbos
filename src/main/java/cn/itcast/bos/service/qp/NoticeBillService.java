package cn.itcast.bos.service.qp;

import java.util.List;

import cn.itcast.bos.domain.qp.NoticeBill;


/**
 * 业务通知单  业务接口
 * @author Mr_Jc
 *
 */

public interface NoticeBillService {

	/**
	 * 新增业务员通知单
	 * @param noticeBill
	 */
	public void saveNoticeBill(NoticeBill noticeBill);

	/**
	 * 人工调度查找出未自动分单的工单
	 * @return
	 */
	public List<NoticeBill> findnoassociations();

	/**
	 * 调度方法
	 * @param noticeBill
	 */
	public void saveOrUpdate(NoticeBill noticeBill);
	/**
	 * 查出所有的工单   人工转台
	 * @return
	 */
	public List<NoticeBill> findassociations();
	
}
