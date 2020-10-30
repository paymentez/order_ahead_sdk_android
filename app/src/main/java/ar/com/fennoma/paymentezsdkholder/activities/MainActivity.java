package ar.com.fennoma.paymentezsdkholder.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import ar.com.fennoma.paymentezsdk.controllers.PaymentezSDK;
import ar.com.fennoma.paymentezsdk.models.PmzBuyer;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.styles.PmzStyle;
import ar.com.fennoma.paymentezsdkholder.R;
import ar.com.fennoma.paymentezsdkholder.models.Color;

public class MainActivity extends BaseActivity {

    private List<Color> colors;

    private View button;

    private TextView bgColor;
    private TextView textColor;
    private TextView buttonColor;
    private TextView buttonTextColor;

    private Color bgColorSelected;
    private Color textColorSelected;
    private Color buttonColorSelected;
    private Color buttonTextColorSelected;

    private View bgColorShower;
    private View textColorShower;
    private View buttonColorShower;
    private View buttonTextColorShower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        colors = Color.getColorList(this);
        findViews();
        setViews();
        selectLifeMilesColors();
    }

    private void setViews() {
        setButton();
        setRandomizeButton();
        setBgSpinner();
        setTextSpinner();
        setButtonSpinner();
    }

    private void setRandomizeButton() {
        findViewById(R.id.randomize).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectRandomColors();
            }
        });
    }

    private void setButton() {
        final PmzBuyer buyer = new PmzBuyer().setName("Pepe").setPhone("123123123").setFiscalNumber("fiscalNumber")
                .setUserReference("userReference").setEmail("pepe@test.com.ar");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PmzStyle style = new PmzStyle().setBackgroundColor(bgColorSelected.getColorRes())
                        .setButtonBackgroundColor(buttonColorSelected.getColorRes())
                        .setTextColor(textColorSelected.getColorRes())
                        .setButtonTextColor(buttonTextColorSelected.getColorRes());

                PaymentezSDK.getInstance()
                        .setStyle(style)
                        .startSearch(MainActivity.this, buyer, "appReference", new PaymentezSDK.PmzSearchListener() {
                            @Override
                            public void onFinishedSuccessfully(PmzOrder order) {
                                Toast.makeText(MainActivity.this, getString(R.string.home_flow_success), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(MainActivity.this, getString(R.string.home_flow_cancelled), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }

    private void setBgSpinner() {
        View bgArea = findViewById(R.id.bg_color_area);
        Spinner bgSpinner = findViewById(R.id.bg_color_spinner);

        List<String> strings = Color.convertToStringList(colors);
        strings.add(0, getString(R.string.background_sdk_color_picker_title));

        setSpinner(strings, bgArea, bgSpinner, bgColor, new ISpinnerListener() {
            @Override
            public void onSpinnerItemClicked(int position) {
                setBgSelection(colors.get(position));
            }
        });
    }

    private void setTextSpinner() {
        View textArea = findViewById(R.id.text_color_area);
        Spinner textSpinner = findViewById(R.id.text_color_spinner);

        List<String> strings = Color.convertToStringList(colors);
        strings.add(0, getString(R.string.text_sdk_color_picker_title));

        setSpinner(strings, textArea, textSpinner, textColor, new ISpinnerListener() {
            @Override
            public void onSpinnerItemClicked(int position) {
                setTextSelection(colors.get(position));
            }
        });
    }

    private void setButtonSpinner() {
        View buttonArea = findViewById(R.id.button_color_area);
        Spinner buttonSpinner = findViewById(R.id.button_color_spinner);

        List<String> strings = Color.convertToStringList(colors);
        strings.add(0, getString(R.string.button_sdk_color_picker_title));

        setSpinner(strings, buttonArea, buttonSpinner, buttonColor, new ISpinnerListener() {
            @Override
            public void onSpinnerItemClicked(int position) {
                setButtonSelection(colors.get(position));
            }
        });
    }

    private void setTextSelection(Color color) {
        textColorSelected = color;
        textColorShower.setBackgroundColor(textColorSelected.getColorRes());
        textColor.setText(textColorSelected.getColorName());
    }

    private void setBgSelection(Color color) {
        bgColorSelected = color;
        bgColorShower.setBackgroundColor(bgColorSelected.getColorRes());
        bgColor.setText(bgColorSelected.getColorName());
    }

    private void setButtonSelection(Color color) {
        buttonColorSelected = color;
        buttonColorShower.setBackgroundColor(buttonColorSelected.getColorRes());
        buttonColor.setText(buttonColorSelected.getColorName());
    }

    private void setButtonTextColorSelection(Color color) {
        buttonTextColorSelected = color;
        buttonTextColorShower.setBackgroundColor(buttonTextColorSelected.getColorRes());
        buttonTextColor.setText(buttonTextColorSelected.getColorName());
    }

    private void findViews() {
        button = findViewById(R.id.button);

        bgColor = findViewById(R.id.bg_color);
        textColor = findViewById(R.id.text_color);
        buttonColor = findViewById(R.id.button_color);
        buttonTextColor = findViewById(R.id.button_text_color);

        bgColorShower = findViewById(R.id.bg_color_shower);
        textColorShower = findViewById(R.id.text_color_shower);
        buttonColorShower = findViewById(R.id.button_color_shower);
        buttonTextColorShower = findViewById(R.id.button_text_color_shower);
    }

    private void selectRandomColors() {
        setBgSelection(colors.get(getRandomTill(colors.size())));
        setTextSelection(colors.get(getRandomTill(colors.size())));
        setButtonSelection(colors.get(getRandomTill(colors.size())));
        setButtonTextColorSelection(colors.get(getRandomTill(colors.size())));
    }

    private void selectLifeMilesColors() {
        setBgSelection(new Color("Blanco", getResources().getColor(R.color.sdk_white)));
        setTextSelection(new Color("LM Negro", getResources().getColor(R.color.lm_black)));
        setButtonSelection(new Color("LM Color Principal", getResources().getColor(R.color.lm_main_color)));
        setButtonTextColorSelection(new Color("Blanco", getResources().getColor(R.color.sdk_white)));
    }

    private int getRandomTill(int max) {
        return new Random().nextInt(max);
    }
}
