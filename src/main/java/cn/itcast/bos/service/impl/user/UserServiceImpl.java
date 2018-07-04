package cn.itcast.bos.service.impl.user;

import java.util.List;

import org.hibernate.Hibernate;
import org.jbpm.api.IdentityService;
import org.jbpm.api.identity.Group;

import cn.itcast.bos.domain.auth.Role;
import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;
import cn.itcast.bos.service.base.BaseService;
import cn.itcast.bos.service.user.UserService;
import cn.itcast.bos.utils.MD5Utils;
import cn.itcast.bos.utils.MailUitls;

public class UserServiceImpl extends BaseService implements UserService {

	@Override
	public User login(User user) {
		List<User> list = userDAO.findByNamedQuery("User.login", user.getUsername(), MD5Utils.md5(user.getPassword()));
		User loginUser =list.isEmpty() ? null : list.get(0);
		//手动对用户管理权限信息初始化
		if (loginUser!=null && loginUser.getRole()!=null) {
			Hibernate.initialize(loginUser.getRole().getFunctions());
		}
		return loginUser; 
	}

	@Override
	public void editPassword(User user) {
		// 如果修改用户所有内容，直接调用 update
		// 如果修改用户某个属性，先查询，再修改
		User exist = userDAO.findById(user.getId()); // 持久态
		exist.setPassword(MD5Utils.md5(user.getPassword()));
	}

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		//防止role   Id为空串 新建用户  但是没有选择角色
		if(user.getRole()!=null && 
				user.getRole().getId()!=null && 
				user.getRole().getId().trim().length()==0){
			user.setRole(null);
		}
		//保存用户之前  对密码加密
		user.setPassword(MD5Utils.md5(user.getPassword()));
		userDAO.save(user);
		//在添加用户的同时  向jbpm系统插入一个用户
		IdentityService identityService = processEngine.getIdentityService();
		identityService.createUser(user.getId(),user.getUsername(),user.getUsername());//建立jbpm用户
		if(user.getRole()!=null){
			//添加用户时  已经和角色建立了关系
			Role role = roleDao.findById(user.getRole().getId());
			//建立关系   jbpm组id    使用角色name属性
			identityService.createMembership(user.getId(),role.getName());
			
		}
		
		
	}

	@Override
	public List<User> listAll() {
		// TODO Auto-generated method stub
		return userDAO.findAll();
	}

	@Override
	public void grantRole(User user) {
		// TODO Auto-generated method stub
		User exit = userDAO.findById(user.getId());
		exit.setRole(user.getRole());//关联角色对象   自动更新
		//建立jbpm  用户和组的关系  一个用户只能属于一个组      
		//先删除用户和原来组的关系   然后再建立新的关系
		IdentityService identityService = processEngine.getIdentityService();
		//获得用户原来的组
		List<Group> list = identityService.findGroupsByUser(exit.getId());
		for(Group group :list){
			identityService.deleteMembership(exit.getId(), group.getId(), null);
		}
		//建立新关系
		Role role = roleDao.findById(user.getRole().getId());
		identityService.createMembership(exit.getId(),role.getName());
	}

	@Override
	public void findPassWd(User user) {
		// TODO Auto-generated method stub
		String code=user.getContext(); 
		MailUitls.sendMail(user.getEmail(), code);
	}

	@Override
	public void deleteUser(String ids[]) {
		// TODO Auto-generated method stub
		for(String id :ids){
			User user = userDAO.findById(id);
			userDAO.delete(user);
		}
	}

	@Override
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean) {
		// TODO Auto-generated method stub
		return pageQuery(pageRequestBean, userDAO);
	}

	

	

}
