package leiren.haozhaojob.modules.operation.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 商户商品自定义分类表
 * 
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018-08-16 15:03:54
 */
@TableName("t_merchant_product_customer_label")
public class MerchantProductCustomerLabelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer labelId;
	/**
	 * 商家id 若为0即可看作是通用的商品标签
	 */
	private Integer merchantId;

    /**
     * 商户名称
     */
    @TableField(exist=false)
    private String merchantName;

	/**
	 * 标签名称
	 */
	private String labelName;
	/**
	 * 排序id
	 */
	private Integer sortNo;
	/**
	 * 创建时间
	 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	private Date createTime;
	/**
	 * 是否删除 0=正常，1=删除
	 */
	private Integer isDelete;

    /**
     * 审核状态
     */
	private Integer status;

    /**
     * 审核意见
     */
	private String remark;

	/**
	 * 设置：
	 */
	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}
	/**
	 * 获取：
	 */
	public Integer getLabelId() {
		return labelId;
	}
	/**
	 * 设置：商家id 若为0即可看作是通用的商品标签
	 */
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
	/**
	 * 获取：商家id 若为0即可看作是通用的商品标签
	 */
	public Integer getMerchantId() {
		return merchantId;
	}
	/**
	 * 设置：标签名称
	 */
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	/**
	 * 获取：标签名称
	 */
	public String getLabelName() {
		return labelName;
	}
	/**
	 * 设置：排序id
	 */
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	/**
	 * 获取：排序id
	 */
	public Integer getSortNo() {
		return sortNo;
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
	 * 设置：是否删除 0=正常，1=删除
	 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	/**
	 * 获取：是否删除 0=正常，1=删除
	 */
	public Integer getIsDelete() {
		return isDelete;
	}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
