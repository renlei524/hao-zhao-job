package com.scxxwb.net.admin.modules.operation.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 商户审核信息表
 * 
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-04
 */
@TableName("t_merchant_check")
public class MerchantCheckEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id,自动增长,订单表相关的后缀取此字段的最后一位
	 */
	@TableId
	private Integer id;

	/**
	 * 商户ID
	 */
	@NotNull(message="商户不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer merchantId;

	/**
	 * 此商户所属的用户
	 */
	@Min(value =1,message = "请选择此商户所属的用户",groups = {AddGroup.class,UpdateGroup.class})
	private Integer userId;

	/**
	 * 商户登录名;默认与user_id的mobile一致,若mobile在本表已存在则填邮箱
	 */
	@NotNull(message="登录名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String loginUsername;

	private String loginPwd;
	/**
	 * 分公司id
	 */
	@NotNull(message="分公司不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer agentId;

	/**
	 * 分公司名称
	 */
	@TableField(exist=false)
	private  String agentName;
	/**
	 * 商户名称(店铺名称)
	 */
	@NotNull(message="商户名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	@Length(min = 1,max=20, message = "商户名称最多20字", groups={AddGroup.class,UpdateGroup.class})
	private String merchantName;
	/**
	 * 商户头像
	 */
	private String avatar;
	/**
	 * 商户展示图
	 */
	private String photos;
	/**
	 * 维度坐标
	 */
	private Double latitude;
	/**
	 * 经度坐标
	 */
	private Double longitude;
	/**
	 * 联系人
	 */
	@NotNull(message="联系人不能为空", groups = {AddGroup.class, UpdateGroup.class})
	@Length(min = 1,max=15, message = "联系人最多15字", groups={AddGroup.class,UpdateGroup.class})
	private String contacts;
	/**
	 * 本店联系电话
	 */
	@NotNull(message="联系电话不能为空", groups = {AddGroup.class, UpdateGroup.class})
	@Length(min = 11,max=11, message = "请输入正确的手机号(11位)", groups={AddGroup.class,UpdateGroup.class})
	private String telphone;
	/**
	 * 1:创建审核中 2:审核不通过 3:修改审核中 4:修改不通过 5:正常使用 6：禁用
	 */
	@NotNull(message="未审核", groups = {AddGroup.class, UpdateGroup.class})
	private Integer status;
	/**
	 * 0:未推荐 1:推荐
	 */
	private Boolean recommend;
	/**
	 * 商户分类id
	 */
	private Integer typeId;
	/**
	 * 商户分类名称
	 */
	@NotNull(message="商户分类不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String typeName;
	/**
	 * 省code，来源于表t_wb_system_area
	 */
	private Integer province;
	/**
	 * 市code，来源于表t_wb_system_area
	 */
	private Integer city;
	/**
	 * 区code，来源于表t_wb_system_area
	 */
	private Integer area;

	/**
	 * 乡镇
	 */
	private  Integer town;
	/**
	 * 商户地址包含省市区信息
	 */
	private String address;

	/**
	 * 简单地址
	 */
    @NotNull(message = "详细地址不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private String simpleAddress;

	/**
	 * 社区id
	 */
	private Integer communityId;
	/**
	 * 平均分
	 */
	private Double score;
	/**
	 * 消息推送的设备Id
	 */
	private String pushDeviceId;
	/**
	 * 开户时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 收款码
	 */
	private String merchantCode;
	/**
	 * 身份证号码
	 */
	@NotNull(message = "身份证号不能为空",groups = {AddGroup.class,UpdateGroup.class})
	@Length(min = 18,max = 18,message = "请输入正确的身份证号(18位)",groups = {AddGroup.class,UpdateGroup.class})
	private  String idCard;
	/**
	 * 身份证正面照片
	 */
	private  String idCardFacePhoto;
	/**
	 * 身份证背面照片
	 */
	private  String idCardBackPhoto;
	/**
	 * 营业执照
	 */
	private  String licence;
	/**
	 * 银行卡号
	 */
	private String accountNummber;

	/**
	 * 后台账号ID(创建人或者业务员)
	 */
	private  Integer sysUserId;

	/**
	 * 创建人名称
	 */
	@TableField(exist=false)
	private  String sysUserName;

	/**
	 * 是否开启配送
	 */
	private Integer supportDispatching;

	/**
	 * 支付通道
	 */
	private Integer payChannel;

	/**
	 * 是否开启语音功能
	 */
   private Integer isVoiceFunction;

	/**
	 * 商户限额,0表示不限
	 */
	private Integer merchantLimit;

	/**
	 * 服务费,千分比
	 */
	private Integer serviceCharge;

	/**
	 * 审核备注信息
	 */
	private String remark;
	/**
	 * 客户经理
	 */
	private Integer salesman;

    /**
     * 合同编号
     */
    private String contractNumber;

	/**
	 * 微信公众号
	 */
	private String wechatPublicNumber;

	/**
	 * 设置：主键id,自动增长,订单表相关的后缀取此字段的最后一位
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键id,自动增长,订单表相关的后缀取此字段的最后一位
	 */
	public Integer getId() {
		return id;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 设置：此商户所属的用户
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：此商户所属的用户
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：商户登录名;默认与user_id的mobile一致,若mobile在本表已存在则填邮箱
	 */
	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}
	/**
	 * 获取：商户登录名;默认与user_id的mobile一致,若mobile在本表已存在则填邮箱
	 */
	public String getLoginUsername() {
		return loginUsername;
	}
	/**
	 * 设置：登录密码;默认值是login_username加密后的字符串
	 */
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	/**
	 * 获取：登录密码;默认值是login_username加密后的字符串
	 */
	public String getLoginPwd() {
		return loginPwd;
	}
	/**
	 * 设置：分公司id
	 */
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	/**
	 * 获取：分公司id
	 */
	public Integer getAgentId() {
		return agentId;
	}
	/**
	 * 设置：商户名称(店铺名称)
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	/**
	 * 获取：商户名称(店铺名称)
	 */
	public String getMerchantName() {
		return merchantName;
	}
	/**
	 * 设置：商户头像
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	/**
	 * 获取：商户头像
	 */
	public String getAvatar() {
		return avatar;
	}
	/**
	 * 设置：商户展示图
	 */
	public void setPhotos(String photos) {
		this.photos = photos;
	}
	/**
	 * 获取：商户展示图
	 */
	public String getPhotos() {
		return photos;
	}
	/**
	 * 设置：维度坐标
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	/**
	 * 获取：维度坐标
	 */
	public Double getLatitude() {
		return latitude;
	}
	/**
	 * 设置：经度坐标
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	/**
	 * 获取：经度坐标
	 */
	public Double getLongitude() {
		return longitude;
	}
	/**
	 * 设置：联系人
	 */
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	/**
	 * 获取：联系人
	 */
	public String getContacts() {
		return contacts;
	}
	/**
	 * 设置：本店联系电话
	 */
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	/**
	 * 获取：本店联系电话
	 */
	public String getTelphone() {
		return telphone;
	}
	/**
	 * 设置：1:创建审核中 2:审核不通过 3:修改审核中 4:修改不通过 5:正常使用 6：禁用
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：1:创建审核中 2:审核不通过 3:修改审核中 4:修改不通过 5:正常使用 6：禁用
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：0:未推荐 1:推荐
	 */
	public void setRecommend(Boolean recommend) {
		this.recommend = recommend;
	}
	/**
	 * 获取：0:未推荐 1:推荐
	 */
	public Boolean getRecommend() {
		return recommend;
	}
	/**
	 * 设置：商户分类id
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	/**
	 * 获取：商户分类id
	 */
	public Integer getTypeId() {
		return typeId;
	}
	/**
	 * 设置：
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 获取：
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置：省code，来源于表t_wb_system_area
	 */
	public void setProvince(Integer province) {
		this.province = province;
	}
	/**
	 * 获取：省code，来源于表t_wb_system_area
	 */
	public Integer getProvince() {
		return province;
	}
	/**
	 * 设置：市code，来源于表t_wb_system_area
	 */
	public void setCity(Integer city) {
		this.city = city;
	}
	/**
	 * 获取：市code，来源于表t_wb_system_area
	 */
	public Integer getCity() {
		return city;
	}
	/**
	 * 设置：区code，来源于表t_wb_system_area
	 */
	public void setArea(Integer area) {
		this.area = area;
	}
	/**
	 * 获取：区code，来源于表t_wb_system_area
	 */
	public Integer getArea() {
		return area;
	}
	/**
	 * 设置：商户地址包含省市区信息
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：商户地址包含省市区信息
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：社区id
	 */
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	/**
	 * 获取：社区id
	 */
	public Integer getCommunityId() {
		return communityId;
	}
	/**
	 * 设置：平均分
	 */
	public void setScore(Double score) {
		this.score = score;
	}
	/**
	 * 获取：平均分
	 */
	public Double getScore() {
		return score;
	}
	/**
	 * 设置：消息推送的设备Id
	 */
	public void setPushDeviceId(String pushDeviceId) {
		this.pushDeviceId = pushDeviceId;
	}
	/**
	 * 获取：消息推送的设备Id
	 */
	public String getPushDeviceId() {
		return pushDeviceId;
	}
	/**
	 * 设置：开户时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：开户时间
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
	/**
	 * 获取：收款码
	 */
	public String getMerchantCode() {
		return merchantCode;
	}
	/**
	 * 设置：收款码
	 */
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getIdCardFacePhoto() {
		return idCardFacePhoto;
	}

	public void setIdCardFacePhoto(String idCardFacePhoto) {
		this.idCardFacePhoto = idCardFacePhoto;
	}

	public String getIdCardBackPhoto() {
		return idCardBackPhoto;
	}

	public void setIdCardBackPhoto(String idCardBackPhoto) {
		this.idCardBackPhoto = idCardBackPhoto;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getAccountNummber() {
		return accountNummber;
	}

	public void setAccountNummber(String accountNummber) {
		this.accountNummber = accountNummber;
	}

	public Integer getTown() {return town;}

	public void setTown(Integer town) {this.town = town;}

	public Integer getSysUserId() {return sysUserId;}

	public void setSysUserId(Integer sysUserId) {this.sysUserId = sysUserId;}

	public String getSysUserName() {return sysUserName;}

	public void setSysUserName(String sysUserName) {this.sysUserName = sysUserName;}

	public Integer getSupportDispatching() {
		return supportDispatching;
	}

	public void setSupportDispatching(Integer supportDispatching) {
		this.supportDispatching = supportDispatching;
	}

	public Integer getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(Integer payChannel) {
		this.payChannel = payChannel;
	}

	public Integer getIsVoiceFunction() {
		return isVoiceFunction;
	}

	public void setIsVoiceFunction(Integer isVoiceFunction) {
		this.isVoiceFunction = isVoiceFunction;
	}

	public String getSimpleAddress() {
		return simpleAddress;
	}

	public void setSimpleAddress(String simpleAddress) {
		this.simpleAddress = simpleAddress;
	}

	public Integer getMerchantLimit() {
		return merchantLimit;
	}

	public void setMerchantLimit(Integer merchantLimit) {
		this.merchantLimit = merchantLimit;
	}

	public Integer getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Integer serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSalesman() {
		return salesman;
	}

	public void setSalesman(Integer salesman) {
		this.salesman = salesman;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

    public String getWechatPublicNumber() {
        return wechatPublicNumber;
    }

    public void setWechatPublicNumber(String wechatPublicNumber) {
        this.wechatPublicNumber = wechatPublicNumber;
    }
}
