package ar.com.fennoma.paymentezsdk.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.models.PmzProductConfiguration;
import ar.com.fennoma.paymentezsdk.utils.PmzCurrencyUtils;

public class PmzRadioItem extends LinearLayout {

    private PmzProductConfiguration config;

    private CheckBox radio;
    private TextView price;

    public void setValue(boolean b) {
        config.setChecked(b);
        radio.setChecked(b);
    }

    public long getExtras() {
        if(config.isChecked()) {
            return config.getExtraPrice();
        }
        return 0;
    }

    public interface IPmzRadioListener {
        void onValueChanged(int position);
    }

    public PmzRadioItem(Context context) {
        super(context);
        init();
    }

    public PmzRadioItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PmzRadioItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.pmz_item_product, this);
        findViews();
    }

    private void findViews() {
        radio = findViewById(R.id.radio);
        price = findViewById(R.id.price);
    }

    public void setItem(final PmzProductConfiguration config, final int position, final boolean editable, final IPmzRadioListener listener) {
        this.config = config;

        radio.setText(config.getName());
        price.setText("+ ".concat(PmzCurrencyUtils.formatPrice(config.getExtraPrice())));

        radio.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = radio.isChecked();
                if(!b && editable) {
                    radio.setChecked(false);
                    config.setChecked(false);
                    if(listener != null) {
                        listener.onValueChanged(position);
                    }
                } else if(b) {
                    radio.setChecked(true);
                    config.setChecked(true);
                    if(listener != null) {
                        listener.onValueChanged(position);
                    }
                } else {
                    radio.setChecked(true);
                }
            }
        });
    }
}
