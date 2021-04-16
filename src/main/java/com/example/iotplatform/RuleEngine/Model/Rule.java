package com.example.iotplatform.RuleEngine.Model;

import java.time.LocalDateTime;

public class Rule {
    /**
     * 规则ID
     */
    private int id;

    /**
     * 优先级
     */
    private int salience;

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

    /**
     * if/then规则部分
     */
    private String foreWare;
    private String hindWare;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalience() {
        return salience;
    }

    public void setSalience(int salience) {
        this.salience = salience;
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

    public String getForeWare() {
        return foreWare;
    }

    public void setForeWare(String foreWare) {
        this.foreWare = foreWare;
    }

    public String getHindWare() {
        return hindWare;
    }

    public void setHindWare(String hindWare) {
        this.hindWare = hindWare;
    }
    
}
