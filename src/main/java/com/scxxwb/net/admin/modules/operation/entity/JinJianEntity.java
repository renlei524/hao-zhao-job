package com.scxxwb.net.admin.modules.operation.entity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 商家进件信息表-点点客
 *
 * @author liyun
 * @email 256372020@qq.com
 * @date 2018-06-28 17:11:39
 */
@TableName("t_merchant_extends_ddk")
public class JinJianEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商家id
     */
    @TableId
    private Integer merchantId;
    /**
     * 商户进件成功后在点点客保存的会员id
     */
    private String merchantMemberId;
    /**
     * 商户性质    必填 选项：1.企业商户；2.个体商户
     */
    private Integer merchantClass;
    /**
     * 商户类型 必填  选项：1.连锁商户；2.单店
     */
    private Integer merchantType;
    /**
     * 关联商户号 非必填	1.填写连锁商户的主商户商户号2.非连锁商户不填写
     */
    private String relatedMerchantName;
    /**
     * 商户注册名称 必填	1.填写营业执照上面的商户名称 2.个体商户填写商户简称
     */
    private String merchantName;
    /**
     * 商户简称 必填  简称格式：地区+商户名称+行业
     */
    private String merchantShortName;
    /**
     * 小票名称 必填  如没有特殊需求，可以同商户简称。
     */
    private String userDefinedName;
    /**
     * 注册地址所在省 必填	1.参考附录1：比如河北省，请填写河北 2.个体商户不填写
     */
    private String regProv;
    /**
     * 注册地址所在市 必填	1.参考附录1：比如唐山市，请填写唐山市 2.个体商户不填写河北 2.个体商户不填写
     */
    private String regCity;
    /**
     * 注册地址所在区县 必填  参考附录1：比如XX区，请填写XX区2.个体商户不填写
     */
    private String regArea;
    /**
     * 商户注册详细地址 必填  填写商户实际的注册详细地址
     */
    private String regAddr;
    /**
     * 经营地址所在省 必填  参考附录1：比如河北省，请填写河北
     */
    private String prov;
    /**
     * 经营地址所在市 必填  参考附录1：比如唐山市，请填写唐山市
     */
    private String city;
    /**
     * 经营地址所在区县 必填  参考附录1：比如XX区，请填写XX区
     */
    private String area;
    /**
     * 商户经营详细地址 必填  填写商户实际的经营详细地址
     */
    private String merchantAddr;
    /**
     * 营业开始时间 必填  格式要求：09：00
     */
    private String businessStartHours;
    /**
     * 营业结束时间  必填 格式要求：22：00
     */
    private String businessEndHours;
    /**
     * 商户所属总部  非必填 连锁商户如果有自定义组织架构的填写
     */
    private String merchantHeader;
    /**
     * 是否三证合一  非必填 企业商户必填，选项：0，否；1，是。默认选择是。选择是：填写信用代码证编号；选择否：填写营业执照编号。
     */
    private Boolean boolCreditCode;
    /**
     * 统一社会信用代码证   非必填 三证合一选择是填写
     */
    private String creditCode;
    /**
     * 营业执照编号  非必填 三证合一选择否填写
     */
    private String regCode;
    /**
     * 证照有效期   必填 选项：1，非长期；2，长期
     */
    private Integer licType;
    /**
     * 证照开始日期 必填  日期格式要求：20180115
     */
    private String licStartDate;
    /**
     * 证照结束日期  必填 选择非长期填写，日期格式要求：20180115115
     */
    private String licEndDate;
    /**
     * 组织机构代码证 非必填 三证合一选择否填写
     */
    private String orgCode;
    /**
     * 商户经营类型  必填 选项：1.实体，2.虚拟。
     */
    private Integer cateType;
    /**
     * 法人  非必填 企业用户必填，法人姓名。支持少数名族,最大支持15个汉字。
     */
    private String legalName;
    /**
     * 法人证件类型  非必填 企业用户必填，选项：01:身份证、02:护照、04:军官证、03:港澳台通行证、05:回乡证，06:工商登记号 09:其他
     */
    private Integer idType;
    /**
     * 法人证件号码 非必填  企业用户必填
     */
    private String idNo;
    /**
     * 法人证件有效期 非必填 企业用户必填，选项：1，非长期；2，长期
     */
    private Integer idValidType;
    /**
     * 法人证件有效期开始日期 非必填 企业用户必填，日期格式要求：20180115
     */
    private String idStartDate;
    /**
     * 法人证件有效期证件结束日期   非必填 选择非长期填写，日期格式要求：20180115
     */
    private String idEndDate;
    /**
     * 商户联系人   必填 商户联系人姓名。支持少数名族,最大支持15个汉字
     */
    private String contactName;
    /**
     * 联系人证件类型 必填  选项：01:身份证、02:护照、04:军官证、03:港澳台通行证、05:回乡证，06:工商登记号 09:其他
     */
    private Integer contactIdType;
    /**
     * 联系人证件号码 必填
     */
    private String contactIdNo;
    /**
     * 联系人证件有效期    必填 选项：1，非长期；2，长期
     */
    private Integer contactIdValidType;
    /**
     * 联系人证件有效期开始日期 必填  日期格式要求：20180115
     */
    private String contactIdStartDate;
    /**
     * 联系人证件有效期结束日期    非必填 选择非长期填写，日期格式要求：20180115
     */
    private String contactIdEndDate;
    /**
     * 联系人手机   必填 手机号格式
     */
    private String contactTelNo;
    /**
     * 联系人邮箱   必填 邮箱格式
     */
    private String contactEmail;
    /**
     * 合同编号    必填 填写协议上的实际合同编号（个体商户默认代理商统一的合同编号）
     */
    private String contactNum;
    /**
     * 签约业务经理  必填 实际签约的业务经理。
     */
    private String signName;
    /**
     * 签约日期    必填 实际签约的日期。
     */
    private String signDate;
    /**
     * 合同有效期开始日期   必填 日期格式要求：20180115
     */
    private String signStartDate;
    /**
     * 合同有效期结束日期   必填 日期格式要求：20180115
     */
    private String signEndDate;
    /**
     * 管理员账号   必填 必填，建议使用手机号或联系人姓名拼音+生日，避免重复
     */
    private String tellerId;
    /**
     * 是否发送短信通知商户 必填  发送短信通知商户联系人，包含控台地址、管理员账号、密码 //1：是，0：否
     */
    private Boolean boolSendMes;
    /**
     * 结算账户类型 必填  选项：0.对公 1.对私
     */
    private Boolean boolPrivate;
    /**
     * 结算账户名 必填  结算账户名称，对公结算时，结算账户名称需与商户注册名称一致
     */
    private String bankActName;
    /**
     * 结算账号 必填  结账账号
     */
    private String bankActId;
    /**
     * 银行所在省 必填  参考附录1：比如河北省，请填写河北
     */
    private String bankProv;
    /**
     * 银行所在市 必填  参考附录1：比如唐山市，请填写唐山市
     */
    private String bankCity;
    /**
     * 银行所在区县 必填  参考附录1：比如XX区，请填写XX区
     */
    private String bankArea;
    /**
     * 银行名称 必填  参考附录2:填写
     */
    private String bankName;
    /**
     * 结算周期 必填  选项：1代表T+1； 2代表D+1
     */
    private Integer settleTerm;
    /**
     * D+1结算手续费（%）	非必填 选择：D+1时必填
     */
    private Double fee01;
    /**
     * 持卡人证件类型 非必填 对私结算时必填        //选项：01:身份证、02:护照、04:军官证、03:港澳台通行证、05:回乡证，06:工商登记号 09:其他
     */
    private Integer accountIdType;
    /**
     * 持卡人证件号码 非必填 对私结算时必填
     */
    private String accountIdNo;
    /**
     * 证件有效期   非必填 对私结算时必填 //选项：1，非长期；2，长期
     */
    private Integer accountIdValidType;
    /**
     * 证件有效期开始日期 非必填 对私结算时必填  日期格式要求：20180115
     */
    private String accountIdStartDate;
    /**
     * 证件有效期结束日期   非必填 选择非长期填写，日期格式要求：20180115
     */
    private String accountIdEndDate;
    /**
     * 是否开通TS业务(秒到业务)    必填 选项：0：否 1：是
     */
    private Boolean auth24;
    /**
     * TS结算手续费（固定）	非必填 选择固定手续费时填写
     */
    private Double fee20;
    /**
     * TS结算手续费（%）	非必填 选择百分比手续费时填写
     */
    private Double fee21;
    /**
     * 封顶值 非必填 选择百分比手续费时填写
     */
    private Double fee22;
    /**
     * TS起结金额（元）	非必填 选项开通TS业务时填写
     */
    private Double tsCashLimit;
    /**
     * 是否开通银行卡支付   必填 选项：0：否 1：是
     */
    private Boolean auth23;
    /**
     * 手续费类型 非必填 开通银行卡支付必填 选项：01-减免类 02-优惠类 03-标准类
     */
    private Integer chargeCateCode;
    /**
     * 借记卡手续费（%）	非必填 开通银行卡支付必填 减免类手续费为0，其他类型手续费保留小数点后4位。如：0.5533
     */
    private Double fee02;
    /**
     * 封顶  非必填 减免类为0，其他封顶值精确点小数点后2位。如：18.55元
     */
    private Double fee04;
    /**
     * 贷记卡手续费（%）	非必填 开通银行卡支付必填 减免类为0，其他类型手续费保留小数点后4位。如：0.5533
     */
    private Double fee03;
    /**
     * 银联现场注册码 非必填 银联现场注册商户填写
     */
    private String unionRegCode;
    /**
     * 云闪付借记卡手续费（%）	非必填 开通银行卡支付必填 减免类手续费为0，其他类型手续费保留小数点后4位。如：0.5533
     */
    private Double fee05;
    /**
     * 封顶(元)   非必填 减免类为0，其他封顶值精确点小数点后2位。如：18.55
     */
    private Double fee07;
    /**
     * 云闪付贷记卡手续费（%）	非必填 开通银行卡支付必填         //减免类为0，其他类型手续费保留小数点后4位。如：0.5533
     */
    private Double fee06;
    /**
     * 开通银联二维码 非必填 选项：0：否 1：是
     */
    private Boolean auth20;
    /**
     * 借记卡手续费（%）	非必填 选择开通银联二维码业务后必填。手续费保留小数点后4位。如：0.5533
     */
    private Double fee23;
    /**
     * 封顶(元)   非必填 精确点小数点后2位。如：18.55元
     */
    private Double fee24;
    /**
     * 贷记卡手续费（%）	非必填 选择开通银联二维码业务后必填。手续费保留小数点后4位。如：0.5533
     */
    private Double fee25;
    /**
     * 是否开通微信支付    必填 选项：0：否 1：是
     */
    private Boolean auth18;
    /**
     * 商户经营类目（微信）	非必填 开通微信支付业务时必填，参照附录4微信支付宝经营类目对照表
     */
    private String wechatCateCode;
    /**
     * 微信支付手续费 非必填 开通微信支付业务时必填，填写相应的手续费，精确到小数点后4位。如：0.5533        //选择否，不填写
     */
    private Double fee08;
    /**
     * 公众号支付appid 非必填 微信公众号支付必填 如：wx191306eb02bea157
     */
    private String wechatPubNumAppid;
    /**
     * 公众号支付授权目录 非必填 微信公众号支付必填 如：http://mtest2.dzq.com/qcode
     */
    private String wechatPubNumAuth;
    /**
     * 关注公众号   非必填 如果不填写默认关注汇付微信公众号
     */
    private String wechatPubNum;
    /**
     * 是否为商户的公众号   必填 微信公众号支付必填        //选项：1）是；0）否
     */
    private Boolean isMerchantWechat;
    /**
     * 是否开通支付宝支付 必填  选项：1.是；0.否
     */
    private Boolean auth19;
    /**
     * 商户经营类目（支付宝）	非必填 开通支付宝支付业务时必填，参照附录4微信支付宝经营类目对照表
     */
    private String alipayCateCode;
    /**
     * 支付宝手续费 非必填 选择是，填写相应的手续费，精确到小数点后4位。如：0.5533        //选择否，不填写
     */
    private Double fee12;
    /**
     * 代理商会员号	必填	代理商会员号18位 310000015000017897
     */
    private String memberId;
    /**
     * 代理商操作员	必填	代理商操作员号 CUXXWB
     */
    private String opTellerId;
    /**
     * 异步通知地址	非必填	审核结果异步通知推送地址
     */
    private String bgRetUrl;
    /**
     * 请求流水号	非必填	由接入方生成且保证唯一，查询时会用到
     */
    private String reqSerialNum;
    /**
     * 非必填	APP支付必填        //如：wx191306eb02bea157
     */
    private String wxAppid;
    /**
     * 商户种类	必填	选项: 1-政府机构//2-国营企业//3-私营企业//4-外资企业//5-个体工商户//7-事业单位
     */
    private Integer pnrPayMerchantType;
    /**
     * 图片上传方式	必填	01 控台上传（默认）;        //02 接口上传
     */
    private Integer picUploadWay;
    /**
     * 图片文件ID	非必填	当图片上传方式picUploadWay= “02”(接口上传)时，picture IDs （图片文件ID）字段必填，通过json串儿的方式，指定图片类型及图片文件编号//pictureIDs内容格式： {" archFlph01":"afc24351-b58c-4261-950f-64571b064f36"," archFlph02":"bfc24351-b58c-4261-950f-64571b064f35",...}若某个字段没图片，则赋值为空。例：{“archFlph02”: “”}
     */
    private String pictureIds;
    /**
     * 是否开通延迟入账	非必填	选项：0：否 1：是
     */
    private Integer auth29;
    /**
     * 状态 0=申请 1=审核成功 2=失败 3=冻结
     */
    private Integer status;
    /**
     * 产品号
     */
    private String prodId;
    /**
     * 系统号
     */
    private String sysId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 营业执照图片.企业商户必填，格式：数字+数字，如：1_1，表示第一个商户的营业执照照片，与zip包中的图片名称对应
     */
    private String archflph01;
    /**
     * 税务登记证.企业商户必填，格式：数字+数字，如：1_2，表示第一个商户的税务登记证照片，与zip包中的图片名称对应
     */
    private String archflph02;
    /**
     * 组织机构代码证.企业商户必填，格式：数字+数字，如：1_3，表示第一个商户的组织机构代码证照片，与zip包中的图片名称对应
     */
    private String archflph03;
    /**
     * 开户许可证.对公结算必填，格式：数字+数字，如：1_4，表示第一个商户的开户许可证照片，与zip包中的图片名称对应
     */
    private String archflph04;
    /**
     * 法人身份证正面.企业商户必填，格式：数字+数字，如：1_5，表示第一个商户的法人身份证正面照片，与zip包中的图片名称对应
     */
    private String archflph05;
    /**
     * 法人身份证反面.企业商户必填，格式：数字+数字，如：1_6，表示第一个商户的法人身份证反面照片，与zip包中的图片名称对应
     */
    private String archflph06;
    /**
     * 结算人身份证正面.个体商户必填，格式：数字+数字，如：1_7，表示第一个商户的结算人身份证正面照片，与zip包中的图片名称对应
     */
    private String archflph07;
    /**
     * 结算人身份证反面.个体商户必填，格式：数字+数字，如：1_8，表示第一个商户的结算人身份证反面照片，与zip包中的图片名称对应
     */
    private String archflph08;
    /**
     * 商务协议.格式：数字+数字，如：1_9，表示第一个商户的商务协议照片，与zip包中的图片名称对应
     */
    private String archflph09;
    /**
     * 公司照片一.企业商户必填，格式：数字+数字，如：1_10，表示第一个商户的公司照片一，与zip包中的图片名称对应
     */
    private String archflph10;
    /**
     * 公司照片二.企业商户必填，格式：数字+数字，如：1_11，表示第一个商户的公司照片二，与zip包中的图片名称对应
     */
    private String archflph11;
    /**
     * 公司照片三.企业商户必填，格式：数字+数字，如：1_12，表示第一个商户的公司照片三，与zip包中的图片名称对应
     */
    private String archflph12;
    /**
     * 联系人身份证正面.个体商户必填，格式：数字+数字，如：1_13，表示第一个商户的联系人身份证正面照片，与zip包中的图片名称对应
     */
    private String archflph13;
    /**
     * 联系人身份证反面.个体商户必填，格式：数字+数字，如：1_14，表示第一个商户的联系人身份证反面照片，与zip包中的图片名称对应
     */
    private String archflph14;
    /**
     * 店铺门头照片.开通微信支付宝时必填，格式：数字+数字，如：1_15，表示第一个商户的店铺门头照片，与zip包中的图片名称对应
     */
    private String archflph15;
    /**
     * 店铺收银台照片.开通微信支付宝时必填，格式：数字+数字，如：1_16，表示第一个商户的店铺收银台照片，与zip包中的图片名称对应
     */
    private String archflph16;
    /**
     * 店内照片.开通微信支付宝时必填，格式：数字+数字，如：1_17，表示第一个商户的店内照片，与zip包中的图片名称对应
     */
    private String archflph17;
    /**
     * 结算卡正面.对私结算必填，格式：数字+数字，如：1_18，表示第一个结算银行卡正面照片，与zip包中的图片名称对应
     */
    private String archflph18;
    /**
     * 结算卡反面.对私结算必填，格式：数字+数字，如：1_19，表示第一个结算银行卡反面照片，与zip包中的图片名称对应
     */
    private String archflph19;
    /**
     * 审核状态：0（成功），1（失败）
     */
    private Boolean checkStatus;

    /**
     * 设置：商家id
     */
    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }
    /**
     * 获取：商家id
     */
    public Integer getMerchantId() {
        return merchantId;
    }
    /**
     * 设置：商户进件成功后在点点客保存的会员id
     */
    public void setMerchantMemberId(String merchantMemberId) {
        this.merchantMemberId = merchantMemberId;
    }
    /**
     * 获取：商户进件成功后在点点客保存的会员id
     */
    public String getMerchantMemberId() {
        return merchantMemberId;
    }
    /**
     * 设置：商户性质    必填 选项：1.企业商户；2.个体商户
     */
    public void setMerchantClass(Integer merchantClass) {
        this.merchantClass = merchantClass;
    }
    /**
     * 获取：商户性质    必填 选项：1.企业商户；2.个体商户
     */
    public Integer getMerchantClass() {
        return merchantClass;
    }
    /**
     * 设置：商户类型 必填  选项：1.连锁商户；2.单店
     */
    public void setMerchantType(Integer merchantType) {
        this.merchantType = merchantType;
    }
    /**
     * 获取：商户类型 必填  选项：1.连锁商户；2.单店
     */
    public Integer getMerchantType() {
        return merchantType;
    }
    /**
     * 设置：关联商户号 非必填	1.填写连锁商户的主商户商户号2.非连锁商户不填写
     */
    public void setRelatedMerchantName(String relatedMerchantName) {
        this.relatedMerchantName = relatedMerchantName;
    }
    /**
     * 获取：关联商户号 非必填	1.填写连锁商户的主商户商户号2.非连锁商户不填写
     */
    public String getRelatedMerchantName() {
        return relatedMerchantName;
    }
    /**
     * 设置：商户注册名称 必填	1.填写营业执照上面的商户名称 2.个体商户填写商户简称
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    /**
     * 获取：商户注册名称 必填	1.填写营业执照上面的商户名称 2.个体商户填写商户简称
     */
    public String getMerchantName() {
        return merchantName;
    }
    /**
     * 设置：商户简称 必填  简称格式：地区+商户名称+行业
     */
    public void setMerchantShortName(String merchantShortName) {
        this.merchantShortName = merchantShortName;
    }
    /**
     * 获取：商户简称 必填  简称格式：地区+商户名称+行业
     */
    public String getMerchantShortName() {
        return merchantShortName;
    }
    /**
     * 设置：小票名称 必填  如没有特殊需求，可以同商户简称。
     */
    public void setUserDefinedName(String userDefinedName) {
        this.userDefinedName = userDefinedName;
    }
    /**
     * 获取：小票名称 必填  如没有特殊需求，可以同商户简称。
     */
    public String getUserDefinedName() {
        return userDefinedName;
    }
    /**
     * 设置：注册地址所在省 必填	1.参考附录1：比如河北省，请填写河北 2.个体商户不填写
     */
    public void setRegProv(String regProv) {
        this.regProv = regProv;
    }
    /**
     * 获取：注册地址所在省 必填	1.参考附录1：比如河北省，请填写河北 2.个体商户不填写
     */
    public String getRegProv() {
        return regProv;
    }
    /**
     * 设置：注册地址所在市 必填	1.参考附录1：比如唐山市，请填写唐山市 2.个体商户不填写河北 2.个体商户不填写
     */
    public void setRegCity(String regCity) {
        this.regCity = regCity;
    }
    /**
     * 获取：注册地址所在市 必填	1.参考附录1：比如唐山市，请填写唐山市 2.个体商户不填写河北 2.个体商户不填写
     */
    public String getRegCity() {
        return regCity;
    }
    /**
     * 设置：注册地址所在区县 必填  参考附录1：比如XX区，请填写XX区2.个体商户不填写
     */
    public void setRegArea(String regArea) {
        this.regArea = regArea;
    }
    /**
     * 获取：注册地址所在区县 必填  参考附录1：比如XX区，请填写XX区2.个体商户不填写
     */
    public String getRegArea() {
        return regArea;
    }
    /**
     * 设置：商户注册详细地址 必填  填写商户实际的注册详细地址
     */
    public void setRegAddr(String regAddr) {
        this.regAddr = regAddr;
    }
    /**
     * 获取：商户注册详细地址 必填  填写商户实际的注册详细地址
     */
    public String getRegAddr() {
        return regAddr;
    }
    /**
     * 设置：经营地址所在省 必填  参考附录1：比如河北省，请填写河北
     */
    public void setProv(String prov) {
        this.prov = prov;
    }
    /**
     * 获取：经营地址所在省 必填  参考附录1：比如河北省，请填写河北
     */
    public String getProv() {
        return prov;
    }
    /**
     * 设置：经营地址所在市 必填  参考附录1：比如唐山市，请填写唐山市
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * 获取：经营地址所在市 必填  参考附录1：比如唐山市，请填写唐山市
     */
    public String getCity() {
        return city;
    }
    /**
     * 设置：经营地址所在区县 必填  参考附录1：比如XX区，请填写XX区
     */
    public void setArea(String area) {
        this.area = area;
    }
    /**
     * 获取：经营地址所在区县 必填  参考附录1：比如XX区，请填写XX区
     */
    public String getArea() {
        return area;
    }
    /**
     * 设置：商户经营详细地址 必填  填写商户实际的经营详细地址
     */
    public void setMerchantAddr(String merchantAddr) {
        this.merchantAddr = merchantAddr;
    }
    /**
     * 获取：商户经营详细地址 必填  填写商户实际的经营详细地址
     */
    public String getMerchantAddr() {
        return merchantAddr;
    }
    /**
     * 设置：营业开始时间 必填  格式要求：09：00
     */
    public void setBusinessStartHours(String businessStartHours) {
        this.businessStartHours = businessStartHours;
    }
    /**
     * 获取：营业开始时间 必填  格式要求：09：00
     */
    public String getBusinessStartHours() {
        return businessStartHours;
    }
    /**
     * 设置：营业结束时间  必填 格式要求：22：00
     */
    public void setBusinessEndHours(String businessEndHours) {
        this.businessEndHours = businessEndHours;
    }
    /**
     * 获取：营业结束时间  必填 格式要求：22：00
     */
    public String getBusinessEndHours() {
        return businessEndHours;
    }
    /**
     * 设置：商户所属总部  非必填 连锁商户如果有自定义组织架构的填写
     */
    public void setMerchantHeader(String merchantHeader) {
        this.merchantHeader = merchantHeader;
    }
    /**
     * 获取：商户所属总部  非必填 连锁商户如果有自定义组织架构的填写
     */
    public String getMerchantHeader() {
        return merchantHeader;
    }
    /**
     * 设置：是否三证合一  非必填 企业商户必填，选项：0，否；1，是。默认选择是。选择是：填写信用代码证编号；选择否：填写营业执照编号。
     */
    public void setBoolCreditCode(Boolean boolCreditCode) {
        this.boolCreditCode = boolCreditCode;
    }
    /**
     * 获取：是否三证合一  非必填 企业商户必填，选项：0，否；1，是。默认选择是。选择是：填写信用代码证编号；选择否：填写营业执照编号。
     */
    public Boolean getBoolCreditCode() {
        return boolCreditCode;
    }
    /**
     * 设置：统一社会信用代码证   非必填 三证合一选择是填写
     */
    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }
    /**
     * 获取：统一社会信用代码证   非必填 三证合一选择是填写
     */
    public String getCreditCode() {
        return creditCode;
    }
    /**
     * 设置：营业执照编号  非必填 三证合一选择否填写
     */
    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }
    /**
     * 获取：营业执照编号  非必填 三证合一选择否填写
     */
    public String getRegCode() {
        return regCode;
    }
    /**
     * 设置：证照有效期   必填 选项：1，非长期；2，长期
     */
    public void setLicType(Integer licType) {
        this.licType = licType;
    }
    /**
     * 获取：证照有效期   必填 选项：1，非长期；2，长期
     */
    public Integer getLicType() {
        return licType;
    }
    /**
     * 设置：证照开始日期 必填  日期格式要求：20180115
     */
    public void setLicStartDate(String licStartDate) {
        this.licStartDate = licStartDate;
    }
    /**
     * 获取：证照开始日期 必填  日期格式要求：20180115
     */
    public String getLicStartDate() {
        return licStartDate;
    }
    /**
     * 设置：证照结束日期  必填 选择非长期填写，日期格式要求：20180115115
     */
    public void setLicEndDate(String licEndDate) {
        this.licEndDate = licEndDate;
    }
    /**
     * 获取：证照结束日期  必填 选择非长期填写，日期格式要求：20180115115
     */
    public String getLicEndDate() {
        return licEndDate;
    }
    /**
     * 设置：组织机构代码证 非必填 三证合一选择否填写
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    /**
     * 获取：组织机构代码证 非必填 三证合一选择否填写
     */
    public String getOrgCode() {
        return orgCode;
    }
    /**
     * 设置：商户经营类型  必填 选项：1.实体，2.虚拟。
     */
    public void setCateType(Integer cateType) {
        this.cateType = cateType;
    }
    /**
     * 获取：商户经营类型  必填 选项：1.实体，2.虚拟。
     */
    public Integer getCateType() {
        return cateType;
    }
    /**
     * 设置：法人  非必填 企业用户必填，法人姓名。支持少数名族,最大支持15个汉字。
     */
    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }
    /**
     * 获取：法人  非必填 企业用户必填，法人姓名。支持少数名族,最大支持15个汉字。
     */
    public String getLegalName() {
        return legalName;
    }
    /**
     * 设置：法人证件类型  非必填 企业用户必填，选项：01:身份证、02:护照、04:军官证、03:港澳台通行证、05:回乡证，06:工商登记号 09:其他
     */
    public void setIdType(Integer idType) {
        this.idType = idType;
    }
    /**
     * 获取：法人证件类型  非必填 企业用户必填，选项：01:身份证、02:护照、04:军官证、03:港澳台通行证、05:回乡证，06:工商登记号 09:其他
     */
    public Integer getIdType() {
        return idType;
    }
    /**
     * 设置：法人证件号码 非必填  企业用户必填
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    /**
     * 获取：法人证件号码 非必填  企业用户必填
     */
    public String getIdNo() {
        return idNo;
    }
    /**
     * 设置：法人证件有效期 非必填 企业用户必填，选项：1，非长期；2，长期
     */
    public void setIdValidType(Integer idValidType) {
        this.idValidType = idValidType;
    }
    /**
     * 获取：法人证件有效期 非必填 企业用户必填，选项：1，非长期；2，长期
     */
    public Integer getIdValidType() {
        return idValidType;
    }
    /**
     * 设置：法人证件有效期开始日期 非必填 企业用户必填，日期格式要求：20180115
     */
    public void setIdStartDate(String idStartDate) {
        this.idStartDate = idStartDate;
    }
    /**
     * 获取：法人证件有效期开始日期 非必填 企业用户必填，日期格式要求：20180115
     */
    public String getIdStartDate() {
        return idStartDate;
    }
    /**
     * 设置：法人证件有效期证件结束日期   非必填 选择非长期填写，日期格式要求：20180115
     */
    public void setIdEndDate(String idEndDate) {
        this.idEndDate = idEndDate;
    }
    /**
     * 获取：法人证件有效期证件结束日期   非必填 选择非长期填写，日期格式要求：20180115
     */
    public String getIdEndDate() {
        return idEndDate;
    }
    /**
     * 设置：商户联系人   必填 商户联系人姓名。支持少数名族,最大支持15个汉字
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    /**
     * 获取：商户联系人   必填 商户联系人姓名。支持少数名族,最大支持15个汉字
     */
    public String getContactName() {
        return contactName;
    }
    /**
     * 设置：联系人证件类型 必填  选项：01:身份证、02:护照、04:军官证、03:港澳台通行证、05:回乡证，06:工商登记号 09:其他
     */
    public void setContactIdType(Integer contactIdType) {
        this.contactIdType = contactIdType;
    }
    /**
     * 获取：联系人证件类型 必填  选项：01:身份证、02:护照、04:军官证、03:港澳台通行证、05:回乡证，06:工商登记号 09:其他
     */
    public Integer getContactIdType() {
        return contactIdType;
    }
    /**
     * 设置：联系人证件号码 必填
     */
    public void setContactIdNo(String contactIdNo) {
        this.contactIdNo = contactIdNo;
    }
    /**
     * 获取：联系人证件号码 必填
     */
    public String getContactIdNo() {
        return contactIdNo;
    }
    /**
     * 设置：联系人证件有效期    必填 选项：1，非长期；2，长期
     */
    public void setContactIdValidType(Integer contactIdValidType) {
        this.contactIdValidType = contactIdValidType;
    }
    /**
     * 获取：联系人证件有效期    必填 选项：1，非长期；2，长期
     */
    public Integer getContactIdValidType() {
        return contactIdValidType;
    }
    /**
     * 设置：联系人证件有效期开始日期 必填  日期格式要求：20180115
     */
    public void setContactIdStartDate(String contactIdStartDate) {
        this.contactIdStartDate = contactIdStartDate;
    }
    /**
     * 获取：联系人证件有效期开始日期 必填  日期格式要求：20180115
     */
    public String getContactIdStartDate() {
        return contactIdStartDate;
    }
    /**
     * 设置：联系人证件有效期结束日期    非必填 选择非长期填写，日期格式要求：20180115
     */
    public void setContactIdEndDate(String contactIdEndDate) {
        this.contactIdEndDate = contactIdEndDate;
    }
    /**
     * 获取：联系人证件有效期结束日期    非必填 选择非长期填写，日期格式要求：20180115
     */
    public String getContactIdEndDate() {
        return contactIdEndDate;
    }
    /**
     * 设置：联系人手机   必填 手机号格式
     */
    public void setContactTelNo(String contactTelNo) {
        this.contactTelNo = contactTelNo;
    }
    /**
     * 获取：联系人手机   必填 手机号格式
     */
    public String getContactTelNo() {
        return contactTelNo;
    }
    /**
     * 设置：联系人邮箱   必填 邮箱格式
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    /**
     * 获取：联系人邮箱   必填 邮箱格式
     */
    public String getContactEmail() {
        return contactEmail;
    }
    /**
     * 设置：合同编号    必填 填写协议上的实际合同编号（个体商户默认代理商统一的合同编号）
     */
    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }
    /**
     * 获取：合同编号    必填 填写协议上的实际合同编号（个体商户默认代理商统一的合同编号）
     */
    public String getContactNum() {
        return contactNum;
    }
    /**
     * 设置：签约业务经理  必填 实际签约的业务经理。
     */
    public void setSignName(String signName) {
        this.signName = signName;
    }
    /**
     * 获取：签约业务经理  必填 实际签约的业务经理。
     */
    public String getSignName() {
        return signName;
    }
    /**
     * 设置：签约日期    必填 实际签约的日期。
     */
    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }
    /**
     * 获取：签约日期    必填 实际签约的日期。
     */
    public String getSignDate() {
        return signDate;
    }
    /**
     * 设置：合同有效期开始日期   必填 日期格式要求：20180115
     */
    public void setSignStartDate(String signStartDate) {
        this.signStartDate = signStartDate;
    }
    /**
     * 获取：合同有效期开始日期   必填 日期格式要求：20180115
     */
    public String getSignStartDate() {
        return signStartDate;
    }
    /**
     * 设置：合同有效期结束日期   必填 日期格式要求：20180115
     */
    public void setSignEndDate(String signEndDate) {
        this.signEndDate = signEndDate;
    }
    /**
     * 获取：合同有效期结束日期   必填 日期格式要求：20180115
     */
    public String getSignEndDate() {
        return signEndDate;
    }
    /**
     * 设置：管理员账号   必填 必填，建议使用手机号或联系人姓名拼音+生日，避免重复
     */
    public void setTellerId(String tellerId) {
        this.tellerId = tellerId;
    }
    /**
     * 获取：管理员账号   必填 必填，建议使用手机号或联系人姓名拼音+生日，避免重复
     */
    public String getTellerId() {
        return tellerId;
    }
    /**
     * 设置：是否发送短信通知商户 必填  发送短信通知商户联系人，包含控台地址、管理员账号、密码 //1：是，0：否
     */
    public void setBoolSendMes(Boolean boolSendMes) {
        this.boolSendMes = boolSendMes;
    }
    /**
     * 获取：是否发送短信通知商户 必填  发送短信通知商户联系人，包含控台地址、管理员账号、密码 //1：是，0：否
     */
    public Boolean getBoolSendMes() {
        return boolSendMes;
    }
    /**
     * 设置：结算账户类型 必填  选项：0.对公 1.对私
     */
    public void setBoolPrivate(Boolean boolPrivate) {
        this.boolPrivate = boolPrivate;
    }
    /**
     * 获取：结算账户类型 必填  选项：0.对公 1.对私
     */
    public Boolean getBoolPrivate() {
        return boolPrivate;
    }
    /**
     * 设置：结算账户名 必填  结算账户名称，对公结算时，结算账户名称需与商户注册名称一致
     */
    public void setBankActName(String bankActName) {
        this.bankActName = bankActName;
    }
    /**
     * 获取：结算账户名 必填  结算账户名称，对公结算时，结算账户名称需与商户注册名称一致
     */
    public String getBankActName() {
        return bankActName;
    }
    /**
     * 设置：结算账号 必填  结账账号
     */
    public void setBankActId(String bankActId) {
        this.bankActId = bankActId;
    }
    /**
     * 获取：结算账号 必填  结账账号
     */
    public String getBankActId() {
        return bankActId;
    }
    /**
     * 设置：银行所在省 必填  参考附录1：比如河北省，请填写河北
     */
    public void setBankProv(String bankProv) {
        this.bankProv = bankProv;
    }
    /**
     * 获取：银行所在省 必填  参考附录1：比如河北省，请填写河北
     */
    public String getBankProv() {
        return bankProv;
    }
    /**
     * 设置：银行所在市 必填  参考附录1：比如唐山市，请填写唐山市
     */
    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }
    /**
     * 获取：银行所在市 必填  参考附录1：比如唐山市，请填写唐山市
     */
    public String getBankCity() {
        return bankCity;
    }
    /**
     * 设置：银行所在区县 必填  参考附录1：比如XX区，请填写XX区
     */
    public void setBankArea(String bankArea) {
        this.bankArea = bankArea;
    }
    /**
     * 获取：银行所在区县 必填  参考附录1：比如XX区，请填写XX区
     */
    public String getBankArea() {
        return bankArea;
    }
    /**
     * 设置：银行名称 必填  参考附录2:填写
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    /**
     * 获取：银行名称 必填  参考附录2:填写
     */
    public String getBankName() {
        return bankName;
    }
    /**
     * 设置：结算周期 必填  选项：1代表T+1； 2代表D+1
     */
    public void setSettleTerm(Integer settleTerm) {
        this.settleTerm = settleTerm;
    }
    /**
     * 获取：结算周期 必填  选项：1代表T+1； 2代表D+1
     */
    public Integer getSettleTerm() {
        return settleTerm;
    }
    /**
     * 设置：D+1结算手续费（%）	非必填 选择：D+1时必填
     */
    public void setFee01(Double fee01) {
        this.fee01 = fee01;
    }
    /**
     * 获取：D+1结算手续费（%）	非必填 选择：D+1时必填
     */
    public Double getFee01() {
        return fee01;
    }
    /**
     * 设置：持卡人证件类型 非必填 对私结算时必填        //选项：01:身份证、02:护照、04:军官证、03:港澳台通行证、05:回乡证，06:工商登记号 09:其他
     */
    public void setAccountIdType(Integer accountIdType) {
        this.accountIdType = accountIdType;
    }
    /**
     * 获取：持卡人证件类型 非必填 对私结算时必填        //选项：01:身份证、02:护照、04:军官证、03:港澳台通行证、05:回乡证，06:工商登记号 09:其他
     */
    public Integer getAccountIdType() {
        return accountIdType;
    }
    /**
     * 设置：持卡人证件号码 非必填 对私结算时必填
     */
    public void setAccountIdNo(String accountIdNo) {
        this.accountIdNo = accountIdNo;
    }
    /**
     * 获取：持卡人证件号码 非必填 对私结算时必填
     */
    public String getAccountIdNo() {
        return accountIdNo;
    }
    /**
     * 设置：证件有效期   非必填 对私结算时必填 //选项：1，非长期；2，长期
     */
    public void setAccountIdValidType(Integer accountIdValidType) {
        this.accountIdValidType = accountIdValidType;
    }
    /**
     * 获取：证件有效期   非必填 对私结算时必填 //选项：1，非长期；2，长期
     */
    public Integer getAccountIdValidType() {
        return accountIdValidType;
    }
    /**
     * 设置：证件有效期开始日期 非必填 对私结算时必填  日期格式要求：20180115
     */
    public void setAccountIdStartDate(String accountIdStartDate) {
        this.accountIdStartDate = accountIdStartDate;
    }
    /**
     * 获取：证件有效期开始日期 非必填 对私结算时必填  日期格式要求：20180115
     */
    public String getAccountIdStartDate() {
        return accountIdStartDate;
    }
    /**
     * 设置：证件有效期结束日期   非必填 选择非长期填写，日期格式要求：20180115
     */
    public void setAccountIdEndDate(String accountIdEndDate) {
        this.accountIdEndDate = accountIdEndDate;
    }
    /**
     * 获取：证件有效期结束日期   非必填 选择非长期填写，日期格式要求：20180115
     */
    public String getAccountIdEndDate() {
        return accountIdEndDate;
    }
    /**
     * 设置：是否开通TS业务(秒到业务)    必填 选项：0：否 1：是
     */
    public void setAuth24(Boolean auth24) {
        this.auth24 = auth24;
    }
    /**
     * 获取：是否开通TS业务(秒到业务)    必填 选项：0：否 1：是
     */
    public Boolean getAuth24() {
        return auth24;
    }
    /**
     * 设置：TS结算手续费（固定）	非必填 选择固定手续费时填写
     */
    public void setFee20(Double fee20) {
        this.fee20 = fee20;
    }
    /**
     * 获取：TS结算手续费（固定）	非必填 选择固定手续费时填写
     */
    public Double getFee20() {
        return fee20;
    }
    /**
     * 设置：TS结算手续费（%）	非必填 选择百分比手续费时填写
     */
    public void setFee21(Double fee21) {
        this.fee21 = fee21;
    }
    /**
     * 获取：TS结算手续费（%）	非必填 选择百分比手续费时填写
     */
    public Double getFee21() {
        return fee21;
    }
    /**
     * 设置：封顶值 非必填 选择百分比手续费时填写
     */
    public void setFee22(Double fee22) {
        this.fee22 = fee22;
    }
    /**
     * 获取：封顶值 非必填 选择百分比手续费时填写
     */
    public Double getFee22() {
        return fee22;
    }
    /**
     * 设置：TS起结金额（元）	非必填 选项开通TS业务时填写
     */
    public void setTsCashLimit(Double tsCashLimit) {
        this.tsCashLimit = tsCashLimit;
    }
    /**
     * 获取：TS起结金额（元）	非必填 选项开通TS业务时填写
     */
    public Double getTsCashLimit() {
        return tsCashLimit;
    }
    /**
     * 设置：是否开通银行卡支付   必填 选项：0：否 1：是
     */
    public void setAuth23(Boolean auth23) {
        this.auth23 = auth23;
    }
    /**
     * 获取：是否开通银行卡支付   必填 选项：0：否 1：是
     */
    public Boolean getAuth23() {
        return auth23;
    }
    /**
     * 设置：手续费类型 非必填 开通银行卡支付必填 选项：01-减免类 02-优惠类 03-标准类
     */
    public void setChargeCateCode(Integer chargeCateCode) {
        this.chargeCateCode = chargeCateCode;
    }
    /**
     * 获取：手续费类型 非必填 开通银行卡支付必填 选项：01-减免类 02-优惠类 03-标准类
     */
    public Integer getChargeCateCode() {
        return chargeCateCode;
    }
    /**
     * 设置：借记卡手续费（%）	非必填 开通银行卡支付必填 减免类手续费为0，其他类型手续费保留小数点后4位。如：0.5533
     */
    public void setFee02(Double fee02) {
        this.fee02 = fee02;
    }
    /**
     * 获取：借记卡手续费（%）	非必填 开通银行卡支付必填 减免类手续费为0，其他类型手续费保留小数点后4位。如：0.5533
     */
    public Double getFee02() {
        return fee02;
    }
    /**
     * 设置：封顶  非必填 减免类为0，其他封顶值精确点小数点后2位。如：18.55元
     */
    public void setFee04(Double fee04) {
        this.fee04 = fee04;
    }
    /**
     * 获取：封顶  非必填 减免类为0，其他封顶值精确点小数点后2位。如：18.55元
     */
    public Double getFee04() {
        return fee04;
    }
    /**
     * 设置：贷记卡手续费（%）	非必填 开通银行卡支付必填 减免类为0，其他类型手续费保留小数点后4位。如：0.5533
     */
    public void setFee03(Double fee03) {
        this.fee03 = fee03;
    }
    /**
     * 获取：贷记卡手续费（%）	非必填 开通银行卡支付必填 减免类为0，其他类型手续费保留小数点后4位。如：0.5533
     */
    public Double getFee03() {
        return fee03;
    }
    /**
     * 设置：银联现场注册码 非必填 银联现场注册商户填写
     */
    public void setUnionRegCode(String unionRegCode) {
        this.unionRegCode = unionRegCode;
    }
    /**
     * 获取：银联现场注册码 非必填 银联现场注册商户填写
     */
    public String getUnionRegCode() {
        return unionRegCode;
    }
    /**
     * 设置：云闪付借记卡手续费（%）	非必填 开通银行卡支付必填 减免类手续费为0，其他类型手续费保留小数点后4位。如：0.5533
     */
    public void setFee05(Double fee05) {
        this.fee05 = fee05;
    }
    /**
     * 获取：云闪付借记卡手续费（%）	非必填 开通银行卡支付必填 减免类手续费为0，其他类型手续费保留小数点后4位。如：0.5533
     */
    public Double getFee05() {
        return fee05;
    }
    /**
     * 设置：封顶(元)   非必填 减免类为0，其他封顶值精确点小数点后2位。如：18.55
     */
    public void setFee07(Double fee07) {
        this.fee07 = fee07;
    }
    /**
     * 获取：封顶(元)   非必填 减免类为0，其他封顶值精确点小数点后2位。如：18.55
     */
    public Double getFee07() {
        return fee07;
    }
    /**
     * 设置：云闪付贷记卡手续费（%）	非必填 开通银行卡支付必填         //减免类为0，其他类型手续费保留小数点后4位。如：0.5533
     */
    public void setFee06(Double fee06) {
        this.fee06 = fee06;
    }
    /**
     * 获取：云闪付贷记卡手续费（%）	非必填 开通银行卡支付必填         //减免类为0，其他类型手续费保留小数点后4位。如：0.5533
     */
    public Double getFee06() {
        return fee06;
    }
    /**
     * 设置：开通银联二维码 非必填 选项：0：否 1：是
     */
    public void setAuth20(Boolean auth20) {
        this.auth20 = auth20;
    }
    /**
     * 获取：开通银联二维码 非必填 选项：0：否 1：是
     */
    public Boolean getAuth20() {
        return auth20;
    }
    /**
     * 设置：借记卡手续费（%）	非必填 选择开通银联二维码业务后必填。手续费保留小数点后4位。如：0.5533
     */
    public void setFee23(Double fee23) {
        this.fee23 = fee23;
    }
    /**
     * 获取：借记卡手续费（%）	非必填 选择开通银联二维码业务后必填。手续费保留小数点后4位。如：0.5533
     */
    public Double getFee23() {
        return fee23;
    }
    /**
     * 设置：封顶(元)   非必填 精确点小数点后2位。如：18.55元
     */
    public void setFee24(Double fee24) {
        this.fee24 = fee24;
    }
    /**
     * 获取：封顶(元)   非必填 精确点小数点后2位。如：18.55元
     */
    public Double getFee24() {
        return fee24;
    }
    /**
     * 设置：贷记卡手续费（%）	非必填 选择开通银联二维码业务后必填。手续费保留小数点后4位。如：0.5533
     */
    public void setFee25(Double fee25) {
        this.fee25 = fee25;
    }
    /**
     * 获取：贷记卡手续费（%）	非必填 选择开通银联二维码业务后必填。手续费保留小数点后4位。如：0.5533
     */
    public Double getFee25() {
        return fee25;
    }
    /**
     * 设置：是否开通微信支付    必填 选项：0：否 1：是
     */
    public void setAuth18(Boolean auth18) {
        this.auth18 = auth18;
    }
    /**
     * 获取：是否开通微信支付    必填 选项：0：否 1：是
     */
    public Boolean getAuth18() {
        return auth18;
    }
    /**
     * 设置：商户经营类目（微信）	非必填 开通微信支付业务时必填，参照附录4微信支付宝经营类目对照表
     */
    public void setWechatCateCode(String wechatCateCode) {
        this.wechatCateCode = wechatCateCode;
    }
    /**
     * 获取：商户经营类目（微信）	非必填 开通微信支付业务时必填，参照附录4微信支付宝经营类目对照表
     */
    public String getWechatCateCode() {
        return wechatCateCode;
    }
    /**
     * 设置：微信支付手续费 非必填 开通微信支付业务时必填，填写相应的手续费，精确到小数点后4位。如：0.5533        //选择否，不填写
     */
    public void setFee08(Double fee08) {
        this.fee08 = fee08;
    }
    /**
     * 获取：微信支付手续费 非必填 开通微信支付业务时必填，填写相应的手续费，精确到小数点后4位。如：0.5533        //选择否，不填写
     */
    public Double getFee08() {
        return fee08;
    }
    /**
     * 设置：公众号支付appid 非必填 微信公众号支付必填 如：wx191306eb02bea157
     */
    public void setWechatPubNumAppid(String wechatPubNumAppid) {
        this.wechatPubNumAppid = wechatPubNumAppid;
    }
    /**
     * 获取：公众号支付appid 非必填 微信公众号支付必填 如：wx191306eb02bea157
     */
    public String getWechatPubNumAppid() {
        return wechatPubNumAppid;
    }
    /**
     * 设置：公众号支付授权目录 非必填 微信公众号支付必填 如：http://mtest2.dzq.com/qcode
     */
    public void setWechatPubNumAuth(String wechatPubNumAuth) {
        this.wechatPubNumAuth = wechatPubNumAuth;
    }
    /**
     * 获取：公众号支付授权目录 非必填 微信公众号支付必填 如：http://mtest2.dzq.com/qcode
     */
    public String getWechatPubNumAuth() {
        return wechatPubNumAuth;
    }
    /**
     * 设置：关注公众号   非必填 如果不填写默认关注汇付微信公众号
     */
    public void setWechatPubNum(String wechatPubNum) {
        this.wechatPubNum = wechatPubNum;
    }
    /**
     * 获取：关注公众号   非必填 如果不填写默认关注汇付微信公众号
     */
    public String getWechatPubNum() {
        return wechatPubNum;
    }
    /**
     * 设置：是否为商户的公众号   必填 微信公众号支付必填        //选项：1）是；0）否
     */
    public void setIsMerchantWechat(Boolean isMerchantWechat) {
        this.isMerchantWechat = isMerchantWechat;
    }
    /**
     * 获取：是否为商户的公众号   必填 微信公众号支付必填        //选项：1）是；0）否
     */
    public Boolean getIsMerchantWechat() {
        return isMerchantWechat;
    }
    /**
     * 设置：是否开通支付宝支付 必填  选项：1.是；0.否
     */
    public void setAuth19(Boolean auth19) {
        this.auth19 = auth19;
    }
    /**
     * 获取：是否开通支付宝支付 必填  选项：1.是；0.否
     */
    public Boolean getAuth19() {
        return auth19;
    }
    /**
     * 设置：商户经营类目（支付宝）	非必填 开通支付宝支付业务时必填，参照附录4微信支付宝经营类目对照表
     */
    public void setAlipayCateCode(String alipayCateCode) {
        this.alipayCateCode = alipayCateCode;
    }
    /**
     * 获取：商户经营类目（支付宝）	非必填 开通支付宝支付业务时必填，参照附录4微信支付宝经营类目对照表
     */
    public String getAlipayCateCode() {
        return alipayCateCode;
    }
    /**
     * 设置：支付宝手续费 非必填 选择是，填写相应的手续费，精确到小数点后4位。如：0.5533        //选择否，不填写
     */
    public void setFee12(Double fee12) {
        this.fee12 = fee12;
    }
    /**
     * 获取：支付宝手续费 非必填 选择是，填写相应的手续费，精确到小数点后4位。如：0.5533        //选择否，不填写
     */
    public Double getFee12() {
        return fee12;
    }
    /**
     * 设置：代理商会员号	必填	代理商会员号18位 310000015000017897
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    /**
     * 获取：代理商会员号	必填	代理商会员号18位 310000015000017897
     */
    public String getMemberId() {
        return memberId;
    }
    /**
     * 设置：代理商操作员	必填	代理商操作员号 CUXXWB
     */
    public void setOpTellerId(String opTellerId) {
        this.opTellerId = opTellerId;
    }
    /**
     * 获取：代理商操作员	必填	代理商操作员号 CUXXWB
     */
    public String getOpTellerId() {
        return opTellerId;
    }
    /**
     * 设置：异步通知地址	非必填	审核结果异步通知推送地址
     */
    public void setBgRetUrl(String bgRetUrl) {
        this.bgRetUrl = bgRetUrl;
    }
    /**
     * 获取：异步通知地址	非必填	审核结果异步通知推送地址
     */
    public String getBgRetUrl() {
        return bgRetUrl;
    }
    /**
     * 设置：请求流水号	非必填	由接入方生成且保证唯一，查询时会用到
     */
    public void setReqSerialNum(String reqSerialNum) {
        this.reqSerialNum = reqSerialNum;
    }
    /**
     * 获取：请求流水号	非必填	由接入方生成且保证唯一，查询时会用到
     */
    public String getReqSerialNum() {
        return reqSerialNum;
    }
    /**
     * 设置：非必填	APP支付必填        //如：wx191306eb02bea157
     */
    public void setWxAppid(String wxAppid) {
        this.wxAppid = wxAppid;
    }
    /**
     * 获取：非必填	APP支付必填        //如：wx191306eb02bea157
     */
    public String getWxAppid() {
        return wxAppid;
    }
    /**
     * 设置：商户种类	必填	选项: 1-政府机构//2-国营企业//3-私营企业//4-外资企业//5-个体工商户//7-事业单位
     */
    public void setPnrPayMerchantType(Integer pnrPayMerchantType) {
        this.pnrPayMerchantType = pnrPayMerchantType;
    }
    /**
     * 获取：商户种类	必填	选项: 1-政府机构//2-国营企业//3-私营企业//4-外资企业//5-个体工商户//7-事业单位
     */
    public Integer getPnrPayMerchantType() {
        return pnrPayMerchantType;
    }
    /**
     * 设置：图片上传方式	必填	01 控台上传（默认）;        //02 接口上传
     */
    public void setPicUploadWay(Integer picUploadWay) {
        this.picUploadWay = picUploadWay;
    }
    /**
     * 获取：图片上传方式	必填	01 控台上传（默认）;        //02 接口上传
     */
    public Integer getPicUploadWay() {
        return picUploadWay;
    }
    /**
     * 设置：图片文件ID	非必填	当图片上传方式picUploadWay= “02”(接口上传)时，picture IDs （图片文件ID）字段必填，通过json串儿的方式，指定图片类型及图片文件编号//pictureIDs内容格式： {" archFlph01":"afc24351-b58c-4261-950f-64571b064f36"," archFlph02":"bfc24351-b58c-4261-950f-64571b064f35",...}若某个字段没图片，则赋值为空。例：{“archFlph02”: “”}
     */
    public void setPictureIds(String pictureIds) {
        this.pictureIds = pictureIds;
    }
    /**
     * 获取：图片文件ID	非必填	当图片上传方式picUploadWay= “02”(接口上传)时，picture IDs （图片文件ID）字段必填，通过json串儿的方式，指定图片类型及图片文件编号//pictureIDs内容格式： {" archFlph01":"afc24351-b58c-4261-950f-64571b064f36"," archFlph02":"bfc24351-b58c-4261-950f-64571b064f35",...}若某个字段没图片，则赋值为空。例：{“archFlph02”: “”}
     */
    public String getPictureIds() {
        return pictureIds;
    }
    /**
     * 设置：是否开通延迟入账	非必填	选项：0：否 1：是
     */
    public void setAuth29(Integer auth29) {
        this.auth29 = auth29;
    }
    /**
     * 获取：是否开通延迟入账	非必填	选项：0：否 1：是
     */
    public Integer getAuth29() {
        return auth29;
    }
    /**
     * 设置：状态 0=申请 1=审核成功 2=失败 3=冻结
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    /**
     * 获取：状态 0=申请 1=审核成功 2=失败 3=冻结
     */
    public Integer getStatus() {
        return status;
    }
    /**
     * 设置：产品号
     */
    public void setProdId(String prodId) {
        this.prodId = prodId;
    }
    /**
     * 获取：产品号
     */
    public String getProdId() {
        return prodId;
    }
    /**
     * 设置：系统号
     */
    public void setSysId(String sysId) {
        this.sysId = sysId;
    }
    /**
     * 获取：系统号
     */
    public String getSysId() {
        return sysId;
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
    /**
     * 设置：营业执照图片.企业商户必填，格式：数字+数字，如：1_1，表示第一个商户的营业执照照片，与zip包中的图片名称对应
     */
    public void setArchflph01(String archflph01) {
        this.archflph01 = archflph01;
    }
    /**
     * 获取：营业执照图片.企业商户必填，格式：数字+数字，如：1_1，表示第一个商户的营业执照照片，与zip包中的图片名称对应
     */
    public String getArchflph01() {
        return archflph01;
    }
    /**
     * 设置：税务登记证.企业商户必填，格式：数字+数字，如：1_2，表示第一个商户的税务登记证照片，与zip包中的图片名称对应
     */
    public void setArchflph02(String archflph02) {
        this.archflph02 = archflph02;
    }
    /**
     * 获取：税务登记证.企业商户必填，格式：数字+数字，如：1_2，表示第一个商户的税务登记证照片，与zip包中的图片名称对应
     */
    public String getArchflph02() {
        return archflph02;
    }
    /**
     * 设置：组织机构代码证.企业商户必填，格式：数字+数字，如：1_3，表示第一个商户的组织机构代码证照片，与zip包中的图片名称对应
     */
    public void setArchflph03(String archflph03) {
        this.archflph03 = archflph03;
    }
    /**
     * 获取：组织机构代码证.企业商户必填，格式：数字+数字，如：1_3，表示第一个商户的组织机构代码证照片，与zip包中的图片名称对应
     */
    public String getArchflph03() {
        return archflph03;
    }
    /**
     * 设置：开户许可证.对公结算必填，格式：数字+数字，如：1_4，表示第一个商户的开户许可证照片，与zip包中的图片名称对应
     */
    public void setArchflph04(String archflph04) {
        this.archflph04 = archflph04;
    }
    /**
     * 获取：开户许可证.对公结算必填，格式：数字+数字，如：1_4，表示第一个商户的开户许可证照片，与zip包中的图片名称对应
     */
    public String getArchflph04() {
        return archflph04;
    }
    /**
     * 设置：法人身份证正面.企业商户必填，格式：数字+数字，如：1_5，表示第一个商户的法人身份证正面照片，与zip包中的图片名称对应
     */
    public void setArchflph05(String archflph05) {
        this.archflph05 = archflph05;
    }
    /**
     * 获取：法人身份证正面.企业商户必填，格式：数字+数字，如：1_5，表示第一个商户的法人身份证正面照片，与zip包中的图片名称对应
     */
    public String getArchflph05() {
        return archflph05;
    }
    /**
     * 设置：法人身份证反面.企业商户必填，格式：数字+数字，如：1_6，表示第一个商户的法人身份证反面照片，与zip包中的图片名称对应
     */
    public void setArchflph06(String archflph06) {
        this.archflph06 = archflph06;
    }
    /**
     * 获取：法人身份证反面.企业商户必填，格式：数字+数字，如：1_6，表示第一个商户的法人身份证反面照片，与zip包中的图片名称对应
     */
    public String getArchflph06() {
        return archflph06;
    }
    /**
     * 设置：结算人身份证正面.个体商户必填，格式：数字+数字，如：1_7，表示第一个商户的结算人身份证正面照片，与zip包中的图片名称对应
     */
    public void setArchflph07(String archflph07) {
        this.archflph07 = archflph07;
    }
    /**
     * 获取：结算人身份证正面.个体商户必填，格式：数字+数字，如：1_7，表示第一个商户的结算人身份证正面照片，与zip包中的图片名称对应
     */
    public String getArchflph07() {
        return archflph07;
    }
    /**
     * 设置：结算人身份证反面.个体商户必填，格式：数字+数字，如：1_8，表示第一个商户的结算人身份证反面照片，与zip包中的图片名称对应
     */
    public void setArchflph08(String archflph08) {
        this.archflph08 = archflph08;
    }
    /**
     * 获取：结算人身份证反面.个体商户必填，格式：数字+数字，如：1_8，表示第一个商户的结算人身份证反面照片，与zip包中的图片名称对应
     */
    public String getArchflph08() {
        return archflph08;
    }
    /**
     * 设置：商务协议.格式：数字+数字，如：1_9，表示第一个商户的商务协议照片，与zip包中的图片名称对应
     */
    public void setArchflph09(String archflph09) {
        this.archflph09 = archflph09;
    }
    /**
     * 获取：商务协议.格式：数字+数字，如：1_9，表示第一个商户的商务协议照片，与zip包中的图片名称对应
     */
    public String getArchflph09() {
        return archflph09;
    }
    /**
     * 设置：公司照片一.企业商户必填，格式：数字+数字，如：1_10，表示第一个商户的公司照片一，与zip包中的图片名称对应
     */
    public void setArchflph10(String archflph10) {
        this.archflph10 = archflph10;
    }
    /**
     * 获取：公司照片一.企业商户必填，格式：数字+数字，如：1_10，表示第一个商户的公司照片一，与zip包中的图片名称对应
     */
    public String getArchflph10() {
        return archflph10;
    }
    /**
     * 设置：公司照片二.企业商户必填，格式：数字+数字，如：1_11，表示第一个商户的公司照片二，与zip包中的图片名称对应
     */
    public void setArchflph11(String archflph11) {
        this.archflph11 = archflph11;
    }
    /**
     * 获取：公司照片二.企业商户必填，格式：数字+数字，如：1_11，表示第一个商户的公司照片二，与zip包中的图片名称对应
     */
    public String getArchflph11() {
        return archflph11;
    }
    /**
     * 设置：公司照片三.企业商户必填，格式：数字+数字，如：1_12，表示第一个商户的公司照片三，与zip包中的图片名称对应
     */
    public void setArchflph12(String archflph12) {
        this.archflph12 = archflph12;
    }
    /**
     * 获取：公司照片三.企业商户必填，格式：数字+数字，如：1_12，表示第一个商户的公司照片三，与zip包中的图片名称对应
     */
    public String getArchflph12() {
        return archflph12;
    }
    /**
     * 设置：联系人身份证正面.个体商户必填，格式：数字+数字，如：1_13，表示第一个商户的联系人身份证正面照片，与zip包中的图片名称对应
     */
    public void setArchflph13(String archflph13) {
        this.archflph13 = archflph13;
    }
    /**
     * 获取：联系人身份证正面.个体商户必填，格式：数字+数字，如：1_13，表示第一个商户的联系人身份证正面照片，与zip包中的图片名称对应
     */
    public String getArchflph13() {
        return archflph13;
    }
    /**
     * 设置：联系人身份证反面.个体商户必填，格式：数字+数字，如：1_14，表示第一个商户的联系人身份证反面照片，与zip包中的图片名称对应
     */
    public void setArchflph14(String archflph14) {
        this.archflph14 = archflph14;
    }
    /**
     * 获取：联系人身份证反面.个体商户必填，格式：数字+数字，如：1_14，表示第一个商户的联系人身份证反面照片，与zip包中的图片名称对应
     */
    public String getArchflph14() {
        return archflph14;
    }
    /**
     * 设置：店铺门头照片.开通微信支付宝时必填，格式：数字+数字，如：1_15，表示第一个商户的店铺门头照片，与zip包中的图片名称对应
     */
    public void setArchflph15(String archflph15) {
        this.archflph15 = archflph15;
    }
    /**
     * 获取：店铺门头照片.开通微信支付宝时必填，格式：数字+数字，如：1_15，表示第一个商户的店铺门头照片，与zip包中的图片名称对应
     */
    public String getArchflph15() {
        return archflph15;
    }
    /**
     * 设置：店铺收银台照片.开通微信支付宝时必填，格式：数字+数字，如：1_16，表示第一个商户的店铺收银台照片，与zip包中的图片名称对应
     */
    public void setArchflph16(String archflph16) {
        this.archflph16 = archflph16;
    }
    /**
     * 获取：店铺收银台照片.开通微信支付宝时必填，格式：数字+数字，如：1_16，表示第一个商户的店铺收银台照片，与zip包中的图片名称对应
     */
    public String getArchflph16() {
        return archflph16;
    }
    /**
     * 设置：店内照片.开通微信支付宝时必填，格式：数字+数字，如：1_17，表示第一个商户的店内照片，与zip包中的图片名称对应
     */
    public void setArchflph17(String archflph17) {
        this.archflph17 = archflph17;
    }
    /**
     * 获取：店内照片.开通微信支付宝时必填，格式：数字+数字，如：1_17，表示第一个商户的店内照片，与zip包中的图片名称对应
     */
    public String getArchflph17() {
        return archflph17;
    }
    /**
     * 设置：结算卡正面.对私结算必填，格式：数字+数字，如：1_18，表示第一个结算银行卡正面照片，与zip包中的图片名称对应
     */
    public void setArchflph18(String archflph18) {
        this.archflph18 = archflph18;
    }
    /**
     * 获取：结算卡正面.对私结算必填，格式：数字+数字，如：1_18，表示第一个结算银行卡正面照片，与zip包中的图片名称对应
     */
    public String getArchflph18() {
        return archflph18;
    }
    /**
     * 设置：结算卡反面.对私结算必填，格式：数字+数字，如：1_19，表示第一个结算银行卡反面照片，与zip包中的图片名称对应
     */
    public void setArchflph19(String archflph19) {
        this.archflph19 = archflph19;
    }
    /**
     * 获取：结算卡反面.对私结算必填，格式：数字+数字，如：1_19，表示第一个结算银行卡反面照片，与zip包中的图片名称对应
     */
    public String getArchflph19() {
        return archflph19;
    }
    /**
     * 设置：审核状态：0（成功），1（失败）
     */
    public void setCheckStatus(Boolean checkStatus) {
        this.checkStatus = checkStatus;
    }
    /**
     * 获取：审核状态：0（成功），1（失败）
     */
    public Boolean getCheckStatus() {
        return checkStatus;
    }
}
