package com.mercadopago.android.px.tracking.internal.mapper;

import androidx.annotation.NonNull;
import com.mercadopago.android.px.internal.viewmodel.mappers.NonNullMapper;
import com.mercadopago.android.px.model.internal.PaymentReward;

public class FromDiscountItemToItemId extends NonNullMapper<PaymentReward.Discount.Item, String> {

    @Override
    public String map(@NonNull final PaymentReward.Discount.Item discountItem) {
        return discountItem.getCampaignId();
    }
}