package cn.itcast.bos.utils;

import java.util.Set;

import cn.itcast.bos.domain.auth.Function;
import cn.itcast.bos.domain.auth.Role;
import cn.itcast.bos.domain.user.User;

public class PrivilegeUtils {

	public static boolean checkHasPrivilege(User user, String needPrivileg) {
		// TODO Auto-generated method stub
		//对管理员开绿灯
		if (user.getUsername().equals("admin")) {
			return true;
		}
		
		
		Role role = user.getRole();
		if (role==null) {
			//当前用户没角色
			return false;
		}else {	
			//有角色
			
			Set<Function> functions = role.getFunctions();
			for(Function function : functions){
				if(function.getName().equals(needPrivileg)){
					//满足权限  
					return true;
				}
			}
			return false;
		}
		
	}
	
}
