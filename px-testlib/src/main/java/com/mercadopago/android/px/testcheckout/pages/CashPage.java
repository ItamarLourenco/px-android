package com.mercadopago.android.px.testcheckout.pages;

import android.view.View;
import com.mercadopago.android.px.testcheckout.assertions.CheckoutValidator;
import com.mercadopago.android.testlib.pages.PageObject;
import org.hamcrest.Matcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class CashPage extends PageObject<CheckoutValidator> {

    public CashPage() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public CashPage(CheckoutValidator validator) {
        super(validator);
    }

    public ReviewAndConfirmPage selectMethod(final String paymentMethodName) {
        Matcher<View> paymentCell = withText(paymentMethodName);
        onView(paymentCell).perform(click());
        return new ReviewAndConfirmPage(validator);
    }

    @Override
    public CashPage validate(final CheckoutValidator validator) {
        validator.validate(this);
        return this;
    }
}
