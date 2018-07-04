package cn.itcast.bos.web.action.workflow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.RepositoryService;

import cn.itcast.bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**prop
 * 流程定义管理
 * 
 * @author Mr_Jc
 * 
 */
public class ProcessDefinitionAction extends BaseAction {

	// 业务方法 ----- 发布新流程定义
	public String deploy() throws IOException {
		// 上传文件应该 zip 压缩包，使用 JBPM RepositoryService 将业务流程部署到项目中
		RepositoryService repositoryService = processEngine.getRepositoryService();
		NewDeployment deployment = repositoryService.createDeployment();
		deployment.addResourcesFromZipInputStream(new ZipInputStream(new FileInputStream(deploy)));
		deployment.deploy();

		return "deploySUCCESS";
	}

	// struts2 文件上传 FileIntercetor 完成
	private File deploy;

	public void setDeploy(File deploy) {
		this.deploy = deploy;
	}

	// 业务方法 ---- 查询所有业务流程定义
	public String list() {
		// 查询所有流程定义
		RepositoryService repositoryService = processEngine.getRepositoryService();
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().orderAsc(ProcessDefinitionQuery.PROPERTY_VERSION).list(); // 所有流程定义

		// 每个相同key的流程，只显示最高版本
		// map 的key 就是 pdKey 流程关键字， map的value 就是 流程定义
		Map<String, ProcessDefinition> map = new HashMap<String, ProcessDefinition>();
		for (ProcessDefinition processDefinition : list) {
			map.put(processDefinition.getKey(), processDefinition);
		}

		// 放入值栈返回
		ActionContext.getContext().put("processDefinitions", map.values());

		return "listSUCCESS";
	}

	// 业务方法 --- 查看流程图
	public String viewpng() {
		// 获得图片资源流
		RepositoryService repositoryService = processEngine.getRepositoryService();
		in = repositoryService.getResourceAsStream(deploymentId, imageResourceName);
		return "viewpngSUCCESS";
	}

	private InputStream in;

	// 文件下载流
	public InputStream getInputStream() {
		return in;
	}

	private String deploymentId;
	private String imageResourceName;

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public void setImageResourceName(String imageResourceName) {
		this.imageResourceName = imageResourceName;
	}

}
