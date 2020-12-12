package com.bocsh.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


@ApiModel(description= "微服务定义对象")
public class ServeSaveDto{
	
	@ApiModelProperty(value = "后端开发人员",required=true,allowableValues = "range[1,20]")
	@Length(min=1,max=20)
	@NotBlank
	private String backendDev;

	@ApiModelProperty(value = "前端开发人员",required=true,allowableValues = "range[1,20]")
	@Length(min=1,max=20)
	@NotBlank
	private String frontDev;

	@ApiModelProperty(value = "信科负责人",required=true,allowableValues = "range[1,20]")
	@Length(min=1,max=20)
	@NotBlank
	private String pm;

	@ApiModelProperty(value = "服务描述",allowableValues = "range[0,256]",allowEmptyValue=true)
	@Length(max=256)
	private String serviceDesc;

	@ApiModelProperty(value = "服务编号",required=true,example="bocsh-service-test",allowableValues = "range[1,30]")
	@Length(min=1,max=30)
	@NotBlank
	private String serviceId;

	@ApiModelProperty(value = "服务名称",required=true,example="测试服务",allowableValues = "range[1,30]")
	@Length(min=1,max=30)
	@NotBlank
	private String serviceName;

	public String getBackendDev() {
		return backendDev;
	}

	public void setBackendDev(String backendDev) {
		this.backendDev = backendDev;
	}

	public String getFrontDev() {
		return frontDev;
	}

	public void setFrontDev(String frontDev) {
		this.frontDev = frontDev;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	

}