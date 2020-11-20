package ar.com.fennoma.paymentezsdk.models;

public class PmzTitleItem implements PmzIProductDisplay {

    private String title;

    public PmzTitleItem(PmzProductConfiguration configuration) {
        this.title = configuration.getSubtypeName();
    }

    @Override
    public int getType() {
        return TITLE;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
