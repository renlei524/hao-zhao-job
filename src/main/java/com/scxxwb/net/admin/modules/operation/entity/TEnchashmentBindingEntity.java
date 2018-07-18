package com.scxxwb.net.admin.modules.operation.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 商户与用户的提现账户绑定
 * 
 * @author liyun
 * @email 2563720820@qq.com
 * @date 2018-07-10 10:10:11
 */
@TableName("t_enchashment_binding")
public class TEnchashmentBindingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键Id
	 */
	@TableId
	private Integer id;
	/**
	 * 1:用户提现 2:商户提现
	 */
	private Integer objectType;
	/**
	 * 商户Id或用户Id 根据type判断
	 */
    @NotNull(message="商户名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer objectId;

	/**
	 * 商户名称或用户名称 根据type判断
	 */
	@TableField(exist=false)
	private String objectName;

	/**
	 * 是否是默认
	 */
	private Integer isDefault;
	/**
	 * 提现类型  1 微信 2银行卡 3 支付宝
	 */
	private Integer enchashmentType;
	/**
	 * 银行卡号
	 */
    @NotNull(message="银行卡号不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String account;
	/**
	 * 银行
	 */
	private String bank;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 支行
	 */
    @NotNull(message="开户行不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String subbranch;

	/**
	 * 开户行名称
	 */
    @TableField(exist=false)
	private String  subbranchName;

	/**
	 * 开户名
	 */
    @NotNull(message="开户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String accountName;
	/**
	 * 银联号
	 */
	private String bankUnionId;
	/**
	 * 0删除 1正常
	 */
	private Integer status;

	/**
	 * 设置：主键Id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键Id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：1:用户提现 2:商户提现
	 */
	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}
	/**
	 * 获取：1:用户提现 2:商户提现
	 */
	public Integer getObjectType() {
		return objectType;
	}
	/**
	 * 设置：商户Id或用户Id 根据type判断
	 */
	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}
	/**
	 * 获取：商户Id或用户Id 根据type判断
	 */
	public Integer getObjectId() {
		return objectId;
	}
	/**
	 * 设置：是否是默认
	 */
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	/**
	 * 获取：是否是默认
	 */
	public Integer getIsDefault() {
		return isDefault;
	}
	/**
	 * 设置：提现类型  1 微信 2银行卡 3 支付宝
	 */
	public void setEnchashmentType(Integer enchashmentType) {
		this.enchashmentType = enchashmentType;
	}
	/**
	 * 获取：提现类型  1 微信 2银行卡 3 支付宝
	 */
	public Integer getEnchashmentType() {
		return enchashmentType;
	}
	/**
	 * 设置：提现账户
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取：提现账户
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置：银行
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}
	/**
	 * 获取：银行
	 */
	public String getBank() {
		return bank;
	}
	/**
	 * 设置：真实姓名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/**
	 * 获取：真实姓名
	 */
	public String getRealName() {
		return realName;
	}
	/**
	 * 设置：支行
	 */
	public void setSubbranch(String subbranch) {
		this.subbranch = subbranch;
	}
	/**
	 * 获取：支行
	 */
	public String getSubbranch() {
		return subbranch;
	}
	/**
	 * 设置：开户名
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * 获取：开户名
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * 设置：银联号
	 */
	public void setBankUnionId(String bankUnionId) {
		this.bankUnionId = bankUnionId;
	}
	/**
	 * 获取：银联号
	 */
	public String getBankUnionId() {
		return bankUnionId;
	}
	/**
	 * 设置：0删除 1正常
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：0删除 1正常
	 */
	public Integer getStatus() {
		return status;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

    public String getSubbranchName() {
        return subbranchName;
    }

    public void setSubbranchName(String subbranchName) {
        this.subbranchName = subbranchName;
    }
}
