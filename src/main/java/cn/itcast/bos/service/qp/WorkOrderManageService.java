package cn.itcast.bos.service.qp;

import java.util.List;

import cn.itcast.bos.domain.qp.WorkOrderManage;
import cn.itcast.bos.page.PageQuery;
import cn.itcast.bos.page.PageResponseBean;

/**
 * 工作单管理 业务接口
 * 
 * @author Mr_Jc
 * 
 */
public interface WorkOrderManageService extends PageQuery {

	/**
	 * 保存工作单信息
	 * 
	 * @param workOrderManage
	 */
	public void saveOrUpdate(WorkOrderManage workOrderManage);

	/**
	 * 结合lucene索引库进行分页查询
	 * 
	 * @param conditionName
	 * @param conditionValue
	 * @param page
	 * @param rows
	 * @return
	 */
	public PageResponseBean queryByLucene(String conditionName, String conditionValue, int page, int rows);

	/**
	 * 查询未审核的工作单    managerCheck字段为 0;
	 * @return
	 */
	public List<WorkOrderManage> listUnCheckWorkOrderManages();

	/**
	 * 审核工作单   启动中转流程
	 * @param workOrderManage
	 */
	public void check(WorkOrderManage workOrderManage);

	//业务方法   导入工作单
	public void saveWorkOrderManage(WorkOrderManage workOrderManage);

}
