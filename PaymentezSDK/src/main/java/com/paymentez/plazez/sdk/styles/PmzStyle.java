package com.paymentez.plazez.sdk.styles;

public class PmzStyle {

    private PmzFont font = PmzFont.ROBOTO;
    private Integer backgroundColor;
    private Integer textColor;
    private Integer buttonBackgroundColor;
    private Integer buttonTextColor;
    private Integer headerBackgroundColor;
    private Integer headerTextColor;

    public Integer getButtonBackgroundColor() {
        return buttonBackgroundColor;
    }

    public PmzStyle setButtonBackgroundColor(Integer buttonBackgroundColor) {
        this.buttonBackgroundColor = buttonBackgroundColor;
        return this;
    }

    public Integer getButtonTextColor() {
        return buttonTextColor;
    }

    public PmzStyle setButtonTextColor(Integer buttonTextColor) {
        this.buttonTextColor = buttonTextColor;
        return this;
    }

    public Integer getTextColor() {
        return textColor;
    }

    public PmzStyle setTextColor(Integer textColor) {
        this.textColor = textColor;
        return this;
    }

    public PmzStyle setBackgroundColor(int color) {
        backgroundColor = color;
        return this;
    }

    public Integer getBackgroundColor() {
        return backgroundColor;
    }

    public PmzFont getFont() {
        return font;
    }

    public PmzStyle setFont(PmzFont font) {
        this.font = font;
        return this;
    }

    public Integer getHeaderBackgroundColor() {
        return headerBackgroundColor;
    }

    public PmzStyle setHeaderBackgroundColor(Integer headerBackgroundColor) {
        this.headerBackgroundColor = headerBackgroundColor;
        return this;
    }

    public Integer getHeaderTextColor() {
        return headerTextColor;
    }

    public PmzStyle setHeaderTextColor(Integer headerTextColor) {
        this.headerTextColor = headerTextColor;
        return this;
    }
}
