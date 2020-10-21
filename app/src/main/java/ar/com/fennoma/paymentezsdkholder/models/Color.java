package ar.com.fennoma.paymentezsdkholder.models;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import ar.com.fennoma.paymentezsdkholder.R;

public class Color {

    private int colorRes;
    private String colorName;

    public static List<Color> getColorList(Activity activity) {
        List<Color> colors = new ArrayList<>();
        colors.add(new Color("Naranja", activity.getResources().getColor(R.color.sdk_orange)));
        colors.add(new Color("Rojo", activity.getResources().getColor(R.color.sdk_red)));
        colors.add(new Color("Azul", activity.getResources().getColor(R.color.sdk_blue)));
        colors.add(new Color("Verde", activity.getResources().getColor(R.color.sdk_green)));
        colors.add(new Color("Negro", activity.getResources().getColor(R.color.sdk_black)));
        colors.add(new Color("Blanco", activity.getResources().getColor(R.color.sdk_white)));
        return colors;
    }

    public Color(String name, int res) {
        this.colorName = name;
        this.colorRes = res;
    }

    public static List<String> convertToStringList(List<Color> colors) {
        List<String> names = new ArrayList<>();
        for(Color color: colors) {
            names.add(color.getColorName());
        }
        return names;
    }

    public int getColorRes() {
        return colorRes;
    }

    public void setColorRes(int colorRes) {
        this.colorRes = colorRes;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}
