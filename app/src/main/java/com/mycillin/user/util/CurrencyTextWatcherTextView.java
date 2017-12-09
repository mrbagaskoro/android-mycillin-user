package com.mycillin.user.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by mrbagaskoro on 09-Dec-17.
 */

public class CurrencyTextWatcherTextView implements TextWatcher {
    private final TextView currency;
    private String current = "";
    private int index;
    private boolean deletingDecimalPoint;

    public CurrencyTextWatcherTextView(TextView p_currency) {
        currency = p_currency;
    }

    @Override
    public void beforeTextChanged(CharSequence p_s, int p_start, int p_count, int p_after) {
        if (p_after > 0) {
            index = p_s.length() - p_start;
        } else {
            index = p_s.length() - p_start - 1;
        }
        deletingDecimalPoint = p_count > 0 && p_s.charAt(p_start) == ',';
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable p_s) {
        if (!p_s.toString().equals(current)) {
            currency.removeTextChangedListener(this);
            if (deletingDecimalPoint) {
                p_s.delete(p_s.length() - index - 1, p_s.length() - index);
            }

            String v_text = p_s.toString().replace("Rp ", "").replace(".", "");
            v_text = v_text.replaceAll("\\s", "");

            if(isNumber(v_text)) {
                double v_value = 0;
                if (v_text != null && v_text.length() > 0) {
                    v_value = Double.parseDouble(v_text);
                }

                NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                String v_formattedValue = numberFormat.format(v_value).replace("Rp", "");
                current = v_formattedValue;
                currency.setText(v_formattedValue);

                currency.addTextChangedListener(this);
            }
        }
    }

    private boolean isNumber(String s) {
        if(s.matches("\\d+(?:\\.\\d+)?")) {
            return true;
        }
        else {
            return false;
        }
    }
}
