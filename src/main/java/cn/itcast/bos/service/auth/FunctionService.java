package cn.itcast.bos.service.auth;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.bos.domain.auth.Function;

//功能权限管理接口
/**
 * 
 * @author Mr_Jc
 *
 */
public interface FunctionService {

	/**
	 * 
	 * 查询所有的Function
	 * @return
	 */
	public List<Function> listAll();
	/**
	 * 保存权限
	 * @param function
	 */
	public void saveFunction(Function function);
	/**
	 * 查询树节点的数据  排序
	 * @return
	 */
	public List<Function> findTreeData(DetachedCriteria detachedCriteria);


}
