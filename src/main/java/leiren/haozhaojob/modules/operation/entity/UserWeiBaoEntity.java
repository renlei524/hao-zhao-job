package leiren.haozhaojob.modules.operation.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName("t_user_weibao")
public class UserWeiBaoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private  Integer userId;

    /**
     * 用户名称
     */
    @TableField(exist=false)
    private String userName;

    /**
     * 手机号码
     */
    @TableField(exist=false)
    private String mobile;

    /**
     * 领取微宝
     */
    @TableField(exist=false)
    private BigDecimal receiveWB;

    /**
     * 使用微宝
     */
    @TableField(exist=false)
    private BigDecimal useWB;

    /**
     * 可提现金额
     */
    private BigDecimal weibaoWithdrawals;
    /**
     * 不可提现
     */
    private  Integer weibaoNoWithdrawals;
    /**
     * 冻结金额
     */
    private  Integer weibaoFreezeUp;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getWeibaoWithdrawals() {
        return weibaoWithdrawals;
    }

    public void setWeibaoWithdrawals(BigDecimal weibaoWithdrawals) {
        this.weibaoWithdrawals = weibaoWithdrawals;
    }

    public Integer getWeibaoNoWithdrawals() {
        return weibaoNoWithdrawals;
    }

    public void setWeibaoNoWithdrawals(Integer weibaoNoWithdrawals) {
        this.weibaoNoWithdrawals = weibaoNoWithdrawals;
    }

    public Integer getWeibaoFreezeUp() {
        return weibaoFreezeUp;
    }

    public void setWeibaoFreezeUp(Integer weibaoFreezeUp) {
        this.weibaoFreezeUp = weibaoFreezeUp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getReceiveWB() {
        return receiveWB;
    }

    public void setReceiveWB(BigDecimal receiveWB) {
        this.receiveWB = receiveWB;
    }

    public BigDecimal getUseWB() {
        return useWB;
    }

    public void setUseWB(BigDecimal useWB) {
        this.useWB = useWB;
    }
}
