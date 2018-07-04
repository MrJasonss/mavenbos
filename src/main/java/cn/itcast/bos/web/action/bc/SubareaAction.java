package cn.itcast.bos.web.action.bc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.bos.domain.bc.Region;
import cn.itcast.bos.domain.bc.Subarea;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;
import cn.itcast.bos.utils.FileUtils;
import cn.itcast.bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 分区管理
 * 
 * @author Mr_Jc
 * 
 */
public class SubareaAction extends BaseAction implements ModelDriven<Subarea> {

	
	// 使用log4j 日志记录器，记录日志
	private static final Logger LOG = Logger.getLogger(SubareaAction.class);
	// 模型驱动
	private Subarea subarea = new Subarea();

	@Override
	public Subarea getModel() {
		return subarea;
	}
	
	

	// 业务方法 --- 添加和修改分区
	public String saveOrUpdate() {
		// 调用业务层
		subareaService.saveOrUpdate(subarea);
		return "saveOrUpdateSUCCESS";
	}
	
	
	// 业务方法 --- 批量删除
	public String delBatch() {
				// 获得区域 id
		String[] ids = subarea.getId().split(", ");
			// 调用业务层，作废
		subareaService.delBatch(ids);

		return "delBatchSUCCESS";
	}

	// 业务方法 --- 分页查询
	public String pageQuery() {
		// 封装PageRequestBean
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);

		// 针对QBC添加查询条件
		if (subarea.getAddresskey() != null && subarea.getAddresskey().trim().length() > 0) {
			// 添加关键字条件
			detachedCriteria.add(Restrictions.like("addresskey", "%" + subarea.getAddresskey() + "%"));
		}
		if (subarea.getDecidedZone() != null && subarea.getDecidedZone().getId() != null && subarea.getDecidedZone().getId().trim().length() > 0) {
			// 输入定区编号
			detachedCriteria.add(Restrictions.eq("decidedZone", subarea.getDecidedZone()));// 比较对象，实际上比较定区id 属性
		}
		if (subarea.getRegion() != null) {
			// 表关联，QBC解决方案 --- 别名
			detachedCriteria.createAlias("region", "r");
			if (subarea.getRegion().getProvince() != null && subarea.getRegion().getProvince().trim().length() > 0) {
				detachedCriteria.add(Restrictions.like("r.province", "%" + subarea.getRegion().getProvince() + "%"));
			}
			if (subarea.getRegion().getCity() != null && subarea.getRegion().getCity().trim().length() > 0) {
				detachedCriteria.add(Restrictions.like("r.city", "%" + subarea.getRegion().getCity() + "%"));
			}
			if (subarea.getRegion().getDistrict() != null && subarea.getRegion().getDistrict().trim().length() > 0) {
				detachedCriteria.add(Restrictions.like("r.district", "%" + subarea.getRegion().getDistrict() + "%"));
			}
		}

		PageRequestBean pageRequestBean = initPageRequestBean(detachedCriteria);

		// 调用业务层，查询PageResponseBean对象
		PageResponseBean pageResponseBean = subareaService.pageQuery(pageRequestBean);
		ActionContext.getContext().put("pageResponseBean", pageResponseBean);

		// 将查询结果 保存到Session
		ServletActionContext.getRequest().getSession().setAttribute("pageResponseBean", pageResponseBean);

		return "pageQuerySUCCESS";
	}
	
	
	// 业务方法 --- 批量导入区域信息
	public String importXls() throws IOException {
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
			Subarea subarea = new Subarea();
			Region region = new Region();
			String id = row.getCell(0).getStringCellValue(); // 获得第一个单元格信息
			if (id.trim().equals("")) {
				// id 无值，跳过
				continue;
			}
			subarea.setId(id);
			
			subarea.setAddresskey(row.getCell(4).getStringCellValue());
			subarea.setStartnum(row.getCell(5).getStringCellValue());
			subarea.setEndnum(row.getCell(6).getStringCellValue());
			subarea.setSingle(row.getCell(7).getStringCellValue());
			subarea.setPosition(row.getCell(8).getStringCellValue());
		
			try {
				subareaService.saveSubarea(subarea);
			} catch (Exception e) {
				// 导入region失败，记录日志
				LOG.error("区域导入失败，编号：" + subarea.getId(), e);
			}
		}

		// 返回json
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("msg", "分区导入完成");

		ActionContext.getContext().put("map", map);

		return "importXlsSUCCESS";
	}

	// 接收上传文件
	private File upload; // upload 就是页面 上传项的name属性

	public void setUpload(File upload) {
		this.upload = upload;
	}
	
	
	

	// 业务方法 ---- 数据查询结果导出
	public String exportXls() throws IOException {
		// 对文件名进行编码
		String downloadFileName = "分区数据.xls";
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
		List<Subarea> subareas = pageResponseBean.getRows();

		// 根据内存的数据生成Excel
		// 工作薄
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		// sheet
		HSSFSheet sheet = hssfWorkbook.createSheet("分区数据");
		// 先写标题行
		HSSFRow headRow = sheet.createRow(0);// 第一行 （标题行）
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("关键字");
		headRow.createCell(2).setCellValue("起始号");
		headRow.createCell(3).setCellValue("结束号");
		headRow.createCell(4).setCellValue("是否区分单双号号");
		headRow.createCell(5).setCellValue("位置信息");

		// 向excel写数据
		for (Subarea subarea : subareas) {
			// 每个分区一行
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getAddresskey());
			dataRow.createCell(2).setCellValue(subarea.getStartnum());
			dataRow.createCell(3).setCellValue(subarea.getEndnum());
			dataRow.createCell(4).setCellValue(subarea.getSingle());
			dataRow.createCell(5).setCellValue(subarea.getPosition());
		}

		// 将数据缓存到字节数组
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		hssfWorkbook.write(arrayOutputStream);
		arrayOutputStream.close();
		byte[] data = arrayOutputStream.toByteArray();

		// 再通过字节数组输入流读取数据
		return new ByteArrayInputStream(data);
	}
	//业务方法   关联所有的未关联的分区列表
	public String findnoassociations(){
		List<Subarea> subareas = subareaService.findnoassociations(); 
		// 将查询结果 转换 json格式
		ActionContext.getContext().put("subareas", subareas);// 压入struts2 值栈
		return "findnoassociationsSUCCESS";
	}
	
	//业务方法   ----查询区域数据  显示分区分布图
	public String findSubareasGroupByProvince(){
		List<Object> list = subareaService.findSubareasGroupByProvince();	
		ActionContext.getContext().put("provinces", list);// 压入struts2 值栈
		return "findSubareasGroupByProvinceSUCCESS";
	}

}
