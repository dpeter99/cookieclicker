package com.dpeter99.cookieclicker.util;

/**
 * Interface for the observer pattern
 *
 * Used for object that want to be notified if an other object changes
 */
public interface Observer {
    void onObservableChanged();
}
