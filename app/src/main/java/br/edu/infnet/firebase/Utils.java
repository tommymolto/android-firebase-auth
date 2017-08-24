package br.edu.infnet.firebase;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.widget.Toast;

public class Utils {
    public static boolean temTexto(TextInputLayout inputLayout) {
        return !inputLayout.getEditText().getText().toString().trim().equals("");
    }

    public static String pegaTexto(TextInputLayout inputLayout) {
        return inputLayout.getEditText().getText().toString().trim();
    }

    public static void mostraToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
