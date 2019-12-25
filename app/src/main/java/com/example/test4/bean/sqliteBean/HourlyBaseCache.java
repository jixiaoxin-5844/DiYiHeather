package com.example.test4.bean.sqliteBean;

import org.litepal.crud.LitePalSupport;

// 未来几小时天气信息缓存
public class HourlyBaseCache extends LitePalSupport {

    private int id;

    private String dateText;
    private String infoText;
    private String  maxText;
    private String minText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public String getInfoText() {
        return infoText;
    }

    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }

    public String getMaxText() {
        return maxText;
    }

    public void setMaxText(String maxText) {
        this.maxText = maxText;
    }

    public String getMinText() {
        return minText;
    }

    public void setMinText(String minText) {
        this.minText = minText;
    }

    @Override
    public String toString() {
        return "HourlyBaseCache{" +
                "id=" + id +
                ", dateText='" + dateText + '\'' +
                ", infoText='" + infoText + '\'' +
                ", maxText='" + maxText + '\'' +
                ", minText='" + minText + '\'' +
                '}';
    }
}

