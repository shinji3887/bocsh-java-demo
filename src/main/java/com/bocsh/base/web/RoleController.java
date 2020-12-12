package com.bocsh.base.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bocsh.base.auth.Authorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.RestTemplate;

@RestController
@Api(tags="微服务角色定义")
@RequestMapping("/roles")
public class RoleController {
	
	@Autowired
    private RestTemplate restTemplate;
	
	@ApiOperation(value="获取角色列表", notes="获取角色列表") 
    @GetMapping("/{id}")
    @Authorize({"MSA_ADMIN","MSA_USER"})
    public List getRoles(@PathVariable String id) {

	    List<Map<String,Object>> list = restTemplate.getForObject("http://bocsh-common-mjb.pub/roles/MSA",List.class);
        List<Map<String,Object>> result = new ArrayList<>();
	    String servid = id.split("-")[2].toUpperCase();
	    for(Map<String,Object> row:list){
	        String roleid = (row.get("roleId")).toString().split("_")[1];
	        if(id.equals("bocsh-service-base")){ //基础服务特殊处理
	            if(roleid.equals("ADMIN") || roleid.equals("USER")){
                    row.put("roleId",roleid);
                    result.add(row);
                }

            }
	        else{
                if(roleid.indexOf(servid)==0){ //添加服务所属角色
                    row.put("roleId",roleid);
                    result.add(row);
                }
            }

        }

        return result;
    }

    @ApiOperation(value="获取接口列表", notes="获取接口列表")
    @GetMapping("/api/{id}")
    @Authorize({"MSA_ADMIN","MSA_USER"})
    public List getApis(@PathVariable String id) {

        return restTemplate.getForObject("http://" + id + "/authorization/list",List.class);
    }

    @ApiOperation(value="获取角色关联用户列表", notes="获取角色关联用户列表")
    @GetMapping("/user/{id}")
    @Authorize({"MSA_ADMIN","MSA_USER"})
    public List getUsers(@PathVariable String id) {

        return restTemplate.getForObject("http://bocsh-common-mjb.pub/userroles/MSA_" + id,List.class);
    }
}
