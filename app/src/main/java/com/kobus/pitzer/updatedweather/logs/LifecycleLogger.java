package com.kobus.pitzer.updatedweather.logs;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import timber.log.Timber;

public class LifecycleLogger implements LifecycleObserver {

    private String tag;
    public LifecycleLogger(LifecycleOwner owner) {
        tag = owner.getClass().getName();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        Timber.tag(tag);
        Timber.v("Lifecycle change observed: onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart() {
        Timber.tag(tag);
        Timber.v("Lifecycle change observed: onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume() {
        Timber.tag(tag);
        Timber.v("Lifecycle change observed: onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        Timber.tag(tag);
        Timber.v("Lifecycle change observed: onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop() {
        Timber.tag(tag);
        Timber.v("Lifecycle change observed: onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        Timber.tag(tag);
        Timber.v("Lifecycle change observed: onDestroy");
    }
}
