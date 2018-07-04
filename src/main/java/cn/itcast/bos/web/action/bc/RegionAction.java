package cn.itcast.bos.web.action.bc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.bos.domain.bc.Region;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;
import cn.itcast.bos.utils.PinYin4jUtils;
import cn.itcast.bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 区域管理
 * 
 * @author Mr_Jc
 * 
 */
public class RegionAction extends BaseAction implements ModelDriven<Region> {

	// 使用log4j 日志记录器，记录日志
	private static final Logger LOG = Logger.getLogger(RegionAction.class);

	// 模型驱动
	private Region region = new Region();

	@Override
	public Region getModel() {
		return region;
	}
	
	// 业务方法 --- 保存或修改 取派员
	public String saveOrUpdate() {
		//region对象中 关联 游离状态 Standard对象 （只有id）
		// 调用业务层
		regionService.saveOrUpdate(region);

		return "saveOrUpdateSUCCESS";
	}
	
	// 业务方法 --- 批量删除
	public String delBatch() {
			// 获得区域 id
		String[] ids = region.getId().split(", ");

			// 调用业务层，作废
		regionService.delBatch(ids);

		return "delBatchSUCCESS";
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
			Region region = new Region();
			String id = row.getCell(0).getStringCellValue(); // 获得第一个单元格信息
			if (id.trim().equals("")) {
				// id 无值，跳过
				continue;
			}
			region.setId(id);
			region.setProvince(row.getCell(1).getStringCellValue());
			region.setCity(row.getCell(2).getStringCellValue());
			region.setDistrict(row.getCell(3).getStringCellValue());
			region.setPostcode(row.getCell(4).getStringCellValue());

			// 使用pinyin4j 生成简码和城市编码
			// 连接省市区
			String str = region.getProvince() + region.getCity() + region.getDistrict();
			str = str.replaceAll("省", "").replaceAll("市", "").replaceAll("区", "");

			// 使用pinyin4j生成简码
			String[] arr = PinYin4jUtils.getHeadByString(str); // [B,J,B,J,G,D]
			StringBuffer sb = new StringBuffer();
			for (String headChar : arr) {
				sb.append(headChar);
			}
			region.setShortcode(sb.toString()); // 简码

			// 生成城市编码
			region.setCitycode(PinYin4jUtils.hanziToPinyin(region.getCity(), ""));

			// 保存region信息 (批量导入 如果出错怎么办 ？)
			try {
				regionService.saveRegion(region);
			} catch (Exception e) {
				// 导入region失败，记录日志
				LOG.error("区域导入失败，编号：" + region.getId(), e);
			}
		}

		// 返回json
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("msg", "区域导入完成");

		ActionContext.getContext().put("map", map);

		return "importXlsSUCCESS";
	}

	// 接收上传文件
	private File upload; // upload 就是页面 上传项的name属性

	public void setUpload(File upload) {
		this.upload = upload;
	}

	// 业务方法 --- 分页列表查询
	public String pageQuery() {
		// 封装PageRequestBean
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Region.class);
		if(region.getProvince()!=null&&region.getProvince().trim().length()>0){
			detachedCriteria.add(Restrictions.like("province", "%"+region.getProvince()+"%"));
		}
		if(region.getCity()!=null&&region.getCity().trim().length()>0){
			detachedCriteria.add(Restrictions.like("city", "%"+region.getCity()+"%"));
		}
		if(region.getDistrict()!=null&&region.getDistrict().trim().length()>0){
			detachedCriteria.add(Restrictions.like("district", "%"+region.getDistrict()+"%"));
		}
		if(region.getPostcode()!=null&&region.getPostcode().trim().length()>0){
			detachedCriteria.add(Restrictions.like("postcode", "%"+region.getPostcode()+"%"));
		}
		
		PageRequestBean pageRequestBean = initPageRequestBean(detachedCriteria);

		// 调用业务层 ，查询PageReponseBean对象
		PageResponseBean pageResponseBean = regionService.pageQuery(pageRequestBean);

		// 将结果转换为json
		ActionContext.getContext().put("pageResponseBean", pageResponseBean);

		return "pageQuerySUCCESS";
	}

	// 业务方法 ---- 查询所有区域，转换json列表
	public String ajaxlist() {
		// 调用业务层，查询所有区域信息
		List<Region> regions = regionService.findAllRegions();

		// 将查询结果 转换 json格式
		ActionContext.getContext().put("regions", regions);// 压入struts2 值栈

		return "ajaxlistSUCCESS";
	}
}
