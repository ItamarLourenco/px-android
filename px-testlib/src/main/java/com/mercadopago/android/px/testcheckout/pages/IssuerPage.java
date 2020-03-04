package com.mercadopago.android.px.testcheckout.pages;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import com.mercadopago.android.px.testcheckout.assertions.CheckoutValidator;
import com.mercadopago.android.testlib.pages.PageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class IssuerPage extends PageObject<CheckoutValidator> {

    public IssuerPage() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    protected IssuerPage(CheckoutValidator validator) {
        super(validator);
    }

    public ReviewAndConfirmPage enterBankOptionToReviewAndConfirm(final int bankOption) {
        selectOption(bankOption);
        return new ReviewAndConfirmPage(validator);
    }

    public InstallmentsPage enterBankOptionToInstallments(final int backOption) {
        selectOption(backOption);
        return new InstallmentsPage(validator);
    }

    public CardAssociationResultSuccessPage enterBankOptionToCardAssociationResult(final int backOption) {
        selectOption(backOption);
        return new CardAssociationResultSuccessPage(validator);
    }

    private void selectOption(final int bankOption) {
        ViewInteraction recyclerView = onView(withId(com.mercadopago.android.px.R.id.mpsdkActivityIssuersView));
        recyclerView.perform(scrollToPosition(bankOption));
        recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition(bankOption, click()));
    }

    public PaymentMethodPage pressBack() {
        onView(isRoot()).perform(ViewActions.pressBack());
        return new PaymentMethodPage(validator);
    }

    @Override
    public IssuerPage validate(CheckoutValidator validator) {
        validator.validate(this);
        return this;
    }
}
