package com.dpeter99.cookieclicker.util;


import java.util.HashSet;
import java.util.Set;

public class Observable {

    // this is the object we will be synchronizing on ("the monitor")
    private final Object MONITOR = new Object();

    private Set<Observer> mObservers;

    /**
     * This method adds a new Observer - it will be notified when Observable changes
     *
     * @param observer the observer to be added to the notification list
     */
    public void registerObserver(Observer observer) {
        if (observer == null) return;

        synchronized(MONITOR) {
            if (mObservers == null) {
                mObservers = new HashSet<>(1);
            }
            if (mObservers.add(observer) && mObservers.size() == 1) {
                //performInit(); // some initialization when first observer added
            }
        }
    }

    /**
     * This method removes an Observer - it will no longer be notified when Observable changes
     *
     * @param observer the observer to be removed to the notification list
     */
    public void unregisterObserver(Observer observer) {
        if (observer == null) return;

        synchronized(MONITOR) {
            if (mObservers != null && mObservers.remove(observer) && mObservers.isEmpty()) {
                //performCleanup(); // some cleanup when last observer removed
            }
        }
    }

    /**
     * This method notifies currently registered observers about Observable's change
     */
    protected void notifyObservers() {
        Set<Observer> observersCopy;

        synchronized(MONITOR) {
            if (mObservers == null) return;
            observersCopy = new HashSet<>(mObservers);
        }

        for (Observer observer : observersCopy) {
            observer.onObservableChanged();
        }
    }
}