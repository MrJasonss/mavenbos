package cn.itcast.bos.service.auth;

import java.util.List;

import cn.itcast.bos.domain.auth.Role;

/**
 * 角管理接口
 * @author Mr_Jc
 *
 */
public interface RoleService {

	/**
	 * 添加角色
	 * @param role
	 * @param functionIds
	 */
	public void saveRole(Role role, String functionIds);

	/**
	 * 查询所有的角色
	 * @return
	 */
	public List<Role> listAll();
	/**
	 * 删除角色
	 * @param ids
	 */

	public void deleteRole(String ids[],Role role, String functionIds);

}
