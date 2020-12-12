package com.bocsh.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description= "微服务定义对象")
public class ServeFindDto extends ServeSaveDto{
	
	@ApiModelProperty(value = "id")
	private long id;
	
	@ApiModelProperty(value = "创建日期")
	private String createDate;
	
	@ApiModelProperty(value = "状态")
	private boolean status;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	
}