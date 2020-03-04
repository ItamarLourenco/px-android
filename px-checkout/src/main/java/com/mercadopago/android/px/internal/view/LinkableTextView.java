package com.mercadopago.android.px.internal.view;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import com.mercadopago.android.px.internal.features.TermsAndConditionsActivity;
import com.mercadopago.android.px.model.display_info.LinkablePhrase;
import com.mercadopago.android.px.model.display_info.LinkableText;
import java.util.Collections;
import java.util.Map;

public class LinkableTextView extends androidx.appcompat.widget.AppCompatTextView {

    private LinkableText model;
    private int installmentSelected = -1;

    public LinkableTextView(@NonNull final Context context, @NonNull final AttributeSet attrs) {
        super(context, attrs);
    }

    public void updateModel(@Nullable final LinkableText model) {
        if (model != null) {
            this.model = model;
            render();
        }
    }

    public void updateInstallment(final int installmentSelected) {
        this.installmentSelected = installmentSelected;
    }

    private void render() {
        if (!model.getText().isEmpty()) {
            final Spannable spannableText = new SpannableStringBuilder(model.getText());
            if (model.getLinkablePhrases() != null) {
                for (final LinkablePhrase linkablePhrase : model.getLinkablePhrases()) {
                    final int start = model.getText().indexOf(linkablePhrase.getPhrase());
                    final int end = start + linkablePhrase.getPhrase().length();
                    spannableText.setSpan(
                        new ClickableSpan() {
                            @Override
                            public void onClick(@NonNull final View widget) {
                                onLinkClicked(linkablePhrase);
                            }
                        },
                        start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    spannableText
                        .setSpan(new ForegroundColorSpan(Color.parseColor(linkablePhrase.getTextColor())), start, end,
                            0);
                }
            }

            setTextColor(Color.parseColor(model.getTextColor()));
            setText(spannableText);
            setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    /* default */ void onLinkClicked(@NonNull final LinkablePhrase linkablePhrase) {
        String data = "";
        Map<String, String> links = model.getLinks() != null ? model.getLinks() : Collections.emptyMap();
        if (!links.isEmpty() && installmentSelected != -1) {
            data = links.get(linkablePhrase.getLinkId(installmentSelected));
        } else if (linkablePhrase.getLink() != null || linkablePhrase.getHtml() != null) {
            data = linkablePhrase.getLink() != null ? linkablePhrase.getLink() : linkablePhrase.getHtml();
        }
        TermsAndConditionsActivity.start(getContext(), data);
    }
}