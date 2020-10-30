package ar.com.fennoma.paymentezsdk.models;

public class PmzTitleItem implements PmzIProductDisplay {

    private String title;

    @Override
    public int getType() {
        return TITLE;
    }

    @Override
    public PmzConfiguration getConfiguration() {
        return null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
