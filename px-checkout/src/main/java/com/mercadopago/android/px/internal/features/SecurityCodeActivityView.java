package com.mercadopago.android.px.internal.features;

import com.mercadopago.android.px.internal.base.BaseView;
import com.mercadopago.android.px.model.exceptions.ApiException;
import com.mercadopago.android.px.model.exceptions.CardTokenException;
import com.mercadopago.android.px.model.exceptions.MercadoPagoError;

public interface SecurityCodeActivityView extends BaseView {
    void setSecurityCodeInputMaxLength(int length);

    void showError(MercadoPagoError error, String requestOrigin);

    void setErrorView(CardTokenException exception);

    void clearErrorView();

    void onBackButtonPressed();

    void showLoadingView();

    void stopLoadingView();

    void showApiExceptionError(ApiException exception, String requestOrigin);

    void finishWithResult();

    void initialize();

    void showTimer();

    void showBackSecurityCodeCardView();

    void showFrontSecurityCodeCardView();

    void showStandardErrorMessage();
}
