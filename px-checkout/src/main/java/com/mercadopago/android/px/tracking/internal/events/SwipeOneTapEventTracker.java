package com.mercadopago.android.px.tracking.internal.events;

import androidx.annotation.NonNull;

public class SwipeOneTapEventTracker extends EventTracker {
    @NonNull
    @Override
    public String getEventPath() {
        return BASE_PATH + "/review/one_tap/swipe";
    }
}
