package leiren.haozhaojob.modules.statistics.entity;

import io.swagger.models.auth.In;

/**
 * 商户收入统计
 *
 * @author liyun
 * @email liyun@scxxwb.com
 * @date 2018.06.21
 */
public class MerchantIncomeTotalEntity {

    /**
     * 总收入
     */
    private Double totalIncome;

    /**
     * 总笔数
     */
    private Integer totalNumberOfPen;

    /**
     * 微宝收入
     */
    private  Double weiBaoIncome;

    /**
     * 微宝笔数
     */
    private  Integer weiBaoNumberOfPen;

    /**
     * 总支出
     */
    private Double totalExpenditure;

    /**
     * 支出笔数
     */
    private Integer expenditureNumberOfPen;

    /**
     * 微信收入
     */
    private Double weChatIncome;

    /**
     * 支付宝收入
     */
    private Double alipayIncome;

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Integer getTotalNumberOfPen() {
        return totalNumberOfPen;
    }

    public void setTotalNumberOfPen(Integer totalNumberOfPen) {
        this.totalNumberOfPen = totalNumberOfPen;
    }

    public Double getWeiBaoIncome() {
        return weiBaoIncome;
    }

    public void setWeiBaoIncome(Double weiBaoIncome) {
        this.weiBaoIncome = weiBaoIncome;
    }

    public Integer getWeiBaoNumberOfPen() {
        return weiBaoNumberOfPen;
    }

    public void setWeiBaoNumberOfPen(Integer weiBaoNumberOfPen) {
        this.weiBaoNumberOfPen = weiBaoNumberOfPen;
    }

    public Double getTotalExpenditure() {
        return totalExpenditure;
    }

    public void setTotalExpenditure(Double totalExpenditure) {
        this.totalExpenditure = totalExpenditure;
    }

    public Integer getExpenditureNumberOfPen() {
        return expenditureNumberOfPen;
    }

    public void setExpenditureNumberOfPen(Integer expenditureNumberOfPen) {
        this.expenditureNumberOfPen = expenditureNumberOfPen;
    }

    public Double getWeChatIncome() {
        return weChatIncome;
    }

    public void setWeChatIncome(Double weChatIncome) {
        this.weChatIncome = weChatIncome;
    }

    public Double getAlipayIncome() {
        return alipayIncome;
    }

    public void setAlipayIncome(Double alipayIncome) {
        this.alipayIncome = alipayIncome;
    }
}
