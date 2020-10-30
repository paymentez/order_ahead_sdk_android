package ar.com.fennoma.paymentezsdk.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.Toast;

import ar.com.fennoma.paymentezsdk.R;

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

    public static void toast(Context context, String text){
        if(TextUtils.isEmpty(text)) {
            genericError(context);
        } else {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        }
    }

    public static void genericError(Context context) {
        toast(context, context.getString(R.string.generic_error));
    }
}
