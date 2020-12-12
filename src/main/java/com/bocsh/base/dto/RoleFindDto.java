package com.bocsh.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description= "微服务定义对象")
public class RoleFindDto extends RoleSaveDto{
	
	@ApiModelProperty(value = "id")
	private long id;
	
	@ApiModelProperty(value = "角色代码")
	private String roleId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	
	
}