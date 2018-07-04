package cn.itcast.bos.web.action.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.ProcessInstanceQuery;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.model.ActivityCoordinates;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.web.action.base.BaseAction;

/**
 * 流程实例 管理
 * @author Mr_Jc
 *
 */
public class ProcessInstanceAction extends BaseAction  {

	//业务方法   ----  查看正在运行的流程实例
	public String list(){
		//获得ExecutionService
		ExecutionService executionService = processEngine.getExecutionService();
		ProcessInstanceQuery query = executionService.createProcessInstanceQuery();
		List<ProcessInstance> processInstances = query.list();
		//将结果放入到值栈中
		ActionContext.getContext().put("processInstances", processInstances);
		return "listSUCCESS";
	}
	
	// 业务方法 --- 显示实例流程图
		public String showpng() {
			// 要根据 实例id 查询 发布id 和 图片 name
			ExecutionService executionService = processEngine.getExecutionService();
			ProcessInstance processInstance = executionService.createProcessInstanceQuery().processInstanceId(processInstanceId).uniqueResult();

			// 先查询流程定义
			ProcessDefinitionQuery processDefinitionQuery = processEngine.getRepositoryService().createProcessDefinitionQuery();
			ProcessDefinition processDefinition = processDefinitionQuery.processDefinitionId(processInstance.getProcessDefinitionId()).uniqueResult();

			String deploymentId = processDefinition.getDeploymentId();
			String imageResourceName = processDefinition.getImageResourceName();

			// 通过值栈传递给页面
			ActionContext.getContext().put("deploymentId", deploymentId);
			ActionContext.getContext().put("imageResourceName", imageResourceName);

			// 查看当前任务节点 坐标
			List<ActivityCoordinates> list = new ArrayList<ActivityCoordinates>();

			// 获得每个当前活动节点名称
			Set<String> activityNames = processInstance.findActiveActivityNames();
			for (String name : activityNames) {
				ActivityCoordinates activityCoordinates = processEngine.getRepositoryService().getActivityCoordinates(processDefinition.getId(), name);
				list.add(activityCoordinates);
			}
			ActionContext.getContext().put("list", list);

			return "showpngSUCCESS";
		}
	
	private String processInstanceId;
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
}
