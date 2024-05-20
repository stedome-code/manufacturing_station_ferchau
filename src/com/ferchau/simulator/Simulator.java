package com.ferchau.simulator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Simulator {
    private static final int NUMBER_MACHINES = 3;
    private static final int PRODUCTION_TIME_IN_SECONDS = 6;
    public static void main(String[] args) throws Exception {
        System.out.println("Starting Simulation...");
        ControlSystem controlSystem = new ControlSystem();
        ManufacturingStation manufacturingStation = new ManufacturingStation(controlSystem, NUMBER_MACHINES, PRODUCTION_TIME_IN_SECONDS);
        controlSystem.setPlaceSensorInterface(manufacturingStation);
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown hook triggered. Cleaning up...");
            // Could call e.g. killTimer() to disable all running machines
        }));
        
        while(true)
        {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(0,PRODUCTION_TIME_IN_SECONDS/2 + 1));
            controlSystem.setEmptyPlaceSensor(true);
        }
    }
}
