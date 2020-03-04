package com.mercadopago.android.px.testcheckout.pages;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.test.platform.app.InstrumentationRegistry;
import com.mercadopago.android.px.testcheckout.assertions.CheckoutValidator;
import com.mercadopago.android.testlib.pages.PageObject;
import org.hamcrest.Matchers;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.mercadopago.android.testlib.matchers.UtilityMatchers.withBackgroundColor;

public class CongratsPage extends PageObject<CheckoutValidator> {

    public CongratsPage() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public CongratsPage(final CheckoutValidator validator) {
        super(validator);
    }

    @Override
    public CongratsPage validate(final CheckoutValidator validator) {
        validator.validate(this);
        return this;
    }

    public void clickButtonWithText(@NonNull final String text) {
        onView(withText(Matchers.containsString(text))).perform(click());
    }

    public void clickButtonWithText(@StringRes final int resId) {
        onView(withText(resId)).perform(click());
    }

    public void clickPrimaryButton() {
        onView(withId(com.mercadopago.android.px.R.id.px_button_primary)).perform(click());
    }

    public boolean isSuccess() {
        onView(withId(com.mercadopago.android.px.R.id.header))
            .check(matches(withBackgroundColor(
                InstrumentationRegistry.getTargetContext().getResources()
                    .getColor(com.mercadopago.android.px.R.color.ui_components_success_color))));
        return true;
    }
}
