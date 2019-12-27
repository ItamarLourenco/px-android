package com.mercadopago.android.px.internal.viewmodel.mappers;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.internal.repository.AmountConfigurationRepository;
import com.mercadopago.android.px.internal.repository.DisabledPaymentMethodRepository;
import com.mercadopago.android.px.internal.view.PaymentMethodDescriptorView;
import com.mercadopago.android.px.internal.viewmodel.AccountMoneyDescriptorModel;
import com.mercadopago.android.px.internal.viewmodel.CreditCardDescriptorModel;
import com.mercadopago.android.px.internal.viewmodel.DebitCardDescriptorModel;
import com.mercadopago.android.px.internal.viewmodel.DisabledPaymentMethodDescriptorModel;
import com.mercadopago.android.px.internal.viewmodel.EmptyInstallmentsDescriptorModel;
import com.mercadopago.android.px.model.Currency;
import com.mercadopago.android.px.model.ExpressMetadata;
import com.mercadopago.android.px.model.InterestFree;
import com.mercadopago.android.px.model.PaymentTypes;
import com.mercadopago.android.px.model.internal.Text;

public class PaymentMethodDescriptorMapper extends Mapper<ExpressMetadata, PaymentMethodDescriptorView.Model> {

    @NonNull private final Currency currency;
    @NonNull private final AmountConfigurationRepository amountConfigurationRepository;
    @NonNull private final DisabledPaymentMethodRepository disabledPaymentMethodRepository;

    public PaymentMethodDescriptorMapper(@NonNull final Currency currency,
        @NonNull final AmountConfigurationRepository amountConfigurationRepository,
        @NonNull final DisabledPaymentMethodRepository disabledPaymentMethodRepository) {
        this.currency = currency;
        this.amountConfigurationRepository = amountConfigurationRepository;
        this.disabledPaymentMethodRepository = disabledPaymentMethodRepository;
    }

    @Override
    public PaymentMethodDescriptorView.Model map(@NonNull final ExpressMetadata expressMetadata) {
        final String paymentTypeId = expressMetadata.getPaymentTypeId();
        final String customOptionId = expressMetadata.getCustomOptionId();

        if (disabledPaymentMethodRepository.hasPaymentMethodId(customOptionId)) {
            return DisabledPaymentMethodDescriptorModel.createFrom(expressMetadata.getStatus().getMainMessage());
        } else if (PaymentTypes.isCreditCardPaymentType(paymentTypeId) || expressMetadata.isConsumerCredits()) {
            return mapCredit(expressMetadata);
        } else if (PaymentTypes.isCardPaymentType(paymentTypeId)) {
            return DebitCardDescriptorModel
                .createFrom(currency, amountConfigurationRepository.getConfigurationFor(customOptionId));
        } else if (PaymentTypes.isAccountMoney(expressMetadata.getPaymentMethodId())) {
            return AccountMoneyDescriptorModel.createFrom(expressMetadata.getAccountMoney());
        } else {
            return EmptyInstallmentsDescriptorModel.create();
        }
    }

    private PaymentMethodDescriptorView.Model mapCredit(@NonNull final ExpressMetadata expressMetadata) {
        //This model is useful for Credit Card and Consumer Credits
        // FIXME change model to represent more than just credit cards.
        final Text installmentsRightHeader =
            expressMetadata.hasBenefits() ? expressMetadata.getBenefits().getInstallmentsHeader() : null;
        final InterestFree interestFree =
            expressMetadata.hasBenefits() ? expressMetadata.getBenefits().getInterestFree() : null;
        return CreditCardDescriptorModel
            .createFrom(currency, installmentsRightHeader, interestFree,
                amountConfigurationRepository.getConfigurationFor(expressMetadata.getCustomOptionId()));
    }
}