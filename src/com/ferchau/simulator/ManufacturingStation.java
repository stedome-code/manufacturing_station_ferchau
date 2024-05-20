package com.ferchau.simulator;

public class ManufacturingStation implements PlaceSensorInterface {
    private final int NUMBER_MACHINES;
    private final int PRODUCTION_TIME;
    private final int CONSUMPTION_TIME;
    private ControlSystem controlSystem;
    private Machine[] machines;

    public ManufacturingStation(ControlSystem controlSystem, int numberMachines, int productionTime) {
        this.controlSystem = controlSystem;
        this.NUMBER_MACHINES = numberMachines;
        this.PRODUCTION_TIME = productionTime;
        this.CONSUMPTION_TIME = (int) ((1.0/3.0) * PRODUCTION_TIME);
        this.machines = new Machine[NUMBER_MACHINES];
        for (int i = 0; i < NUMBER_MACHINES; i++) {
            machines[i] = new Machine(i + 1, controlSystem, PRODUCTION_TIME);
        }
    }

    @Override
    public void onEmptyPlaceSensorChanged() {
        if (controlSystem.isEmptyPlaceSensor()) {
            System.out.println("Place (Empty) received Container");
            handleEmptyPlaceSensor();
        } else {
            System.out.println("Place (Empty) is free now, machine retrieved Container");
        }
    }

    @Override
    public void onFullPlaceSensorChanged() {
        if (controlSystem.isFullPlaceSensor()) {
            System.out.println("Place (Full) received Container");
            controlSystem.startTimer(CONSUMPTION_TIME, this::resetFullPlaceSensor);
            if (controlSystem.isEmptyPlaceSensor())
            {
                handleEmptyPlaceSensor();
            }
        } else {
            System.out.println("Place (Full) is free now, waiting for new Container");
            handleFullPlaceSensor();
        }
    }

    private void handleEmptyPlaceSensor() {
        for (Machine machine : machines) {
            if (!machine.isOccupied()) {
                machine.startProduction();
                controlSystem.setEmptyPlaceSensor(false);
                return;
            }
        }
        System.out.println("!!! ---- All machines are occupied, Waiting --- !!!");
    }

    private void resetFullPlaceSensor() {
        controlSystem.setFullPlaceSensor(false);
    }

    private void handleFullPlaceSensor() {
        for (Machine machine : machines) {
            if (machine.isWaiting()) {
                machine.retrieveProducedContainer();
                return;
            }
        }
    }
}
