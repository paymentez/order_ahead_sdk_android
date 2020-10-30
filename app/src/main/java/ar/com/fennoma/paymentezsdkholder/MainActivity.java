package ar.com.fennoma.paymentezsdkholder;

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
import ar.com.fennoma.paymentezsdk.styles.PmzStyle;
import ar.com.fennoma.paymentezsdkholder.activities.BaseActivity;
import ar.com.fennoma.paymentezsdkholder.models.Color;

public class MainActivity extends BaseActivity {

    private List<Color> colors;

    private View searchButton;
    private View searchWithStoreIdButton;
    private View showSummaryButton;
    private View paymentButton;
    private View paymentSkipSummaryButton;
    private View getStoresButton;

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
        selectRandomColors();
    }

    private void setViews() {
        setButtons();
        setRandomizeButton();
        setBgSpinner();
        setTextSpinner();
        setButtonSpinner();
        setButtonTextSpinner();
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
        final PmzBuyer buyer = new PmzBuyer()
                .setEmail("buyer@test.com.ar")
                .setFiscalNumber("123456")
                .setName("Buyer Test")
                .setPhone("01112345678")
                .setUserReference("user_reference");

        final PmzStyle style = new PmzStyle().setBackgroundColor(bgColorSelected.getColorRes())
                .setButtonBackgroundColor(buttonColorSelected.getColorRes())
                .setTextColor(textColorSelected.getColorRes())
                .setButtonTextColor(buttonTextColorSelected.getColorRes());

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentezSDK.getInstance()
                        .setStyle(style)
                        .startSearch(MainActivity.this, buyer, "appOrderReference", new PaymentezSDK.PmzSearchListener() {
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
        searchWithStoreIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentezSDK.getInstance()
                        .setStyle(style)
                        .startSearch(MainActivity.this, buyer, "appOrderReference", 120L, new PaymentezSDK.PmzSearchListener() {
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

        showSummaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentezSDK.getInstance()
                        .setStyle(style)
                        .showSummary(MainActivity.this, "appOrderReference", PmzOrder.hardcoded(), new PaymentezSDK.PmzSearchListener() {
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

        final PmzPaymentData paymentData = new PmzPaymentData()
                .setPaymentMethodReference("paymentMethodReference")
                .setPaymentReference("paymentReference")
                .setAmount(20000L)
                .setService(200L);

        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentezSDK.getInstance()
                        .setStyle(style)
                        .startPayAndPlace(MainActivity.this, PmzOrder.hardcoded(), paymentData, new PaymentezSDK.PmzPayAndPlaceListener() {
                            @Override
                            public void onFinishedSuccessfully(PmzOrder order) {
                                Toast.makeText(MainActivity.this, R.string.main_payment_checking_success, Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(PmzOrder order, PmzError error) {
                                switch (error.getType()) {
                                    case PmzError.NO_ORDER_SET_ERROR:
                                        Toast.makeText(MainActivity.this, R.string.main_payment_checking_no_order_found, Toast.LENGTH_LONG).show();
                                        break;
                                    case PmzError.PAYMENT_ERROR:
                                        Toast.makeText(MainActivity.this, R.string.main_payment_checking_payment_error, Toast.LENGTH_LONG).show();
                                        break;
                                    case PmzError.PLACE_ERROR:
                                        Toast.makeText(MainActivity.this, R.string.main_payment_checking_place_error, Toast.LENGTH_LONG).show();
                                        break;

                                }
                            }
                        });
            }
        });

        paymentSkipSummaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentezSDK.getInstance()
                        .setStyle(style)
                        .startPayAndPlace(MainActivity.this, PmzOrder.hardcoded(), paymentData, true, new PaymentezSDK.PmzPayAndPlaceListener() {
                            @Override
                            public void onFinishedSuccessfully(PmzOrder order) {
                                Toast.makeText(MainActivity.this, R.string.main_payment_checking_success, Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(PmzOrder order, PmzError error) {
                                switch (error.getType()) {
                                    case PmzError.NO_ORDER_SET_ERROR:
                                        Toast.makeText(MainActivity.this, R.string.main_payment_checking_no_order_found, Toast.LENGTH_LONG).show();
                                        break;
                                    case PmzError.PAYMENT_ERROR:
                                        Toast.makeText(MainActivity.this, R.string.main_payment_checking_payment_error, Toast.LENGTH_LONG).show();
                                        break;
                                    case PmzError.PLACE_ERROR:
                                        Toast.makeText(MainActivity.this, R.string.main_payment_checking_place_error, Toast.LENGTH_LONG).show();
                                        break;

                                }
                            }
                        });
            }
        });

        getStoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentezSDK.getInstance().getStores(new PaymentezSDK.PmzStoresListener() {
                    @Override
                    public void onFinishedSuccessfully(List<PmzStore> stores) {
                        Toast.makeText(MainActivity.this, R.string.main_get_stores_got_stores, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(PmzError error) {
                        Toast.makeText(MainActivity.this, R.string.main_get_stores_on_error, Toast.LENGTH_LONG).show();
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
                setBgSelection(position);
            }
        });
    }

    private void setBgSelection(int position) {
        bgColorSelected = colors.get(position);
        bgColorShower.setBackgroundColor(bgColorSelected.getColorRes());
        bgColor.setText(bgColorSelected.getColorName());
    }

    private void setTextSpinner() {
        View textArea = findViewById(R.id.text_color_area);
        Spinner textSpinner = findViewById(R.id.text_color_spinner);

        List<String> strings = Color.convertToStringList(colors);
        strings.add(0, getString(R.string.text_sdk_color_picker_title));

        setSpinner(strings, textArea, textSpinner, textColor, new ISpinnerListener() {
            @Override
            public void onSpinnerItemClicked(int position) {
                setTextSelection(position);
            }
        });
    }

    private void setTextSelection(int position) {
        textColorSelected = colors.get(position);
        textColorShower.setBackgroundColor(textColorSelected.getColorRes());
        textColor.setText(textColorSelected.getColorName());
    }

    private void setButtonSpinner() {
        View buttonArea = findViewById(R.id.button_color_area);
        Spinner buttonSpinner = findViewById(R.id.button_color_spinner);

        List<String> strings = Color.convertToStringList(colors);
        strings.add(0, getString(R.string.button_sdk_color_picker_title));

        setSpinner(strings, buttonArea, buttonSpinner, buttonColor, new ISpinnerListener() {
            @Override
            public void onSpinnerItemClicked(int position) {
                setButtonSelection(position);
            }
        });
    }

    private void setButtonSelection(int position) {
        buttonColorSelected = colors.get(position);
        buttonColorShower.setBackgroundColor(buttonColorSelected.getColorRes());
        buttonColor.setText(buttonColorSelected.getColorName());
    }

    private void setButtonTextSpinner() {
        View buttonTextArea = findViewById(R.id.button_text_color_area);
        Spinner buttonTextSpinner = findViewById(R.id.button_text_color_spinner);

        List<String> strings = Color.convertToStringList(colors);
        strings.add(0, getString(R.string.button_text_sdk_color_picker_title));

        setSpinner(strings, buttonTextArea, buttonTextSpinner, buttonTextColor, new ISpinnerListener() {
            @Override
            public void onSpinnerItemClicked(int position) {
                setButtonTextSelection(position);
            }
        });
    }

    private void setButtonTextSelection(int position) {
        buttonTextColorSelected = colors.get(position);
        buttonTextColorShower.setBackgroundColor(buttonTextColorSelected.getColorRes());
        buttonTextColor.setText(buttonTextColorSelected.getColorName());
    }

    private void findViews() {
        searchButton = findViewById(R.id.button);
        searchWithStoreIdButton = findViewById(R.id.button_with_store_id);
        paymentButton = findViewById(R.id.payment_button);
        paymentSkipSummaryButton = findViewById(R.id.payment_skip_summary_button);
        getStoresButton = findViewById(R.id.get_stores_button);
        showSummaryButton = findViewById(R.id.show_summary_button);

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
        setBgSelection(getRandomTill(colors.size()));
        setTextSelection(getRandomTill(colors.size()));
        setButtonSelection(getRandomTill(colors.size()));
        setButtonTextSelection(getRandomTill(colors.size()));
    }

    private int getRandomTill(int max) {
        return new Random().nextInt(max);
    }
}