package leiren.haozhaojob.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import leiren.haozhaojob.common.validator.group.AddGroup;
import leiren.haozhaojob.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * @author liyun
 * @email 25637208209@qq.com
 * @date 2018-06-26 17:01:55
 */
@TableName("t_sys_app")
public class AppEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 版本号
     */
    @NotNull(message="版本号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String version;

    /**
     * app类型（0：安卓；1：ios）
     */
    private int appType;

    /**
     * 文件地址
     */
    private String appUrl;

    /**
     * 版本备注
     */
    @NotNull(message="版本备注不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String remark;
    /**
     * 创建人
     */
    private Integer userId;

    /**
     * 创建人姓名
     */
    @TableField(exist=false)
    private String userName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date createTime;
    /**
     * 发布时间
     */
    @NotNull(message="发布时间不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Date publishTime;
    /**
     * 是否强制更新
     */
    private Integer isForceUpdate;

    /**
     * 设置：主键
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * 获取：主键
     */
    public Integer getId() {
        return id;
    }
    /**
     * 设置：版本号
     */
    public void setVersion(String version) {
        this.version = version;
    }
    /**
     * 获取：版本号
     */
    public String getVersion() {
        return version;
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
     * 设置：发布时间
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
    /**
     * 获取：发布时间
     */
    public Date getPublishTime() {
        return publishTime;
    }
    /**
     * 设置：是否强制更新
     */
    public void setIsForceUpdate(Integer isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
    }
    /**
     * 获取：是否强制更新
     */
    public Integer getIsForceUpdate() {
        return isForceUpdate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }
}
