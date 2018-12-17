package leiren.haozhaojob.common.contant;

/**
* crontab 表达式 枚举
* Created by leiren on 2018/1/5.
*/
public enum EJobCronEnum {
    OVERVIEW_INTERVAL_TEN_MINUTES("0 0/10 * * * ? *"),

    ACCOUNTS_FOCUS_INTERVAL_TEN_MINUTES("0 0/10 * * * ? *"),

    ACCOUNTS_INTERVAL_TEN_MINUTES("0 0/10 * * * ? *"),
    ACCOUNTS_INTERVAL_ONE_DAY("0 0 0 1/1 * ? *"),
    ACCOUNTS_INTERVAL_ONE_MONTH("0 0 0 1 1/1 ? *"),

    VOICES_INTERVAL_TEN_MINUTES("0 0/10 * * * ? *"),
    VOICES_INTERVAL_ONE_DAY("0 0 0 1/1 * ? *"),
    VOICES_INTERVAL_ONE_WEEK("0 0 0 1/7 * ? *"),
    VOICES_TREND_INTERVAL_FOUR_HOURS("0 0 0/4 * * ? *"),
    VOICES_ORIGIN_INTERVAL_ONE_DAY("0 0 0 1/1 * ? *"),
    VOICES_HOT_DEGREE_INTERVAL_TEN_MINUTES("0 0/10 * * * ? *"),
    VOICES_WEEK_HOT_DEGREE_INTERVAL_TEN_MINUTES("0 0/10 * * * ? *");

    private final String cron;

    EJobCronEnum(String cron) {
        this.cron = cron;
    }

    public String getCron() {
        return this.cron;
    }
}
