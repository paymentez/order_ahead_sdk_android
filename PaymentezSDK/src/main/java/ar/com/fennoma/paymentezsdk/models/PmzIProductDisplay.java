package ar.com.fennoma.paymentezsdk.models;

public interface PmzIProductDisplay {

    int TITLE = 0;
    int ITEM = 1;

    int getType();
    PmzConfiguration getConfiguration();

}
