package com.bocsh.base.dto;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description= "微服务角色对象")
public class RoleSaveDto{
	
	@ApiModelProperty(value = "服务编号",required=true,allowableValues = "range[1,30]")
	@Length(min=1,max=30)
	@NotBlank
	private String serviceId;
	
	@ApiModelProperty(value = "角色代码",required=true,allowableValues = "range[5,5]")
	@Length(min=5,max=5)
	@NotBlank
	private String roleId;
	
	@ApiModelProperty(value = "角色名称",required=true,allowableValues = "range[1,60]")
	@Length(min=1,max=60)
	@NotBlank
	private String roleName;
	
	@ApiModelProperty(value = "角色描述",required=true,allowableValues = "range[1,200]")
	@Length(min=1,max=200)
	@NotBlank
	private String roleDesc;
	
	@ApiModelProperty(value = "开放范围",required=true,allowableValues = "range[1,1]")
	@Length(min=1,max=1)
	@NotBlank
	private String openFlag;
	
	@ApiModelProperty(value = "范围说明",allowableValues = "range[1,200]")
	@Length(max=200)
	private String openDesc;
	
	@ApiModelProperty(value = "测试用户",required=true,allowableValues = "range[1,80]")
	@Length(min=1,max=80)
	private String testUser;

	@ApiModelProperty(value = "备注",allowableValues = "range[1,200]")
	@Length(max=200)
	private String remark;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}

	public String getOpenDesc() {
		return openDesc;
	}

	public void setOpenDesc(String openDesc) {
		this.openDesc = openDesc;
	}

	public String getTestUser() {
		return testUser;
	}

	public void setTestUser(String testUser) {
		this.testUser = testUser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

}