package com.bocsh.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description= "微服务查询参数对象")
public class ServeQueryDto{
	
	@ApiModelProperty(value = "信科负责人")
	private String pm;

	@ApiModelProperty(value = "服务编号")
	private String serviceId;

	@ApiModelProperty(value = "服务名称")
	private String serviceName;

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
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