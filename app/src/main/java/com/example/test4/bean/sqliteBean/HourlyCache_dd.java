package com.example.test4.bean.sqliteBean;

public class HourlyCache_dd {

    private String dateText;
    private String infoText;
    private String  maxText;
    private String minText;

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
        return "HourlyCache_dd{" +
                "dateText='" + dateText + '\'' +
                ", infoText='" + infoText + '\'' +
                ", maxText='" + maxText + '\'' +
                ", minText='" + minText + '\'' +
                '}';
    }
}
