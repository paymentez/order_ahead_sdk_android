package ar.com.fennoma.paymentezsdk.utils;

import android.content.Context;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import java.lang.reflect.Field;

public class TypefaceUtils {
    public static void overrideFont(Context context, String defaultFontNameToOverride, int fontId) {
        try {
            final Typeface customFontTypeface = ResourcesCompat.getFont(context, fontId);

            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
