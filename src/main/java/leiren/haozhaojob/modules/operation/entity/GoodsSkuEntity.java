package leiren.haozhaojob.modules.operation.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import leiren.haozhaojob.common.annotation.ExcelField;
import leiren.haozhaojob.common.validator.group.AddGroup;
import leiren.haozhaojob.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品种类
 * 
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-08-10 16:25:29
 */
@TableName("t_goods_sku")
public class GoodsSkuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	@TableId
	@ExcelField(columnName = "ID")
	private Integer id;
	/**
	 * 商品标签id
	 */
	@ExcelField(columnName = "LabelId")
	@NotNull(message="标签不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer labelId;

	/**
	 * 商品标签名称
	 */
    @TableField(exist=false)
	private String labelName;

	/**
	 * 商品名称
	 */
	@ExcelField(columnName = "goodsName")
	@NotNull(message="商品名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String goodsName;
	/**
	 * 商品详细
	 */
    @ExcelField(columnName = "details")
	private String details;
	/**
	 * 状态1为正常 2为暂停销售 3删除
	 */
    @ExcelField(columnName = "status")
	@NotNull(message="状态不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer status;
	/**
	 * 商品图片
	 */
    @ExcelField(columnName = "pictures")
	@NotNull(message="商品图片不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String pictures;
	/**
	 * 创建时间
	 */
    @ExcelField(columnName = "createTime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
	@NotNull(message="创建时间不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Date createTime;
	/**
	 * 创建人
	 */
    @ExcelField(columnName = "userId")
	@NotNull(message="创建人不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer userId;

    /**
     * 创建人名称
     */
    @TableField(exist=false)
    private String userName;

	/**
	 * 备注
	 */
    @ExcelField(columnName = "remark")
	private String remark;
	/**
	 * 商品编号
	 */
    @ExcelField(columnName = "code")
	@NotNull(message="商品编号不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String code;

	@TableLogic
    @ExcelField(columnName = "delFlag")
	private Integer delFlag;

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	/**
	 * 设置：商品id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：商品id
	 */
	public Integer getId() {
		return id;
	}
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
	 * 设置：商品名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**
	 * 获取：商品名称
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * 设置：商品详细
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	/**
	 * 获取：商品详细
	 */
	public String getDetails() {
		return details;
	}
	/**
	 * 设置：状态1为正常 2为暂停销售 3删除
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态1为正常 2为暂停销售 3删除
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：商品图片
	 */
	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	/**
	 * 获取：商品图片
	 */
	public String getPictures() {
		return pictures;
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
	 * 设置：创建人
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：创建人
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
