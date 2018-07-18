package com.scxxwb.net.admin.modules.statistics.entity;

/**
 * 业务员KPI
 *
 * @author liyuun
 * @email liyun@scxxwb.com
 * @date 2018.06.21
 */
public class SalesmanKPIEntity {

    /**
     * 业务员姓名
     */
    private String sysUsetName;

    /**
     * 数量
     */
    private Integer numBers;

    public String getSysUsetName() {
        return sysUsetName;
    }

    public void setSysUsetName(String sysUsetName) {
        this.sysUsetName = sysUsetName;
    }

    public Integer getNumBers() {
        return numBers;
    }

    public void setNumBers(Integer numBers) {
        this.numBers = numBers;
    }
}
