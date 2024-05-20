package com.ferchau.simulator;

public class ControlSystem {
    private boolean emptyPlaceSensor = false;
    private boolean fullPlaceSensor = false;
    private PlaceSensorInterface placeSensorInterface;

    public void setPlaceSensorInterface(PlaceSensorInterface placeSensorInterface) {
        this.placeSensorInterface = placeSensorInterface;
    }

    public synchronized boolean isEmptyPlaceSensor() {
        return emptyPlaceSensor;
    }

    public synchronized void setEmptyPlaceSensor(boolean emptyPlaceSensor) {
        this.emptyPlaceSensor = emptyPlaceSensor;
        placeSensorInterface.onEmptyPlaceSensorChanged();
    }

    public synchronized boolean isFullPlaceSensor() {
        return fullPlaceSensor;
    }

    public synchronized void setFullPlaceSensor(boolean fullPlaceSensor) {
            this.fullPlaceSensor = fullPlaceSensor;
            placeSensorInterface.onFullPlaceSensorChanged();
    }

    public void startTimer(int seconds, Runnable callback) {
        new Thread(() -> {
            try {
                Thread.sleep(seconds * 1000L);
                callback.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Timer was interrupted");
            }
        }).start();
    }
}
