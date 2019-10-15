package com.mercadopago.android.px.internal.datasource;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.android.px.configuration.AdvancedConfiguration;
import com.mercadopago.android.px.configuration.DiscountParamsConfiguration;
import com.mercadopago.android.px.configuration.PaymentConfiguration;
import com.mercadopago.android.px.internal.repository.PaymentSettingRepository;
import com.mercadopago.android.px.internal.util.JsonUtil;
import com.mercadopago.android.px.internal.util.TextUtil;
import com.mercadopago.android.px.model.Token;
import com.mercadopago.android.px.model.commission.PaymentTypeChargeRule;
import com.mercadopago.android.px.preferences.CheckoutPreference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class PaymentSettingService implements PaymentSettingRepository {

    private static final String PREF_CHECKOUT_PREF = "PREF_CHECKOUT_PREFERENCE";
    private static final String PREF_CHECKOUT_PREF_ID = "PREF_CHECKOUT_PREFERENCE_ID";
    private static final String PREF_PUBLIC_KEY = "PREF_PUBLIC_KEY";
    private static final String PREF_PRIVATE_KEY = "PREF_PRIVATE_KEY";
    private static final String PREF_TOKEN = "PREF_TOKEN";
    private static final String PREF_PRODUCT_ID = "PREF_PRODUCT_ID";
    private static final String PREF_LABELS = "PREF_LABELS";
    private static final String PREF_AMOUNT_ROW_ENABLED = "PREF_AMOUNT_ROW_ENABLED";
    private static final String PREF_CHARGE_RULES = "PREF_CHARGE_RULES";

    @NonNull private final SharedPreferences sharedPreferences;

    //mem cache
    private CheckoutPreference pref;
    private PaymentConfiguration paymentConfiguration;
    private AdvancedConfiguration advancedConfiguration;

    public PaymentSettingService(@NonNull final SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void reset() {
        final SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear().apply();
        pref = null;
        paymentConfiguration = null;
        advancedConfiguration = null;
    }

    @Override
    public void configure(@NonNull final Token token) {
        final SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(PREF_TOKEN, JsonUtil.toJson(token));
        edit.apply();
    }

    @Override
    public void clearToken() {
        sharedPreferences.edit().remove(PREF_TOKEN).apply();
    }

    @Override
    public void configurePreferenceId(@Nullable final String preferenceId) {
        final SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(PREF_CHECKOUT_PREF_ID, preferenceId).apply();
    }

    @Override
    public void configure(@NonNull final AdvancedConfiguration advancedConfiguration) {
        final SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(PREF_PRODUCT_ID, advancedConfiguration.getDiscountParamsConfiguration().getProductId());
        edit.putStringSet(PREF_LABELS, advancedConfiguration.getDiscountParamsConfiguration().getLabels());
        edit.putBoolean(PREF_AMOUNT_ROW_ENABLED, advancedConfiguration.isAmountRowEnabled()).apply();

        this.advancedConfiguration = advancedConfiguration;
    }

    @Override
    public void configure(@NonNull final String publicKey) {
        final SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(PREF_PUBLIC_KEY, publicKey);
        edit.apply();
    }

    @Override
    public void configurePrivateKey(@Nullable final String privateKey) {
        final SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(PREF_PRIVATE_KEY, privateKey);
        edit.apply();
    }

    @Override
    public void configure(@Nullable final PaymentConfiguration paymentConfiguration) {
        //TODO we have to save payment processor for save the hole payment configuration in shared preference
        if (paymentConfiguration != null) {
            sharedPreferences.edit().putString(PREF_CHARGE_RULES, JsonUtil.toJson(paymentConfiguration.getCharges()))
                .apply();
        }
        this.paymentConfiguration = paymentConfiguration;
    }

    @Override
    public void configure(@Nullable final CheckoutPreference checkoutPreference) {
        final SharedPreferences.Editor edit = sharedPreferences.edit();
        if (checkoutPreference == null) {
            edit.remove(PREF_CHECKOUT_PREF).apply();
        } else {
            edit.putString(PREF_CHECKOUT_PREF, JsonUtil.toJson(checkoutPreference));
            edit.apply();
        }
        pref = checkoutPreference;
    }

    @NonNull
    @Override
    public List<PaymentTypeChargeRule> getChargeRules() {
        if (paymentConfiguration == null) {
            return JsonUtil.fromJson(sharedPreferences.getString(PREF_CHARGE_RULES, TextUtil.EMPTY),
                new TypeToken<ArrayList<PaymentTypeChargeRule>>() {
                }.getType());
        } else {
            return getPaymentConfiguration().getCharges();
        }
    }

    @NonNull
    @Override
    public PaymentConfiguration getPaymentConfiguration() {
        return paymentConfiguration;
    }

    @Nullable
    @Override
    public CheckoutPreference getCheckoutPreference() {
        if (pref == null) {
            pref = JsonUtil.fromJson(sharedPreferences.getString(PREF_CHECKOUT_PREF, ""), CheckoutPreference.class);
        }
        return pref;
    }

    @Nullable
    @Override
    public String getCheckoutPreferenceId() {
        return sharedPreferences.getString(PREF_CHECKOUT_PREF_ID, null);
    }

    @NonNull
    @Override
    public String getPublicKey() {
        return sharedPreferences.getString(PREF_PUBLIC_KEY, "");
    }

    @Nullable
    @Override
    public Token getToken() {
        return JsonUtil.fromJson(sharedPreferences.getString(PREF_TOKEN, ""), Token.class);
    }

    @Override
    public boolean hasToken() {
        return getToken() != null;
    }

    @NonNull
    @Override
    public String getTransactionId() {
        return String.format(Locale.getDefault(), "%s%d", getPublicKey(), Calendar.getInstance().getTimeInMillis());
    }

    @NonNull
    @Override
    public AdvancedConfiguration getAdvancedConfiguration() {
        if (advancedConfiguration == null) {
            return new AdvancedConfiguration.Builder()
                .setAmountRowEnabled(sharedPreferences.getBoolean(PREF_AMOUNT_ROW_ENABLED, true))
                .setDiscountParamsConfiguration(new DiscountParamsConfiguration.Builder()
                    .setProductId(sharedPreferences.getString(PREF_PRODUCT_ID, ""))
                    .setLabels(sharedPreferences.getStringSet(PREF_LABELS, Collections.<String>emptySet())).build())
                .build();
        }
        return advancedConfiguration;
    }

    @Nullable
    @Override
    public String getPrivateKey() {
        return sharedPreferences.getString(PREF_PRIVATE_KEY, null);
    }
}
