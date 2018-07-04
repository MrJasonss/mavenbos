package cn.itcast.bos.service.impl.auth;

import java.util.List;

import org.jbpm.api.IdentityService;

import cn.itcast.bos.domain.auth.Function;
import cn.itcast.bos.domain.auth.Role;
import cn.itcast.bos.service.auth.RoleService;
import cn.itcast.bos.service.base.BaseService;

public class RoleServiceImpl extends BaseService implements RoleService {

	@Override
	public void saveRole(Role role, String functionIds) {
		// TODO Auto-generated method stub
		//将角色信息保存到角色表
		roleDao.save(role); 
		//建立role和function的联系  向role_function插入数据
		if(functionIds!=null){
			String [] ids = functionIds.split(",");
			for(String id :ids){
				Function function = functionDao.findById(id);//根据得到的id  获取权限
				role.getFunctions().add(function);//多对多关联    向中间表插入数据
			}
			
		}
		
		//建立jbpm系统中组的信息
		IdentityService identityService = processEngine.getIdentityService();
		identityService.createGroup(role.getName());//角色对应组
		
	}

	@Override
	public List<Role> listAll() {
		// TODO Auto-generated method stub
		return roleDao.findAll();
	}

	@Override
	public void deleteRole(String ids[],Role role, String functionIds) {
		// TODO Auto-generated method stub
		for(String id : ids){
			role = roleDao.findById(id);
			roleDao.delete(role);
			
			//建立role和function的联系  role_function一起删除数据
			if(functionIds!=null){
				String [] Fids = functionIds.split(",");
				for(String Fid :Fids){
					Function function = functionDao.findById(Fid);//根据得到的id  获取权限
					functionDao.delete(function);
				}
				
			}
		}
		
	
	}

	

}
