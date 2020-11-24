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
import ar.com.fennoma.paymentezsdk.models.PmzError;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.models.PmzPaymentData;
import ar.com.fennoma.paymentezsdk.models.PmzStore;
import ar.com.fennoma.paymentezsdk.styles.PmzFont;
import ar.com.fennoma.paymentezsdk.styles.PmzStyle;
import ar.com.fennoma.paymentezsdkholder.R;
import ar.com.fennoma.paymentezsdkholder.models.Color;

public class MainActivity extends BaseActivity {

    private List<Color> colors;

    private View mainButton;
    private View withStoreIdButton;
    private View showSummaryButton;
    private View getStoresButton;
    private View multiPaymentButton;

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
        setButtons();
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

    private void setButtons() {
        final PmzBuyer buyer = new PmzBuyer().setName("Pepe").setPhone("123123123").setFiscalNumber("fiscalNumber")
                .setUserReference("userReference").setEmail("pepe@test.com.ar");

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentezSDK.getInstance()
                        .setStyle(getStyles())
                        .startSearch(MainActivity.this, buyer, "appReference", new PaymentezSDK.PmzSearchListener() {
                            @Override
                            public void onFinishedSuccessfully(PmzOrder order) {
                                Toast.makeText(MainActivity.this, getString(R.string.home_flow_success), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(PmzError error) {
                                if(error.getType().equals(PmzError.SESSION_EXPIRED)) {
                                    Toast.makeText(MainActivity.this, R.string.main_payment_session_expired, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MainActivity.this, R.string.generic_error, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(MainActivity.this, getString(R.string.home_flow_cancelled), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        withStoreIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentezSDK.getInstance()
                        .setStyle(getStyles())
                        .startSearch(MainActivity.this, buyer, "appReference", 105L, new PaymentezSDK.PmzSearchListener() {
                            @Override
                            public void onFinishedSuccessfully(PmzOrder order) {
                                Toast.makeText(MainActivity.this, getString(R.string.home_flow_success), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(PmzError error) {
                                if(error.getType().equals(PmzError.SESSION_EXPIRED)) {
                                    Toast.makeText(MainActivity.this, R.string.main_payment_session_expired, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MainActivity.this, R.string.generic_error, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(MainActivity.this, getString(R.string.home_flow_cancelled), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        showSummaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentezSDK.getInstance()
                        .setStyle(getStyles())
                        .showSummary(MainActivity.this, "appReference", PmzOrder.hardcoded(), new PaymentezSDK.PmzSearchListener() {
                            @Override
                            public void onFinishedSuccessfully(PmzOrder order) {
                                Toast.makeText(MainActivity.this, getString(R.string.home_flow_success), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(PmzError error) {
                                if(error.getType().equals(PmzError.SESSION_EXPIRED)) {
                                    Toast.makeText(MainActivity.this, R.string.main_payment_session_expired, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MainActivity.this, R.string.generic_error, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(MainActivity.this, getString(R.string.home_flow_cancelled), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        getStoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                PaymentezSDK.getInstance()
                        .getStores(new PaymentezSDK.PmzStoresListener() {
                            @Override
                            public void onFinishedSuccessfully(List<PmzStore> stores) {
                                hideLoading();
                                Toast.makeText(MainActivity.this, R.string.main_get_stores_got_stores, Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(PmzError error) {
                                hideLoading();
                                Toast.makeText(MainActivity.this, R.string.main_get_stores_on_error, Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        multiPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentezSDK.getInstance()
                        .setStyle(getStyles())
                        .startPayAndPlace(MainActivity.this, PmzOrder.hardcoded(), PmzPaymentData.hardcodedList(), new PaymentezSDK.MultiPaymentOrderListener() {
                            @Override
                            public void onFinishedSuccessfully(PmzOrder order) {
                                Toast.makeText(MainActivity.this, R.string.main_multi_payment_success, Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(PmzOrder order, PmzError error) {
                                switch (error.getType()) {
                                    case PmzError.SESSION_EXPIRED:
                                        Toast.makeText(MainActivity.this, R.string.main_payment_session_expired, Toast.LENGTH_LONG).show();
                                        break;
                                    case PmzError.PAYMENT_ERROR:
                                        Toast.makeText(MainActivity.this, R.string.main_payment_checking_payment_error, Toast.LENGTH_LONG).show();
                                        break;
                                    case PmzError.PLACE_ERROR:
                                        Toast.makeText(MainActivity.this, R.string.main_payment_checking_place_error, Toast.LENGTH_LONG).show();
                                        break;
                                    case PmzError.NO_ORDER_SET_ERROR:
                                        Toast.makeText(MainActivity.this, R.string.main_payment_checking_no_order_found, Toast.LENGTH_LONG).show();
                                        break;
                                    default:
                                        Toast.makeText(MainActivity.this, R.string.generic_error, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    private PmzStyle getStyles() {
        return new PmzStyle().setBackgroundColor(bgColorSelected.getColorRes())
                .setButtonBackgroundColor(buttonColorSelected.getColorRes())
                .setTextColor(textColorSelected.getColorRes())
                .setButtonTextColor(buttonTextColorSelected.getColorRes())
                .setFont(PmzFont.ROBOTO);
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
        mainButton = findViewById(R.id.button);
        withStoreIdButton = findViewById(R.id.button_with_store_id);
        showSummaryButton = findViewById(R.id.show_summary_button);
        getStoresButton = findViewById(R.id.get_stores_button);
        multiPaymentButton = findViewById(R.id.multi_payment_button);

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
