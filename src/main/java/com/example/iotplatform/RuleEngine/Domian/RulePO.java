package com.example.iotplatform.RuleEngine.Domian;

import java.time.LocalDateTime;

public class RulePO {
    /**
     * 规则ID
     */
    private int id;

    private String rule;

    /**
     * 生效日期
     */
    private LocalDateTime effective_date;

    /**
     * 失效日期
     */
    private LocalDateTime expires_date;

    /**
    * 是否启用
    */
    private boolean enabled;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getEffective_date() {
        return effective_date;
    }

    public void setEffective_date(LocalDateTime effective_date) {
        this.effective_date = effective_date;
    }

    public LocalDateTime getExpires_date() {
        return expires_date;
    }

    public void setExpires_date(LocalDateTime expires_date) {
        this.expires_date = expires_date;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRule(){
        return this.rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

}
