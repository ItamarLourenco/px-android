package com.mercadopago.android.px.internal.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.mercadopago.android.px.R;
import com.mercadopago.android.px.internal.di.Session;

public abstract class PXActivity<P extends BasePresenter> extends AppCompatActivity implements MvpView {

    protected static final String BUNDLE_CREATED = "bundle_created";

    protected P presenter;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState, @Nullable final PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if(!Session.getInstance().isInitialized()){
            finish();
        }
    }

    @CallSuper
    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BUNDLE_CREATED, true);
    }

    @Override
    public void onBackPressed() {
        if (presenter != null) {
            presenter.tracker.trackBack();
        }
        super.onBackPressed();
    }

    public void overrideTransitionIn() {
        overridePendingTransition(R.anim.px_slide_right_to_left_in, R.anim.px_slide_right_to_left_out);
    }

    public void overrideTransitionOut() {
        overridePendingTransition(R.anim.px_slide_left_to_right_in, R.anim.px_slide_left_to_right_out);
    }

    public void overrideTransitionFadeInFadeOut() {
        overridePendingTransition(R.anim.px_fade_in_seamless, R.anim.px_fade_out_seamless);
    }

    public void overrideTransitionWithNoAnimation() {
        overridePendingTransition(R.anim.px_no_change_animation, R.anim.px_no_change_animation);
    }
}
