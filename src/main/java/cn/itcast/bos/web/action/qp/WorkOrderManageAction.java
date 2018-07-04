package cn.itcast.bos.web.action.qp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.bos.domain.bc.Region;
import cn.itcast.bos.domain.bc.Subarea;
import cn.itcast.bos.domain.qp.WorkOrderManage;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;
import cn.itcast.bos.utils.FileUtils;
import cn.itcast.bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 工作单管理
 * 
 * @author seawind
 * 
 */
public class WorkOrderManageAction extends BaseAction implements ModelDriven<WorkOrderManage> {

	// 模型驱动
	private WorkOrderManage workOrderManage = new WorkOrderManage();

	@Override
	public WorkOrderManage getModel() {
		return workOrderManage;
	}

	// 业务方法 ---- 保存工作单
	public String saveOrUpdate() {
		// 调用业务层 完成保存操作
		try {
			workOrderManageService.saveOrUpdate(workOrderManage);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", "success");
			map.put("msg", "保存成功！");
			ActionContext.getContext().put("map", map);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", "failure");
			map.put("msg", "保存失败！异常原因：" + e.getMessage());
			ActionContext.getContext().put("map", map);
		}
		return "saveOrUpdateSUCCESS";
	}

	// 业务方法 --- 分页列表查询
	public String pageQuery() {
		if (conditionName != null && conditionName.trim().length() > 0 && conditionValue != null && conditionValue.trim().length() > 0) {
			// 有查询条件 ，结合lucene索引库查询
			PageResponseBean pageResponseBean = workOrderManageService.queryByLucene(conditionName, conditionValue, page, rows);
			ActionContext.getContext().put("pageResponseBean", pageResponseBean);
			ServletActionContext.getRequest().getSession().setAttribute("pageResponseBean", pageResponseBean);

		} else {
			// 无查询条件
			// 无条件查询所有
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(WorkOrderManage.class);
			PageRequestBean pageRequestBean = initPageRequestBean(detachedCriteria);
			// 调用业务层
			PageResponseBean pageResponseBean = workOrderManageService.pageQuery(pageRequestBean);
			ActionContext.getContext().put("pageResponseBean", pageResponseBean);
			ServletActionContext.getRequest().getSession().setAttribute("pageResponseBean", pageResponseBean);
		}

		return "pageQuerySUCCESS";
	}
	
	// 业务方法 --- 列表查询
	public String WorkOrderpageQuery() {
			// 无查询条件
			// 无条件查询所有
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(WorkOrderManage.class);
			PageRequestBean pageRequestBean = initPageRequestBean(detachedCriteria);
			// 调用业务层
			PageResponseBean pageResponseBean = workOrderManageService.pageQuery(pageRequestBean);
			ActionContext.getContext().put("pageResponseBean", pageResponseBean);
			ServletActionContext.getRequest().getSession().setAttribute("pageResponseBean", pageResponseBean);

			return "WorkOrderpageQuerySUCCESS";
	}
	
	
	//业务方法     显示所有的未审核的工作单
	
