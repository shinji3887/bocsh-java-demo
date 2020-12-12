package com.bocsh.base.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bocsh.base.domain.Role;
import com.bocsh.base.domain.RoleRepository;
import com.bocsh.base.dto.RoleFindDto;
import com.bocsh.base.dto.RoleSaveDto;
import com.bocsh.base.utils.DtoUtils;

@Service
public class RoleService {
	
	@Autowired
    RoleRepository roleRepository;
	
    public List<RoleFindDto> getAll(String serviceId) throws Exception{
    	
    	List<Role> list = roleRepository.findAllByServiceId(serviceId,Sort.by(Direction.ASC,"roleId"));
		List<RoleFindDto> findlist = new ArrayList<RoleFindDto>();
		
		for(Role role:list){
			RoleFindDto finddto = DtoUtils.convertNew(role,RoleFindDto.class);
			findlist.add(finddto);
		}
    	
    	return findlist;
    }
    
    public void add(RoleSaveDto roledto) throws Exception{
    	
    	Role role = DtoUtils.convertNew(roledto,Role.class);
    	roleRepository.save(role);
    }
    
    public void update(RoleSaveDto roledto,Long id) throws Exception{
    	
    	Role role = roleRepository.findById(id).get();
    	role = DtoUtils.convertUpdate(roledto,role);
    	roleRepository.save(role);
    }
    
    public void delete(Long id) throws Exception{
    	
    	roleRepository.deleteById(id);
    }
    
    public boolean exists(Long id) throws Exception{
    	
    	if(!roleRepository.existsById(id)){
    		return false;
    	}
    	else {
    		return true;
    	}
    }
}
