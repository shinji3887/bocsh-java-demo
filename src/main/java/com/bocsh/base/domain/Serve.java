package com.bocsh.base.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the MSA_SERVICE database table.
 * 
 */
@Entity
@Table(name="MSA_DEMO_SERVICE")
public class Serve implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MSA_SERVICE_ID_GENERATOR", sequenceName="SEQ_MSA",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MSA_SERVICE_ID_GENERATOR")
	private long id;

	@Column(name="BACKEND_DEV")
	private String backendDev;

	@Column(name="CREATE_DATE")
	private String createDate;

	@Column(name="FRONT_DEV")
	private String frontDev;

	private String pm;

	@Column(name="SERVICE_DESC")
	private String serviceDesc;

	@Column(name="SERVICE_ID")
	private String serviceId;

	@Column(name="SERVICE_NAME")
	private String serviceName;

	@Column(name="UPDATE_DATE")
	private String updateDate;

    public Serve() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBackendDev() {
		return this.backendDev;
	}

	public void setBackendDev(String backendDev) {
		this.backendDev = backendDev;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getFrontDev() {
		return this.frontDev;
	}

	public void setFrontDev(String frontDev) {
		this.frontDev = frontDev;
	}

	public String getPm() {
		return this.pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getServiceDesc() {
		return this.serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

}