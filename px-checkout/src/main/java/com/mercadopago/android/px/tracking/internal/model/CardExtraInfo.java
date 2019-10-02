package com.mercadopago.android.px.tracking.internal.model;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.android.px.model.Card;
import com.mercadopago.android.px.model.CustomSearchItem;
import com.mercadopago.android.px.model.Issuer;
import com.mercadopago.android.px.model.PayerCost;
import java.lang.reflect.Type;
import java.util.HashMap;

@SuppressWarnings("unused")
@Keep
public class CardExtraInfo extends TrackingMapModel {

    @Nullable private final String cardId;
    @Nullable private final PayerCostInfo selectedInstallment;
    private final Long issuerId;
    private final boolean hasEsc;

    protected CardExtraInfo(@Nullable final String cardId, final boolean hasEsc,
        @Nullable final Long issuerId,
        @Nullable final PayerCostInfo payerCostTrackModel) {
        this.cardId = cardId;
        this.hasEsc = hasEsc;
        selectedInstallment = payerCostTrackModel;
        this.issuerId = issuerId;
    }

    @NonNull
    public static CardExtraInfo savedCard(@NonNull final Card card, @NonNull final PayerCost payerCost,
        final boolean hasEsc) {
        return new CardExtraInfo(card.getId(), hasEsc,
            card.getIssuer().getId(),
            new PayerCostInfo(payerCost));
    }

    @NonNull
    public static CardExtraInfo nonSavedCardInfo(@Nullable final Issuer issuer,
        @Nullable final PayerCost payerCost) {
        return new CardExtraInfo(null, false, issuer != null ? issuer.getId() : null,
            payerCost == null ? null : new PayerCostInfo(payerCost));
    }

    @NonNull
    public static CardExtraInfo customOptions(final CustomSearchItem val, final boolean hasEsc) {
        return new CardExtraInfo(val.getId(), hasEsc, null, null);
    }
}
