package cn.itcast.bos.service.bc;

import java.util.List;

import cn.itcast.bos.domain.bc.Subarea;
import cn.itcast.bos.page.PageQuery;

/**
 * 分区管理 业务接口
 * 
 * @author Mr_Jc
 * 
 */
public interface SubareaService extends PageQuery {

	/**
	 * 添加或者修改分区
	 * 
	 * @param subarea
	 */
	public void saveOrUpdate(Subarea subarea);

	/**
	 * 
	 * 导入的方法增加信息
	 * @return
	 */
	public void saveSubarea(Subarea subarea);

	/**
	 * 批量删除分区
	 * @param ids
	 */
	public void delBatch(String[] ids);

	/**
	 *  查询  关联所有的未关联的分区列表
	 * @return
	 */
	public List<Subarea> findnoassociations();
	/**
	 * 查询区域分布情况
	 * @return
	 */
	public List<Object> findSubareasGroupByProvince();


}
