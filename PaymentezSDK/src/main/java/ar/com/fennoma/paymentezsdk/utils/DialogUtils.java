package ar.com.fennoma.paymentezsdk.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class DialogUtils {

    public interface IDialogListener {
        void onAccept();
        void onCancel();
    }

    public static void showDialog(Activity activity, String title, CharSequence message, final IDialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(listener != null) {
                    listener.onAccept();
                }
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(listener != null) {
                    listener.onCancel();
                }
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
