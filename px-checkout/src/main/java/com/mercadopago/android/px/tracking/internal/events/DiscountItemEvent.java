package com.mercadopago.android.px.tracking.internal.events;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.mercadopago.android.px.tracking.internal.model.DiscountItemData;
import com.mercadopago.android.px.tracking.internal.views.ResultViewTrack;
import java.util.Map;

public final class DiscountItemEvent extends EventTracker {

    private final String eventPath;
    private final DiscountItemData discountItemData;

    public DiscountItemEvent(@NonNull final ResultViewTrack resultViewTrack, final int index,
        @Nullable final String trackId) {
        eventPath = resultViewTrack.getViewPath() + "/tap_discount_item";
        discountItemData = new DiscountItemData(index, trackId);
    }

    @NonNull
    @Override
    public Map<String, Object> getEventData() {
        return discountItemData.toMap();
    }

    @NonNull
    @Override
    public String getEventPath() {
        return eventPath;
    }
}