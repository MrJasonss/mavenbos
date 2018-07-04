package cn.itcast.bos.web.action.workflow;

import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.domain.zm.InStore;
import cn.itcast.bos.domain.zm.OutStore;
import cn.itcast.bos.domain.zm.ReceiveGoodsInfo;
import cn.itcast.bos.domain.zm.TransferInfo;
import cn.itcast.bos.service.workflow.BosTaskService;
import cn.itcast.bos.web.action.base.BaseAction;


/**
 * 进行组任务的相关操作
 * @author Mr_Jc
 *
 */
public class TaskAction extends BaseAction {
	//业务方法   查看当前业务的组任务  
	public String findgrouptask(){
		//查询组任务    使用jbpm  api    taskservice
		TaskService taskService = processEngine.getTaskService();
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		List<Task> tasks = taskService.findGroupTasks(user.getId());
		ActionContext.getContext().put("tasks", tasks);
		return "findgrouptaskSUCCESS";
	}
	
	//业务方法    办理租业务  拾取为个人任务
	public String takeTask(){
		TaskService taskService = processEngine.getTaskService();
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		taskService.takeTask(taskId, user.getId());
		return "takeTaskSUCCESS";
	}
	//业务方法  查询个人任务     
	public String findpersonaltask(){
		TaskService taskService = processEngine.getTaskService();
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		List<Task> tasks = 	taskService.findPersonalTasks(user.getId());
		ActionContext.getContext().put("tasks", tasks);
		
		return "findpersonaltaskSUCCESS";
	}
	
	
	//业务方法  办理中转环节业务
	public String saveTransferinfo(){
		//将请求数据封装到model
		TransferInfo transferInfo = new TransferInfo();
		transferInfo.setInfo(info);
		transferInfo.setArrive(arrive);
		transferInfo.setUpdateTime(new Date());
		//任务开始办理
		bosTaskService.completeTransferInfo(transferInfo,taskId);
		return "saveTransferinfoSUCCESS";
	}
	//业务方法----办理入库业务
	public String instorecomplete(){
		//将业务数据封装到PO对象
		InStore inStore = new InStore();
		inStore.setInfo(info);
		inStore.setUpdateTime(new Date());
		//调用业务层
		bosTaskService.completeInStoreTask(inStore,taskId);
		return "instorecompleteSUCCESS";
	}
	
	//业务方法----办理出库业务
	public String outstorecomplete(){
		//将业务数据封装到PO对象
		OutStore outStore = new OutStore();
		outStore.setInfo(info);
		outStore.setUpdateTime(new Date());
		//调用业务层
		bosTaskService.completeOutStoreTask(outStore,taskId);
		return "outstorecompleteSUCCESS";
	}
	
	//业务方法----办理配送业务
	public String receiveinfocomplete(){
		ReceiveGoodsInfo receiveGoodsInfo =  new ReceiveGoodsInfo();
		receiveGoodsInfo.setInfo(info);
		receiveGoodsInfo.setUpdateTime(new Date());
		
		//调用业务层
		bosTaskService.completeReceiveGoodsInfoTask(receiveGoodsInfo,taskId);
		return "receiveinfocompleteSUCCESS";
	}
	
	
	//属性驱动
	private String taskId;
	private String info;
	private String arrive;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}
	
	

}
