package com.mercadopago.android.px.testcheckout.assertions;

import androidx.annotation.NonNull;
import android.view.View;
import com.mercadopago.android.px.R;
import com.mercadopago.android.px.internal.util.TextUtil;
import com.mercadopago.android.px.model.Campaign;
import com.mercadopago.android.px.model.Discount;
import com.mercadopago.android.px.testcheckout.pages.DiscountDetailPage;
import org.hamcrest.Matcher;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class OneShotDiscountValidator extends DiscountValidator {

    public OneShotDiscountValidator(@NonNull final Campaign campaign, @NonNull final Discount discount) {
        super(campaign, discount);
    }

    @Override
    public void validate(@NonNull final DiscountDetailPage discountDetailPage) {
        super.validate(discountDetailPage);
        final Matcher<View> detail = withId(com.mercadopago.android.px.R.id.detail);
        onView(detail).check(matches(withText(com.mercadopago.android.px.R.string.px_one_shot_discount_detail)));
        final Matcher<View> subtitle = withId(com.mercadopago.android.px.R.id.subtitle);
        final String maxCouponAmount = "$ " + campaign.getMaxCouponAmount();
        final String maxCouponAmountSubtitle =
            TextUtil.format(getInstrumentation().getTargetContext(), R.string.px_max_coupon_amount, maxCouponAmount);
        onView(subtitle).check(matches(withText(maxCouponAmountSubtitle)));
    }
}