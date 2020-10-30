package ar.com.fennoma.paymentezsdk.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

public class ColorHelper {

    public static void replaceButtonBackground(View button, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            replaceRippleBackgroundColor(button, color);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static void replaceRippleBackgroundColor(View button, int color) {
        RippleDrawable background = (RippleDrawable) button.getBackground();
        Drawable drawable = background.getDrawable(0);
        if(drawable != null) {
            ColorStateList myColorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{},
                            new int[]{android.R.attr.state_pressed},
                    },
                    new int[] {
                            color,
                            color
                    }
            );
            drawable.setTintList(myColorStateList);
        }
    }
}
