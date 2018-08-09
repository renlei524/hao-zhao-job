package com.scxxwb.net.admin.modules.operation.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户基础信息表
 * 
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-06
 */
@TableName("t_wb_user")
public class TWbUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 用户手机号码
	 */
    @NotNull(message = "手机号码不能为空",groups = {AddGroup.class, UpdateGroup.class})
	@Length(min = 11,max = 11,message = "请输入正确的手机号(11位)",groups = {AddGroup.class,UpdateGroup.class})
    private String mobile;
	/**
	 * 用户真实姓名
	 */
	@NotNull(message = "姓名不能为空",groups = {AddGroup.class,UpdateGroup.class})
	@Length(min = 1,max = 15,message = "姓名最多15字",groups = {AddGroup.class,UpdateGroup.class})
	private String realName;
	/**
	 * 用户身份证号码
	 */
	@NotNull(message = "身份证号不能为空",groups = {AddGroup.class,UpdateGroup.class})
	@Length(min = 18,max = 18,message = "请输入正确的身份证号(18位)",groups = {AddGroup.class,UpdateGroup.class})
	private String idCard;
	/**
	 * 0:正常 1:异常 2:冻结
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	private Date updateTime;
	/**
	 * ztree属性
	 */
	@TableField(exist=false)
	private Boolean open;

	@TableField(exist=false)
	private List<?> list;
	/**
	 * 消费总金额
	 */
	@TableField(exist = false)
	private Double totalConsumption;
	/**
	 *	最近消费时间
	 */
	@TableField(exist = false)
	private Date recConsumptionTime;
	/**
	 * 最近消费区域
	 */
	@TableField(exist = false)
	private String recConsumptionArea;
	/**
	 * 城市经理
	 */
	@TableField(exist = false)
	private String salesMan;
	/**
	 * 城市经理电话
	 */
	@TableField(exist = false)
	private String salesManTel;







	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置：用户手机号码
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：用户手机号码
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：用户真实姓名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/**
	 * 获取：用户真实姓名
	 */
	public String getRealName() {
		return realName;
	}
	/**
	 * 设置：用户身份证号码
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	/**
	 * 获取：用户身份证号码
	 */
	public String getIdCard() {
		return idCard;
	}
	/**
	 * 设置：0:正常 1:异常 2:冻结
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：0:正常 1:异常 2:冻结
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}


	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Double getTotalConsumption() {
		return totalConsumption;
	}

	public void setTotalConsumption(Double totalConsumption) {
		this.totalConsumption = totalConsumption;
	}

	public Date getRecConsumptionTime() {
		return recConsumptionTime;
	}

	public void setRecConsumptionTime(Date recConsumptionTime) {
		this.recConsumptionTime = recConsumptionTime;
	}

	public String getRecConsumptionArea() {
		return recConsumptionArea;
	}

	public void setRecConsumptionArea(String recConsumptionArea) {
		this.recConsumptionArea = recConsumptionArea;
	}

	public String getSalesMan() {
		return salesMan;
	}

	public void setSalesMan(String salesMan) {
		this.salesMan = salesMan;
	}

	public String getSalesManTel() {
		return salesManTel;
	}

	public void setSalesManTel(String salesManTel) {
		this.salesManTel = salesManTel;
	}
}
