package com.mercadopago.android.px.internal.util.textformatter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import com.mercadopago.android.px.R;
import com.mercadopago.android.px.internal.font.PxFont;
import com.mercadopago.android.px.internal.util.TextUtil;
import com.mercadopago.android.px.internal.util.ViewUtils;
import com.mercadopago.android.px.model.PayerCost;
import java.util.regex.Pattern;

public class CFTFormatter extends ChainFormatter {
    private static final Pattern REGEX_NON_ZERO_PATTERN = Pattern.compile(".*[1-9]+.*$");
    private final PayerCost payerCost;
    private int textColor;
    private final Context context;
    private final SpannableStringBuilder spannableStringBuilder;

    public CFTFormatter(@NonNull final SpannableStringBuilder spannableStringBuilder,
        @NonNull final Context context, @NonNull final PayerCost payerCost) {
        this.spannableStringBuilder = spannableStringBuilder;
        this.context = context;
        this.payerCost = payerCost;
    }

    public CFTFormatter withTextColor(final int color) {
        textColor = color;
        return this;
    }

    public Spannable build() {
        return apply(payerCost.getCFTPercent());
    }

    @Override
    public Spannable apply(@NonNull final CharSequence amount) {
        if (TextUtil.isEmpty(amount) || !REGEX_NON_ZERO_PATTERN.matcher(amount).matches()) {
            return spannableStringBuilder;
        }
        final int initialIndex = spannableStringBuilder.length();
        final String cftDescription = TextUtil.format(context, R.string.px_installments_cft, amount);
        final String separator = " ";
        spannableStringBuilder.append(separator).append(cftDescription);
        final int textLength = separator.length() + cftDescription.length();
        ViewUtils.setColorInSpannable(textColor, initialIndex, initialIndex + textLength, spannableStringBuilder);
        ViewUtils.setFontInSpannable(context, PxFont.REGULAR, spannableStringBuilder, initialIndex,
            initialIndex + textLength);

        return spannableStringBuilder;
    }
}