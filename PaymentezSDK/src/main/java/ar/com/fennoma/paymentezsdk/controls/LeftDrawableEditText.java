package ar.com.fennoma.paymentezsdk.controls;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;

public class LeftDrawableEditText extends AppCompatEditText {

    public interface RightDrawableCLickListener {
        void onLeftDrawableClicked();
    }

    private RightDrawableCLickListener listener;
    private Drawable drawable;
    private final static int FUZZ = 10;

    public LeftDrawableEditText(Context context) {
        super(context);
        init();
    }

    public LeftDrawableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LeftDrawableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        final Drawable[] drawables = getCompoundDrawables();
        if (drawables.length == 4) {
            this.drawable = drawables[2];
        }
    }

    public void setListener(RightDrawableCLickListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && drawable != null && listener != null) {
            final int x = (int) event.getX();
            final int y = (int) event.getY();
            final Rect bounds = drawable.getBounds();
            /*if (x >= (getRight() - bounds.width() - FUZZ) && x <= (getRight() - getPaddingRight() + FUZZ)
                    && y >= (getPaddingTop() - FUZZ) && y <= (getHeight() - getPaddingBottom()) + FUZZ) {
                listener.onLeftDrawableClicked();
                return false;
            }*/
            if (x <= (getLeft() - bounds.width() - FUZZ) && x <= (getLeft() - getPaddingLeft() + FUZZ)
                    && y >= (getPaddingTop() - FUZZ) && y <= (getHeight() - getPaddingBottom()) + FUZZ) {
                listener.onLeftDrawableClicked();
                return false;
            }
        }

        return super.onTouchEvent(event);
    }
}
