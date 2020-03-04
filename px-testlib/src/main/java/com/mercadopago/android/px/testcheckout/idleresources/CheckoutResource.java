package com.mercadopago.android.px.testcheckout.idleresources;

import androidx.test.platform.app.InstrumentationRegistry;
import com.mercadopago.android.px.internal.util.HttpClientUtil;
import com.mercadopago.android.testlib.HttpResource;
import okhttp3.OkHttpClient;

public class CheckoutResource extends HttpResource {
    @Override
    protected OkHttpClient getClient() {
        return HttpClientUtil.getClient(InstrumentationRegistry.getTargetContext(), 10, 10, 10);
    }
}
