package cn.itcast.bos.service.impl.auth;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.bos.domain.auth.Function;
import cn.itcast.bos.service.auth.FunctionService;
import cn.itcast.bos.service.base.BaseService;

public class FunctionServiceImpl extends BaseService implements FunctionService {

	@Override
	public List<Function> listAll() {
		// TODO Auto-generated method stub
		return functionDao.findAll();
	}

	@Override
	public void saveFunction(Function function) {
		// TODO Auto-generated method stub
		//未选中   防止“”的外键关联
		if (function.getParentFunction()!=null && 
				function.getParentFunction().getId()!=null &&
				function.getParentFunction().getId().trim().length()==0) {
			function.setParentFunction(null);
		}
		functionDao.save(function);
	}

	@Override
	public List<Function> findTreeData(DetachedCriteria detachedCriteria) {
		// TODO Auto-generated method stub
		return functionDao.findByCriteria(detachedCriteria);
	}

}
