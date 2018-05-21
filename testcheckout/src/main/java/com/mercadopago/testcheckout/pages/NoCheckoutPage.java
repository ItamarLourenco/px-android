package com.mercadopago.testcheckout.pages;

import com.mercadopago.testcheckout.assertions.CheckoutValidator;
import com.mercadopago.testlib.pages.PageObject;

public class NoCheckoutPage extends PageObject<CheckoutValidator> {

    public NoCheckoutPage() {
    }

    public NoCheckoutPage(final CheckoutValidator validator) {
        super(validator);
    }

    @Override
    public NoCheckoutPage validate(CheckoutValidator validator) {
        validator.validate(this);
        return this;
    }
}