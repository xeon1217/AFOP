package com.example.afop.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.afop.data.dataSource.DataSource

class ForcedTerminationService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.d("termination Service", "onTaskRemoved!")
        try {
            DataSource.exit()
        } catch (e: Exception) { }
    }

    override fun onDestroy() {
        Log.d("termination Service", "onDestroy!")
    }
}