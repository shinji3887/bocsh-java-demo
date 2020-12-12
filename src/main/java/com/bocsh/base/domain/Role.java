package com.bocsh.base.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the MSA_ROLE database table.
 * 
 */
@Entity
@Table(name="MSA_DEMO_ROLE")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MSA_ROLE_ID_GENERATOR", sequenceName="SEQ_MSA",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MSA_ROLE_ID_GENERATOR")
	private long id;

	@Column(name="OPEN_DESC")
	private String openDesc;

	@Column(name="OPEN_FLAG")
	private String openFlag;

	private String remark;

	@Column(name="ROLE_DESC")
	private String roleDesc;

	@Column(name="ROLE_ID")
	private String roleId;

	@Column(name="ROLE_NAME")
	private String roleName;

	@Column(name="SERVICE_ID")
	private String serviceId;

	@Column(name="TEST_USER")
	private String testUser;

    public Role() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOpenDesc() {
		return this.openDesc;
	}

	public void setOpenDesc(String openDesc) {
		this.openDesc = openDesc;
	}

	public String getOpenFlag() {
		return this.openFlag;
	}

	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getTestUser() {
		return this.testUser;
	}

	public void setTestUser(String testUser) {
		this.testUser = testUser;
	}

}