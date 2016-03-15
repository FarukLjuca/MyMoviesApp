package com.atlantbh.mymoviesapp.offline;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class OfflineSyncService extends IntentService {
    public OfflineSyncService() { super("OfflineSync"); }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Intent localIntent =
                new Intent("OfflineSync").putExtra("status", "ok");
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}
