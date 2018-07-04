package cn.itcast.bos.service.user;

import java.util.List;

import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.page.PageQuery;

/**
 * 用户管理 业务接口
 * 
 * @author Mr_Jc
 * 
 */
public interface UserService extends PageQuery {

	// 登陆
	public User login(User user);

	// 修改密码
	public void editPassword(User user);

	//保存用户
	public void saveUser(User user);

	//查询所有的用户
	public List<User> listAll();
	//授予角色
	public void grantRole(User user);
	//找回密码

	public void findPassWd(User user);

	public void deleteUser(String ids[]);




	

}
