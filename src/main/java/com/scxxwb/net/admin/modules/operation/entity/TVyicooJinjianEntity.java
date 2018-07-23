package com.scxxwb.net.admin.modules.operation.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-07-20 09:58:13
 */
@ApiModel(description = "微易客进件实体类", value = "进件对象")
@TableName("t_vyicoo_jinjian")
public class TVyicooJinjianEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商户类型 1：企业 2：个体工商户
	 */
	@TableId
	@ApiModelProperty(value = "商户类型", name = "type", required = true)
	private String type;
	/**
	 * 商户名称
	 */
	@ApiModelProperty(value = "商户名称", name = "name", required = true)
	private String name;
	/**
	 * 商户简称（4-15字）
	 */
	@ApiModelProperty(value = "商户简称（4-15）个字", required = true)
	private String shortname;
	/**
	 * 商户真实姓名
	 */
	@ApiModelProperty(value = "商户真实姓名", required = true)
	private String realname;
	/**
	 * 联系人手机号
	 */
	@ApiModelProperty(value = "联系人手机号", required = true)
	private String mobile;
	/**
	 * 身份证号
	 */
	@ApiModelProperty(value = "身份证号", required = true)
	private String idNo;
	/**
	 * 省
	 */
	@ApiModelProperty(value = "省", required = true)
	private String gbProvinceNo;
	/**
	 * 市
	 */
	@ApiModelProperty(value = "市", required = true)
	private String gbCityNo;
	/**
	 * 区
	 */
	@ApiModelProperty(value = "区", required = true)
	private String gbDistrictNo;
	/**
	 * 经营地址
	 */
	@ApiModelProperty(value = "经营地址", required = true)
	private String address;
	/**
	 * 联系邮箱
	 */
	@ApiModelProperty(value = "联系邮箱", required = true)
	private String email;
	/**
	 * 经营类别
	 */
	@ApiModelProperty(value = "经营类别", required = true)
	private String category;
	/**
	 * 营业执照类型
	 */
	@ApiModelProperty(value = "营业执照类型", required = true)
	private String licenseType;
	/**
	 * 营业执照编号
	 */
	@ApiModelProperty(value = "营业执照编号", required = true)
	private String licenseNo;
	/**
	 * 营业执照地址
	 */
	@ApiModelProperty(value = "营业执照地址", required = true)
	private String licenseAddress;
	/**
	 * 注册号开始日期 （例：2017-12-01）
	 */
	@ApiModelProperty(value = "注册号开始日期", required = true)
	private String licenseStartDate;
	/**
	 * 注册号结束日期 （例：2029-12-01 永久为-1）
	 */
	@ApiModelProperty(value = "注册号结束日期", required = true)
	private String licenseEndDate;
	/**
	 * 开户支行ID
	 */
	@ApiModelProperty(value = "开户支行id", required = true)
	private String bankId;
	/**
	 * 开户支行所在城市编码
	 */
	@ApiModelProperty(value = "开户支行所在城市编码", required = true)
	private String accountCity;
	/**
	 * 开户支行行号
	 */
	@ApiModelProperty(value = "开户支行行号", required = true)
	private String bankNo;
	/**
	 * 帐户类型 0：对私 1：对公
	 */
	@ApiModelProperty(value = "账户类型 0：对私 1：对公", required = true)
	private String accountType;
	/**
	 * 开户名称
	 */
	@ApiModelProperty(value = "开户名称", required = true)
	private String accountName;
	/**
	 * 银行预留手机号
	 */
	@ApiModelProperty(value = "银行预留手机号", required = true)
	private String accountMobile;
	/**
	 * 有无公众号 1：有公众号 2：无公众号
	 */

	@ApiModelProperty(value = "有无公众号 1：有 2：无", required = true)
	private String selfAppid;
	/**
	 * 有公众号必填（公众号主体需同营业执照名称一致）
	 */
	@ApiModelProperty(value = "有公众号必填")
	private String wxAppid;
	/**
	 * 商户微信号
	 */
	@ApiModelProperty(value = "商户微信号", required = true)
	private String wechatId;
	/**
	 * 法人银行卡号
	 */
	@ApiModelProperty(value = "法人银行卡号", required = true)
	private String bankcardNo;
	/**
	 * 营业执照照片
	 */
	@ApiModelProperty(value = "营业执照照片", required = true)
	private String licensePic;
	/**
	 * 法人身份证正面照片
	 */
	@ApiModelProperty(value = "法人身份证正面照片", required = true)
	private String idFrontPic;
	/**
	 * 法人身份证反面照片
	 */
	@ApiModelProperty(value = "法人身份证反面照片", required = true)
	private String idBackPic;
	/**
	 * 对私：法人结算银行卡照片 对公：开户许可证
	 */
	@ApiModelProperty(value = "对私：法人结算银行卡照片 对公： 开户许可证", required = true)
	private String bankcardPic;
	/**
	 * 店铺门头照片
	 */
	@ApiModelProperty(value = "店铺门头照片", required = true)
	private String shopPic;
	/**
	 * 经营场所内照片
	 */
	@ApiModelProperty(value = "经营场所内照片", required = true)
	private String extraPic1;
	/**
	 * 收银台招牌照片
	 */
	@ApiModelProperty(value = "收银台招牌照片", required = true)
	private String extraPic2;
	/**
	 * 支付方式配置json串
	 */
	@ApiModelProperty(value = "支付方式配置json串")
	private String payment;
	/**
	 * 易客付商户号
	 */
	@ApiModelProperty(value = "易客付商户号", required = true)
	private String mchId;
	/**
	 * 商户进件状态 0：申请中， 1：启用 2 : 认证中 3：失败 4：处理中
	 */
	@ApiModelProperty(value = "商户进件状态 0：申请中， 1：启用 2 : 认证中 3：失败 4：处理中")
	private Integer verifyStatus;
	/**
	 * 商户id
	 */
	@ApiModelProperty(value = "商户id", required = true)
	private Integer merchantId;

	/**
	 * 设置：商户类型 1：企业 2：个体工商户
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：商户类型 1：企业 2：个体工商户
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：商户名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：商户名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：商户简称（4-15字）
	 */
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	/**
	 * 获取：商户简称（4-15字）
	 */
	public String getShortname() {
		return shortname;
	}
	/**
	 * 设置：商户真实姓名
	 */
	public void setRealname(String realname) {
		this.realname = realname;
	}
	/**
	 * 获取：商户真实姓名
	 */
	public String getRealname() {
		return realname;
	}
	/**
	 * 设置：联系人手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：联系人手机号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：身份证号
	 */
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	/**
	 * 获取：身份证号
	 */
	public String getIdNo() {
		return idNo;
	}
	/**
	 * 设置：省
	 */
	public void setGbProvinceNo(String gbProvinceNo) {
		this.gbProvinceNo = gbProvinceNo;
	}
	/**git
	 * 获取：省
	 */
	public String getGbProvinceNo() {
		return gbProvinceNo;
	}
	/**
	 * 设置：市
	 */
	public void setGbCityNo(String gbCityNo) {
		this.gbCityNo = gbCityNo;
	}
	/**
	 * 获取：市
	 */
	public String getGbCityNo() {
		return gbCityNo;
	}
	/**
	 * 设置：区
	 */
	public void setGbDistrictNo(String gbDistrictNo) {
		this.gbDistrictNo = gbDistrictNo;
	}
	/**
	 * 获取：区
	 */
	public String getGbDistrictNo() {
		return gbDistrictNo;
	}
	/**
	 * 设置：经营地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：经营地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：联系邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：联系邮箱
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：经营类别
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * 获取：经营类别
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * 设置：营业执照类型
	 */
	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}
	/**
	 * 获取：营业执照类型
	 */
	public String getLicenseType() {
		return licenseType;
	}
	/**
	 * 设置：营业执照编号
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	/**
	 * 获取：营业执照编号
	 */
	public String getLicenseNo() {
		return licenseNo;
	}
	/**
	 * 设置：营业执照地址
	 */
	public void setLicenseAddress(String licenseAddress) {
		this.licenseAddress = licenseAddress;
	}
	/**
	 * 获取：营业执照地址
	 */
	public String getLicenseAddress() {
		return licenseAddress;
	}
	/**
	 * 设置：注册号开始日期 （例：2017-12-01）
	 */
	public void setLicenseStartDate(String licenseStartDate) {
		this.licenseStartDate = licenseStartDate;
	}
	/**
	 * 获取：注册号开始日期 （例：2017-12-01）
	 */
	public String getLicenseStartDate() {
		return licenseStartDate;
	}
	/**
	 * 设置：注册号结束日期 （例：2029-12-01 永久为-1）
	 */
	public void setLicenseEndDate(String licenseEndDate) {
		this.licenseEndDate = licenseEndDate;
	}
	/**
	 * 获取：注册号结束日期 （例：2029-12-01 永久为-1）
	 */
	public String getLicenseEndDate() {
		return licenseEndDate;
	}
	/**
	 * 设置：开户支行ID
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	/**
	 * 获取：开户支行ID
	 */
	public String getBankId() {
		return bankId;
	}
	/**
	 * 设置：开户支行所在城市编码
	 */
	public void setAccountCity(String accountCity) {
		this.accountCity = accountCity;
	}
	/**
	 * 获取：开户支行所在城市编码
	 */
	public String getAccountCity() {
		return accountCity;
	}
	/**
	 * 设置：开户支行行号
	 */
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	/**
	 * 获取：开户支行行号
	 */
	public String getBankNo() {
		return bankNo;
	}
	/**
	 * 设置：帐户类型 0：对私 1：对公
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	/**
	 * 获取：帐户类型 0：对私 1：对公
	 */
	public String getAccountType() {
		return accountType;
	}
	/**
	 * 设置：开户名称
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * 获取：开户名称
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * 设置：银行预留手机号
	 */
	public void setAccountMobile(String accountMobile) {
		this.accountMobile = accountMobile;
	}
	/**
	 * 获取：银行预留手机号
	 */
	public String getAccountMobile() {
		return accountMobile;
	}
	/**
	 * 设置：有无公众号 1：有公众号 2：无公众号
	 */
	public void setSelfAppid(String selfAppid) {
		this.selfAppid = selfAppid;
	}
	/**
	 * 获取：有无公众号 1：有公众号 2：无公众号
	 */
	public String getSelfAppid() {
		return selfAppid;
	}
	/**
	 * 设置：有公众号必填（公众号主体需同营业执照名称一致）
	 */
	public void setWxAppid(String wxAppid) {
		this.wxAppid = wxAppid;
	}
	/**
	 * 获取：有公众号必填（公众号主体需同营业执照名称一致）
	 */
	public String getWxAppid() {
		return wxAppid;
	}
	/**
	 * 设置：商户微信号
	 */
	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}
	/**
	 * 获取：商户微信号
	 */
	public String getWechatId() {
		return wechatId;
	}
	/**
	 * 设置：法人银行卡号
	 */
	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}
	/**
	 * 获取：法人银行卡号
	 */
	public String getBankcardNo() {
		return bankcardNo;
	}
	/**
	 * 设置：营业执照照片
	 */
	public void setLicensePic(String licensePic) {
		this.licensePic = licensePic;
	}
	/**
	 * 获取：营业执照照片
	 */
	public String getLicensePic() {
		return licensePic;
	}
	/**
	 * 设置：法人身份证正面照片
	 */
	public void setIdFrontPic(String idFrontPic) {
		this.idFrontPic = idFrontPic;
	}
	/**
	 * 获取：法人身份证正面照片
	 */
	public String getIdFrontPic() {
		return idFrontPic;
	}
	/**
	 * 设置：法人身份证反面照片
	 */
	public void setIdBackPic(String idBackPic) {
		this.idBackPic = idBackPic;
	}
	/**
	 * 获取：法人身份证反面照片
	 */
	public String getIdBackPic() {
		return idBackPic;
	}
	/**
	 * 设置：对私：法人结算银行卡照片 对公：开户许可证
	 */
	public void setBankcardPic(String bankcardPic) {
		this.bankcardPic = bankcardPic;
	}
	/**
	 * 获取：对私：法人结算银行卡照片 对公：开户许可证
	 */
	public String getBankcardPic() {
		return bankcardPic;
	}
	/**
	 * 设置：店铺门头照片
	 */
	public void setShopPic(String shopPic) {
		this.shopPic = shopPic;
	}
	/**
	 * 获取：店铺门头照片
	 */
	public String getShopPic() {
		return shopPic;
	}
	/**
	 * 设置：经营场所内照片
	 */
	public void setExtraPic1(String extraPic1) {
		this.extraPic1 = extraPic1;
	}
	/**
	 * 获取：经营场所内照片
	 */
	public String getExtraPic1() {
		return extraPic1;
	}
	/**
	 * 设置：收银台招牌照片
	 */
	public void setExtraPic2(String extraPic2) {
		this.extraPic2 = extraPic2;
	}
	/**
	 * 获取：收银台招牌照片
	 */
	public String getExtraPic2() {
		return extraPic2;
	}
	/**
	 * 设置：支付方式配置json串
	 */
	public void setPayment(String payment) {
		this.payment = payment;
	}
	/**
	 * 获取：支付方式配置json串
	 */
	public String getPayment() {
		return payment;
	}
	/**
	 * 设置：易客付商户号
	 */
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	/**
	 * 获取：易客付商户号
	 */
	public String getMchId() {
		return mchId;
	}
	/**
	 * 设置：商户进件状态 0：申请中， 1：启用 2 : 认证中 3：失败 4：处理中
	 */
	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	/**
	 * 获取：商户进件状态 0：申请中， 1：启用 2 : 认证中 3：失败 4：处理中
	 */
	public Integer getVerifyStatus() {
		return verifyStatus;
	}
	/**
	 * 设置：商户id
	 */
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
	/**
	 * 获取：商户id
	 */
	public Integer getMerchantId() {
		return merchantId;
	}
}
