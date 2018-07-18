package com.scxxwb.net.admin.modules.operation.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

@TableName("t_user_weibao")
public class TUserWeiBaoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private  Integer userId;
    /**
     * 可提现金额
     */
    private Integer weibaoWithdrawals;
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

    public Integer getWeibaoWithdrawals() {
        return weibaoWithdrawals;
    }

    public void setWeibaoWithdrawals(Integer weibaoWithdrawals) {
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
}
