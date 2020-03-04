package com.mercadopago.android.px.internal.datasource.cache;

import androidx.annotation.NonNull;
import com.mercadopago.android.px.internal.core.FileManager;
import java.io.File;

/**
 * {@link Runnable} class for evicting all the cached files
 */
/* default */ final class CacheEvict implements Runnable {
    private final FileManager fileManager;
    private final File cacheDir;

    /* default */ CacheEvict(@NonNull final FileManager fileManager, @NonNull final File cacheDir) {
        this.fileManager = fileManager;
        this.cacheDir = cacheDir;
    }

    @Override
    public void run() {
        fileManager.removeFile(cacheDir);
    }
}