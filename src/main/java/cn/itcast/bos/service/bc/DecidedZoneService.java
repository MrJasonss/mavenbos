package cn.itcast.bos.service.bc;

import cn.itcast.bos.domain.bc.DecidedZone;
import cn.itcast.bos.page.PageQuery;

/**
 * 定区管理 业务接口
 * 
 * @author Mr_Jc
 * 
 */
public interface DecidedZoneService extends PageQuery {

	/**
	 * 保存定区数据
	 * 
	 * @param decidedZone
	 * @param subareaId
	 */
	public void saveOrUpdate(DecidedZone decidedZone, String[] subareaId);

	//删除定区数据
	public void delBatch(String[] ids);

}
