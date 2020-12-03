package com.example.afop.data.dataSource

import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent

class WebSocketClient {
    /*
    companion object {
        val compositeDisposable = CompositeDisposable()
        val mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, Network.getStomp())

        private fun connectWebSocket() {
            val lifecycle: Disposable = mStompClient.lifecycle().subscribe {
                when (it.type) {
                    LifecycleEvent.Type.OPENED -> {
                        Log.e("stomp", "Stomp Connection Opened");
                    }
                    LifecycleEvent.Type.ERROR -> {
                        Log.e("stomp", "Error ", it.exception);
                    }
                    LifecycleEvent.Type.CLOSED -> {
                        Log.e("stomp", "Stomp Connection Closed");
                    }
                    LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> {
                        Log.e("stomp", "Failed Server Heartbeat ");
                    }
                }
            }

            if (!mStompClient.isConnected) {
                mStompClient.connect()
            }

            val topic: Disposable = mStompClient.topic("/sub/chat/room/4359").subscribe {
                Log.e("stomp", it.payload)
            }

            mStompClient.send("/sub/chat/room/4359", "send message!").subscribe()
            compositeDisposable.add(lifecycle)
            compositeDisposable.add(topic)
        }

        fun connectSocket() {
            if (mStompClient != null) {
                if (!mStompClient.isConnected) {
                    connectWebSocket()
                }
            }
        }

        fun disConnectSocket() {
            if (mStompClient != null) {
                if (mStompClient.isConnected) {
                    mStompClient.disconnect();
//                compositeDisposable.dispose();
                }
            }
        }
    }
     */
}