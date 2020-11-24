package ar.com.fennoma.paymentezsdk.styles;

import ar.com.fennoma.paymentezsdk.R;

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
