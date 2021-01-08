package com.paymentez.plazez.sdk.styles;

import com.paymentez.plazez.sdk.R;

public enum PmzFont {

    ROBOTO,
    COMIC_SANS,
    ARIAL,
    SERIF;

    public static int getFont(PmzFont font) {
        switch (font) {
            case ROBOTO:
                return R.font.roboto_regular;
            case ARIAL:
                return R.font.arial;
            case COMIC_SANS:
                return R.font.comic;
        }
        return R.font.roboto_regular;
    }
}
