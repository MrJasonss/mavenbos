package cn.itcast.bos.service.bc;

import java.util.List;

import cn.itcast.bos.domain.bc.Region;
import cn.itcast.bos.page.PageQuery;

/**
 * 区域管理 业务接口
 * 
 * @author Mr_Jc
 * 
 */
public interface RegionService extends PageQuery {

	/**
	 * 添加区域
	 * 
	 * @param region
	 */
	public void saveRegion(Region region);

	/**
	 * 查询所有区域
	 * 
	 * @return
	 */
	public List<Region> findAllRegions();
	/**
	 * 手动增加或者修改区域
	 * @param id
	 * 
	 */
	public void saveOrUpdate(Region region);
	/**
	 * 批量删除区域
	 * @param ids
	 */
	public void delBatch(String[] ids);

}
