package com.mercadopago.android.px.internal.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import com.mercadopago.android.px.internal.callbacks.PaymentServiceHandler;
import com.mercadopago.android.px.model.ExpressMetadata;
import com.mercadopago.android.px.model.IPaymentDescriptor;
import com.mercadopago.android.px.model.PayerCost;
import com.mercadopago.android.px.model.PaymentData;
import com.mercadopago.android.px.model.PaymentRecovery;
import com.mercadopago.android.px.model.PaymentResult;
import java.util.List;

public interface PaymentRepository {

    void startPayment();

    void startExpressPayment(@NonNull final ExpressMetadata expressMetadata, @Nullable final PayerCost payerCost,
        final boolean splitPayment);

    void startExpressPaymentWithOffMethod(@NonNull final String paymentMethodId, @NonNull final String paymentTypeId);

    boolean isExplodingAnimationCompatible();

    @NonNull
    @Size(min = 1)
    List<PaymentData> getPaymentDataList();

    @NonNull
    PaymentResult createPaymentResult(@NonNull final IPaymentDescriptor genericPayment);

    int getPaymentTimeout();

    void attach(@NonNull final PaymentServiceHandler handler);

    void detach(@NonNull final PaymentServiceHandler handler);

    void storePayment(@NonNull final IPaymentDescriptor iPayment);

    @Nullable
    IPaymentDescriptor getPayment();

    boolean hasPayment();

    boolean hasRecoverablePayment();

    @NonNull
    PaymentRecovery createRecoveryForInvalidESC();

    @NonNull
    PaymentRecovery createPaymentRecovery();
}
