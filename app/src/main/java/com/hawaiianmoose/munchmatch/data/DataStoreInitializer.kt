package com.hawaiianmoose.munchmatch.data

import android.content.Context

object DataStoreInitializer {
    fun initDataStore(context: Context) {
        DataStoreProvider.createDataStore(
            producePath = { context.filesDir.resolve(DataStoreProvider.DATA_STORE_FILE_NAME).absolutePath }
        )
    }
}