	public String list(){
		//查询未审核的工作单
		List<WorkOrderManage> workOrderManages = workOrderManageService.listUnCheckWorkOrderManages();
		ActionContext.getContext().put("workOrderManages", workOrderManages);
		
		return "listSUCCESS";
	}
	public String batchImport() throws IOException{
		// 进行Excel解析
				// 1、 工作薄对象
			
				HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(upload));
				// 解析工作薄
				hssfWorkbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK); // 避免空指针异常

				// 2、 获得Sheet
				HSSFSheet sheet = hssfWorkbook.getSheetAt(0); // 获得第一个sheet

				// 3、遍历每一行
				for (Row row : sheet) {
					// 进行解析 ， 每一行数据，对应 Region 区域信息
					if (row.getRowNum() == 0) {// 第一行（表头，无需解析）
						continue;
					}
					// 从第二行 开始解析
					WorkOrderManage workOrderManage = new WorkOrderManage();
					String id = row.getCell(0).getStringCellValue(); // 获得第一个单元格信息
					if (id.trim().equals("")) {
						// id 无值，跳过
						continue;
					}
					workOrderManage.setId(id);
					workOrderManage.setProduct(row.getCell(1).getStringCellValue());//产品
					workOrderManage.setProdtimelimit(row.getCell(2).getStringCellValue());//产品时限
					workOrderManage.setProdtype(row.getCell(3).getStringCellValue());//产品类型
					workOrderManage.setSendername(row.getCell(4).getStringCellValue());//发件人姓名
					workOrderManage.setSenderphone(row.getCell(5).getStringCellValue());//发件人电话
					workOrderManage.setSenderaddr(row.getCell(6).getStringCellValue());//发件人地址
					workOrderManage.setReceivername(row.getCell(7).getStringCellValue());//收件人姓名
					workOrderManage.setReceiverphone(row.getCell(8).getStringCellValue());//收件电话
					workOrderManage.setReceiveraddr(row.getCell(9).getStringCellValue());//收件人地址
					workOrderManage.setActlweit(Double.parseDouble(row.getCell(10).getStringCellValue()));
					
					try {
						workOrderManageService.saveWorkOrderManage(workOrderManage);
					} catch (Exception e) {
						// 导入region失败，记录日志
						LOG.error("区域导入失败，编号：" + workOrderManage.getId(), e);
					}
				}

				// 返回json
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("result", "success");
				map.put("msg", "分区导入完成");

				ActionContext.getContext().put("map", map);
				return "batchImportSUCCESS";
	}
	
	// 业务方法 ---- 数据查询结果导出
		public String exportXls() throws IOException {
			// 对文件名进行编码
			String downloadFileName = "工作单导入模板.xls";
			// 获得用户使用浏览器类型
			String agent = ServletActionContext.getRequest().getHeader("user-agent");

			// 对下载文件名编码
			downloadFileName = FileUtils.encodeDownloadFilename(downloadFileName, agent);
			// 将结果放入值栈
			ActionContext.getContext().put("downloadFileName", downloadFileName);

			return "exportXlsSUCCESS";
		}
		
		// 提供文件下载流
		public InputStream getInputStream() throws IOException {
			// 将Session 中缓存 PageResponseBean 中数据，生成Excel
			PageResponseBean pageResponseBean = (PageResponseBean) ServletActionContext.getRequest().getSession().getAttribute("pageResponseBean");
			List<WorkOrderManage> workOrderManages = pageResponseBean.getRows();
		
			// 根据内存的数据生成Excel
			// 工作薄
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			// sheet
			HSSFSheet sheet = hssfWorkbook.createSheet("工作单");
			// 先写标题行
			HSSFRow headRow = sheet.createRow(0);// 第一行 （标题行）
			headRow.createCell(0).setCellValue("编号");
			headRow.createCell(1).setCellValue("产品");
			headRow.createCell(2).setCellValue("产品时限");
			headRow.createCell(3).setCellValue("产品类型");
			headRow.createCell(4).setCellValue("发件人姓名");
			headRow.createCell(5).setCellValue("发件人电话");
			headRow.createCell(6).setCellValue("发件人地址");
			headRow.createCell(7).setCellValue("收件人姓名");
			headRow.createCell(8).setCellValue("收件人电话");
			headRow.createCell(9).setCellValue("收件人地址");
			headRow.createCell(10).setCellValue("实际重量");

			// 向excel写数据
			for (WorkOrderManage workOrderManage : workOrderManages) {
				// 每个分区一行
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(workOrderManage.getId());
				dataRow.createCell(1).setCellValue(workOrderManage.getProdtype());
				dataRow.createCell(2).setCellValue(workOrderManage.getProdtimelimit());
				dataRow.createCell(3).setCellValue(workOrderManage.getProdtype());
				dataRow.createCell(4).setCellValue(workOrderManage.getSendername());
				dataRow.createCell(5).setCellValue(workOrderManage.getSenderphone());
				dataRow.createCell(6).setCellValue(workOrderManage.getSenderaddr());
				dataRow.createCell(7).setCellValue(workOrderManage.getReceivername());
				dataRow.createCell(8).setCellValue(workOrderManage.getReceiverphone());
				dataRow.createCell(9).setCellValue(workOrderManage.getReceiveraddr());
				dataRow.createCell(10).setCellValue(workOrderManage.getActlweit());
				
			}

			// 将数据缓存到字节数组
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			hssfWorkbook.write(arrayOutputStream);
			arrayOutputStream.close();
			byte[] data = arrayOutputStream.toByteArray();

			// 再通过字节数组输入流读取数据
			return new ByteArrayInputStream(data);
		}
	
	
	
	
	//业务方法    审核工作单  接受id  顺便启动中转流程
	public String check(){
		//model中的id字段
		workOrderManageService.check(workOrderManage);
		
		return "checkSUCCESS";
	}

	// 属性驱动
	private String conditionName;
	private String conditionValue;

	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}
	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}
	// 接收上传文件
	private File upload; // upload 就是页面 上传项的name属性

	public void setUpload(File upload) {
			this.upload = upload;
	}

}
