package cn.itcast.bos.service.impl.qp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.ExecutionService;

import cn.itcast.bos.domain.qp.WorkOrderManage;
import cn.itcast.bos.domain.zm.ZhongZhuanInfo;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;
import cn.itcast.bos.service.base.BaseService;
import cn.itcast.bos.service.qp.WorkOrderManageService;

/**
 * 工作单管理实现类
 * 
 * @author Mr_Jc
 * 
 */
public class WorkOrderManageServiceImpl extends BaseService implements WorkOrderManageService {

	@Override
	public void saveOrUpdate(WorkOrderManage workOrderManage) {
		workOrderManageDAO.saveOrUpdate(workOrderManage);
	}

	@Override
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean) {
		return pageQuery(pageRequestBean, workOrderManageDAO);
	}

	@Override
	public PageResponseBean queryByLucene(String conditionName, String conditionValue, int page, int rows) {
		return workOrderManageDAO.queryByLucene(conditionName, conditionValue, page, rows);
	}

	@Override
	public List<WorkOrderManage> listUnCheckWorkOrderManages() {
		// TODO Auto-generated method stub
		return workOrderManageDAO.findByNamedQuery("WorkOrderManage.listUnChecked");
	}

	@Override
	public void check(WorkOrderManage workOrderManage) {
		// TODO Auto-generated method stub
		//1.操作一     将工作单中的managercheck  属性设置为1
		WorkOrderManage persistWorkOrderManage = workOrderManageDAO.findById(workOrderManage.getId());
		persistWorkOrderManage.setManagerCheck("1");
		//2.操作二    启动中转流程配送
		ExecutionService executionService = processEngine.getExecutionService();
		//启动流程时  关联流程实例  对应全局中转信息对象
		ZhongZhuanInfo zhongZhuanInfo = new ZhongZhuanInfo();
		zhongZhuanInfo.setArrive("0");//未到达
		zhongZhuanInfo.setWorkOrderManage(persistWorkOrderManage);//关联工作单信息
		//对中转info进行持久化
		zhongZhuanInfoDAO.save(zhongZhuanInfo);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("zhongZhuanInfo", zhongZhuanInfo);
		executionService.startProcessInstanceByKey("transfer", variables);
	}

	@Override
	public void saveWorkOrderManage(WorkOrderManage workOrderManage) {
		// TODO Auto-generated method stub
		workOrderManageDAO.save(workOrderManage);
	}

}
