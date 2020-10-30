package ar.com.fennoma.paymentezsdk.styles;

public class PmzStyle {

    private Integer backgroundColor;
    private Integer textColor;
    private Integer buttonBackgroundColor;
    private Integer buttonTextColor;

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
}
