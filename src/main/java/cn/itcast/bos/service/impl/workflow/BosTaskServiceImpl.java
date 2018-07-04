package cn.itcast.bos.service.impl.workflow;

import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;

import cn.itcast.bos.domain.zm.InStore;
import cn.itcast.bos.domain.zm.OutStore;
import cn.itcast.bos.domain.zm.ReceiveGoodsInfo;
import cn.itcast.bos.domain.zm.TransferInfo;
import cn.itcast.bos.domain.zm.ZhongZhuanInfo;
import cn.itcast.bos.service.base.BaseService;
import cn.itcast.bos.service.workflow.BosTaskService;

public class BosTaskServiceImpl extends BaseService implements BosTaskService {


	@Override
	public void completeTransferInfo(TransferInfo transferInfo, String taskId) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		//1.业务数据持久化
		transferInfoDAO.save(transferInfo);
		//2.将业务数据关联到流程变量
		TaskService taskService = processEngine.getTaskService();
		ZhongZhuanInfo zhongZhuanInfo = (ZhongZhuanInfo) taskService.getVariable(taskId,"zhongZhuanInfo");
		zhongZhuanInfo.setArrive(transferInfo.getArrive());
		zhongZhuanInfo.getTransferInfos().add(transferInfo);//向集合中添加一个中转环节信息
		
		//3.办理任务自动流转
		if (zhongZhuanInfo.getArrive().equals("0")) {
			//未到达   继续中转  不能走下面
			//使用自由流转实现
			Task task = taskService.getTask(taskId);
			completeTaskByCreateTransiton(task, "中转环节", "to 中转环节xxx");
		}else {
			//到达
			taskService.completeTask(taskId,"to 入库");
		}
		
	}
 
	/**
	 * 自由流转的三个参数
	 * @param task  当前任务
 	 * @param destActivityName  目标节点的name属性
	 * @param createTransitionName   流转transition的name属性
	 */
	public void completeTaskByCreateTransiton(Task task, String destActivityName,String createTransitionName){
	     //这里不会影响事务  
	     EnvironmentImpl envImpl = ((EnvironmentFactory)processEngine).openEnvironment();  //开启环境
	     try {  
	           
	         //动态回退
	    	 //获得流程实例对象
	         ExecutionImpl e = (ExecutionImpl)processEngine.getExecutionService().findExecutionById(task.getExecutionId());  
	         //获得活动对象     --中转环节
	         ActivityImpl currentActivityImpl = e.getActivity();  
	         //获得流程 定义 jbdl.xml文件  
	         ProcessDefinitionImpl processDefinitionImpl = currentActivityImpl.getProcessDefinition();  
	           
	         //生成一个transition  
	         ActivityImpl destActivityImpl = processDefinitionImpl.findActivity(destActivityName);  
	         //动态创建transition 流转
	         TransitionImpl toApply = currentActivityImpl.createOutgoingTransition();  
	         toApply.setSource(currentActivityImpl); //流转出发点  
	         toApply.setDestination(destActivityImpl);  //流转目标点
	         toApply.setName(createTransitionName);  //为流转命名
	         processEngine.getTaskService().completeTask(task.getId(),createTransitionName); //完成任务  使用动态创建流转
	     } catch (Exception e) {  
	         e.printStackTrace();  
	     }finally{  
	         envImpl.close();  
	     }  
	 }

	@Override
	public void completeInStoreTask(InStore inStore, String taskId) {
		// TODO Auto-generated method stub
		//1.业务数据持久化
		inStoreDAO.save(inStore);
		//2.将业务数据关联到流程变量
		TaskService taskService = processEngine.getTaskService();
		ZhongZhuanInfo zhongZhuanInfo = (ZhongZhuanInfo) taskService.getVariable(taskId,"zhongZhuanInfo");
		zhongZhuanInfo.setInStore(inStore);
		//3.办理任务自动流转
		taskService.completeTask(taskId,"to 出库");
	}

	@Override
	public void completeOutStoreTask(OutStore outStore, String taskId) {
		// TODO Auto-generated method stub
		//1.业务数据持久化
		outStoreDAO.save(outStore);
		//2.将业务数据关联到流程变量
		TaskService taskService = processEngine.getTaskService();
		ZhongZhuanInfo zhongZhuanInfo = (ZhongZhuanInfo) taskService.getVariable(taskId,"zhongZhuanInfo");
		zhongZhuanInfo.setOutStore(outStore);
		//3.办理任务自动流转
		taskService.completeTask(taskId,"to 配送签收");
	}

	@Override
	public void completeReceiveGoodsInfoTask(ReceiveGoodsInfo receiveGoodsInfo,
			String taskId) {
		// TODO Auto-generated method stub
		//1.业务数据持久化
		receiveGoodsInfoDAO.save(receiveGoodsInfo);
		//2.将业务数据关联到流程变量
		TaskService taskService = processEngine.getTaskService();
		ZhongZhuanInfo zhongZhuanInfo = (ZhongZhuanInfo) taskService.getVariable(taskId,"zhongZhuanInfo");
		zhongZhuanInfo.setReceiveGoodsInfo(receiveGoodsInfo);
		//3.办理任务自动流转
		taskService.completeTask(taskId,"to end1");
	}
}
