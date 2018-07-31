package com.scxxwb.net.admin.modules.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.scxxwb.net.admin.common.validator.group.AddGroup;
import com.scxxwb.net.admin.common.validator.group.UpdateGroup;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.util.Date;


/**
 * 社区管理
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@TableName("t_community_sys_dept")
public class CommunitySysDeptEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //部门ID
    @TableId
    private Long deptId;

    @TableField(exist=false)
    private Integer parentId = 0;

    /**
     * 所属分公司
     */
    private Integer sysDeptId;

    //部门名称
    @NotNull(message="机构名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(max = 18, message = "机构名称不能超过18个字符", groups = {AddGroup.class, UpdateGroup.class})
    private String name;
    //排序
    private Integer orderNum;
    /**
     * 负责人
     */
    @NotNull(message="负责人不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(max = 15, message = "负责人姓名不能超过15个字符", groups = {AddGroup.class, UpdateGroup.class})
    private String leader;
    /**
     * 负责人电话号码
     */
    @NotNull(message="联系电话不能为空", groups = {Default.class,AddGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^\\d{11}$",message = "手机号码格式错误", groups = {Default.class,AddGroup.class, UpdateGroup.class})
    private String leaderTel;

    @TableLogic
    private Integer delFlag;
    /**
     * 省
     */
    @Min(value = 0, message = "所属地区不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer province;
    /**
     * 市
     */
    private Integer city;
    /**
     * 区
     */
    private Integer area;
    /**
     * 街道/乡镇
     */
    private Integer town;
    /**
     * 社区详细地址
     */
    private String address;
    /**
     * 创建用户ID
     */
    private Long userId;
    /**
     * 创建用户名称
     */
    @TableField(exist=false)
    private String creUserName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date creTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date updTime;

    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;
    /**
     * 审批人
     */
    private Integer approveUserId;
    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date approveTime;
    /**
     * 审批备注
     */
    private String approveRemark;
    /**
     * 版本号
     */
    private String version;
    /**
     * 是否有效
     */
    private String validFlag;
    /**
     * ztree属性
     */
    @TableField(exist=false)
    private Boolean open;

    @TableField(exist=false)
    private List<?> list;


    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    /**
     * 设置：部门名称
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 获取：部门名称
     */
    public String getName() {
        return name;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getLeaderTel() {
        return leaderTel;
    }

    public void setLeaderTel(String leaderTel) {
        this.leaderTel = leaderTel;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCreUserName() {
        return creUserName;
    }

    public void setCreUserName(String creUserName) {
        this.creUserName = creUserName;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 设置：排序
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
    /**
     * 获取：排序
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getTown() {
        return town;
    }

    public void setTown(Integer town) {
        this.town = town;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    public Integer getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(Integer approveUserId) {
        this.approveUserId = approveUserId;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getApproveRemark() {
        return approveRemark;
    }

    public void setApproveRemark(String approveRemark) {
        this.approveRemark = approveRemark;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSysDeptId() {
        return sysDeptId;
    }

    public void setSysDeptId(Integer sysDeptId) {
        this.sysDeptId = sysDeptId;
    }
}
