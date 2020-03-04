package com.mercadopago.android.px.testcheckout.pages;

import androidx.annotation.NonNull;
import androidx.test.espresso.action.ViewActions;
import android.view.View;
import com.mercadopago.android.px.testcheckout.assertions.CheckoutValidator;
import com.mercadopago.android.testlib.pages.PageObject;
import org.hamcrest.Matcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public final class PayerInformationFirstNamePage extends PageObject<CheckoutValidator> {

    public PayerInformationFirstNamePage(final CheckoutValidator validator) {
        super(validator);
    }

    public PayerInformationFirstNamePage enterFirstName(@NonNull final String firstName) {
        onView(withId(com.mercadopago.android.px.R.id.mpsdkName)).perform(typeText(firstName));
        return new PayerInformationFirstNamePage(validator);
    }

    public PayerInformationLastNamePage pressNextButton() {
        final Matcher<View> cardNextButtonTextMatcher = withId(com.mercadopago.android.px.R.id.mpsdkNextButton);
        onView(cardNextButtonTextMatcher).perform(click());
        return new PayerInformationLastNamePage(validator);
    }

    @Override
    public PayerInformationFirstNamePage validate(final CheckoutValidator validator) {
        validator.validate(this);
        return this;
    }

    public PaymentMethodPage pressBack() {
        onView(isRoot()).perform(ViewActions.pressBack());
        return new PaymentMethodPage(validator);
    }
}