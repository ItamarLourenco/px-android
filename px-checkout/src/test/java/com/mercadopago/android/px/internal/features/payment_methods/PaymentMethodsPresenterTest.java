package com.mercadopago.android.px.internal.features.payment_methods;

import com.mercadopago.android.px.internal.repository.PaymentMethodsRepository;
import com.mercadopago.android.px.internal.repository.UserSelectionRepository;
import com.mercadopago.android.px.model.PaymentMethod;
import com.mercadopago.android.px.model.PaymentTypes;
import com.mercadopago.android.px.model.exceptions.ApiException;
import com.mercadopago.android.px.model.exceptions.MercadoPagoError;
import com.mercadopago.android.px.preferences.PaymentPreference;
import com.mercadopago.android.px.utils.ReflectionArgumentMatchers;
import com.mercadopago.android.px.utils.StubFailMpCall;
import com.mercadopago.android.px.utils.StubSuccessMpCall;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentMethodsPresenterTest {

    @Mock
    private UserSelectionRepository userSelectionRepository;
    @Mock
    private PaymentMethodsRepository paymentMethodsRepository;
    @Mock
    private PaymentMethods.View view;

    private PaymentMethodsPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new PaymentMethodsPresenter(userSelectionRepository,
            paymentMethodsRepository);

        presenter.attachView(view);
    }

    @Test
    public void whenPaymentMethodsPresenterStartsThenShowPaymentMethods() {
        final List<PaymentMethod> paymentMethodsStub =
            com.mercadopago.android.px.mocks.PaymentMethods.getPaymentMethodListMLA();
        when(paymentMethodsRepository.getPaymentMethods()).thenReturn(new StubSuccessMpCall(paymentMethodsStub));

        verify(view).showProgress();
        verify(view).showPaymentMethods(paymentMethodsStub);
        verify(view).hideProgress();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void whenPaymentMethodsPresenterStartsAndPaymentMethodCallFailsThenShowError() {
        verifyPaymentMethodsFailedApiCall();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void whenPaymentMethodsCallFailsAndRecoverFromFailureThenShowPaymentMethods() {
        final List<PaymentMethod> paymentMethodsStub =
            com.mercadopago.android.px.mocks.PaymentMethods.getPaymentMethodListMLA();

        verifyPaymentMethodsFailedApiCall();

        when(paymentMethodsRepository.getPaymentMethods()).thenReturn(new StubSuccessMpCall(paymentMethodsStub));

        presenter.recoverFromFailure();

        verify(view, atLeastOnce()).showProgress();
        verify(view).showPaymentMethods(paymentMethodsStub);
        verify(view, atLeastOnce()).hideProgress();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void whenPaymentTypeIsExcludedThenDoNotShowIt() {
        final List<PaymentMethod> paymentMethodsStub =
            com.mercadopago.android.px.mocks.PaymentMethods.getPaymentMethodListMLA();
        final List<PaymentMethod> excludedPaymentMethodsStub =
            com.mercadopago.android.px.mocks.PaymentMethods.getPaymentMethodListWithoutCreditCardMLA();
        final PaymentPreference paymentPreference = new PaymentPreference();
        final List<String> paymentTypes = new ArrayList<>();
        paymentTypes.add(PaymentTypes.CREDIT_CARD);
        paymentPreference.setExcludedPaymentTypeIds(paymentTypes);

        when(paymentMethodsRepository.getPaymentMethods()).thenReturn(new StubSuccessMpCall(paymentMethodsStub));

        presenter.setPaymentPreference(paymentPreference);

        verify(view).showProgress();
        verify(view).showPaymentMethods(ReflectionArgumentMatchers.reflectionEquals(excludedPaymentMethodsStub));
        verify(view).hideProgress();
    }

    // --------- Helper methods ----------- //

    private void verifyPaymentMethodsFailedApiCall() {
        final ApiException apiException = mock(ApiException.class);
        when(paymentMethodsRepository.getPaymentMethods()).thenReturn(new StubFailMpCall<>(apiException));

        verify(view, atLeastOnce()).showProgress();
        verify(view).showError(any(MercadoPagoError.class));
        verify(view, atLeastOnce()).hideProgress();
    }
}
