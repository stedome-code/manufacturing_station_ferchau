package com.ferchau.simulator;

public class Machine {
    private int id;
    private boolean isOccupied;
    private boolean isWaiting;
    private ControlSystem controlSystem;
    private int productionTime = 3;

    public Machine(int id, ControlSystem controlSystem, int productionTime) {
        this.id = id;
        this.controlSystem = controlSystem;
        this.productionTime = productionTime;
        this.isOccupied = false;
        this.isWaiting = false;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void startProduction() {
        System.out.println("Machine " + id + " is running");
        isOccupied = true;
        controlSystem.startTimer(productionTime, this::finishProduction);
    }

    private void finishProduction() {
        System.out.println("Machine " + id + " finished running.");
        if (controlSystem.isFullPlaceSensor()) {
            isWaiting = true;
            System.out.println("--- !!! There is no empty space at the FullPlaceSensor --- !!!");
            return;
        }
        isOccupied = false;
        controlSystem.setFullPlaceSensor(true);
    }

    public void retrieveProducedContainer() {
        isOccupied = false;
        isWaiting = false;
        controlSystem.setFullPlaceSensor(true);
    }
}
