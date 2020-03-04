package com.mercadopago.android.px.tracking.internal.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.mercadopago.android.px.model.PaymentMethod;

public final class NameViewTracker extends PaymentMethodDataTracker {

    public static final String PATH = BASE_VIEW_PATH + PAYMENTS_PATH + "/select_method/ticket/name";

    public NameViewTracker(@Nullable PaymentMethod paymentModel) {
        super(paymentModel);
    }

    @NonNull
    @Override
    public String getViewPath() {
        return PATH;
    }
}
