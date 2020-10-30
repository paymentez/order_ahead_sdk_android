package ar.com.fennoma.paymentezsdk.controls;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.drawable.DrawableCompat;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.controllers.PaymentezSDK;
import ar.com.fennoma.paymentezsdk.styles.PmzStyle;

public class QuantitySelector extends LinearLayout {

    public interface PmzIQuantitySelectorListener {
        void onQuantityChanged(int quantity);
    }

    private TextView count;
    private AppCompatImageView addButton;
    private AppCompatImageView minusButton;

    private int counter;
    private PmzIQuantitySelectorListener listener;

    public QuantitySelector(Context context) {
        super(context);
        initView();
    }

    public QuantitySelector(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public QuantitySelector(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.pmz_quantity_selector, this);
        setViews();
    }

    public int getCounter() {
        return counter;
    }

    public void setListener(PmzIQuantitySelectorListener listener) {
        this.listener = listener;
    }

    private void setViews() {
        counter = 1;
        count = findViewById(R.id.count);
        addButton = findViewById(R.id.plus);
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                setCount();
                if(listener != null) {
                    listener.onQuantityChanged(counter);
                }
            }
        });
        minusButton = findViewById(R.id.minus);
        minusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counter > 1) {
                    counter--;
                    setCount();
                    if(listener != null) {
                        listener.onQuantityChanged(counter);
                    }
                }
            }
        });
        PmzStyle style = PaymentezSDK.getInstance().getStyle();
        if(style != null) {
            setStyles(style);
        }
    }

    private void setStyles(PmzStyle style) {
        if(style.getTextColor() != null) {
            count.setTextColor(style.getTextColor());
        }
        if (style.getButtonBackgroundColor() != null) {
            addButton.setColorFilter(style.getButtonBackgroundColor());
            minusButton.setColorFilter(style.getButtonBackgroundColor());
        }
    }

    private void setCount() {
        count.setText(String.valueOf(counter));
    }
}
