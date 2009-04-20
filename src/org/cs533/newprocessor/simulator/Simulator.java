/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cs533.newprocessor.simulator;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import org.cs533.newprocessor.ComponentInterface;
import org.cs533.newprocessor.Globals;
import org.cs533.newprocessor.components.memorysubsystem.l2cache.L2Cache;
import org.cs533.newprocessor.components.memorysubsystem.MainMemory;
import org.cs533.newprocessor.components.memorysubsystem.MemoryInstruction;

/**
 *
 * @author amit
 */
public class Simulator {

    static ArrayList<ComponentInterface> components = new ArrayList<ComponentInterface>();
    static Thread simulatorThread;

    public Simulator() {
    }

    public static byte[] generateSimpleCacheLineFromOffset(int offset) {

        byte[] b = new byte[Globals.CACHE_LINE_SIZE];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) offset++;
        }
        return b;
    }

    public static void main(String[] args) throws Exception {
        L2Cache l2 = new L2Cache(new MainMemory());

        MemoryInstruction[] instruction = new MemoryInstruction[4];//
        for (int i = 0; i < instruction.length; i++) {
            instruction[i] = new MemoryInstruction(i*4, generateSimpleCacheLineFromOffset(i * 4), true);
            l2.setMemoryInstruction(instruction[i]);
        }
        runSimulation();
        for (int i = 0; i < instruction.length; i++) {
            while (!instruction[i].getIsCompleted()) {
                Thread.sleep(1);
            }
        }
        MemoryInstruction readInstr = new MemoryInstruction(0, null, false);
        l2.setMemoryInstruction(readInstr);
        while (!readInstr.getIsCompleted()) {
            Thread.sleep(1);
        }
        for (int i = 0; i < readInstr.getOutData().length; i++) {
            System.out.println("FOR address = 0x" + Integer.toHexString(i + readInstr.getInAddress()) + " the value is = " + readInstr.getOutData()[i]);
        }
        stopSimulation();
    }

    public static void registerComponent(ComponentInterface component) {
        components.add(component);
    }

    public static void stopSimulation() {
        simulatorThread.interrupt();
    }

    public static void runSimulation() {
        simulatorThread = new Thread(new Runnable() {

            public void run() {
                while (true) {
                    try {
                        runPrep();
                        runClock();
                    } catch (InterruptedException ex) {
                        Logger.getAnonymousLogger().info("recieved interrupt probably caused by stop call");
                        break;
                    }

                }
            }
        });
        simulatorThread.start();
    }

    public static void runPrep() throws InterruptedException {
        Thread[] t = new Thread[components.size()];
        final AtomicInteger incr = new AtomicInteger(0);
        for (int i = 0; i < t.length; i++) {
            t[i] = new Thread(new Runnable() {

                public void run() {
                    components.get(incr.getAndIncrement()).runPrep();
                }
            });
            t[i].start();
        }
        for (int i = 0; i < t.length; i++) {
            t[i].join();
        }

    }

    public static void runClock() throws InterruptedException {
        Thread[] t = new Thread[components.size()];
        final AtomicInteger incr = new AtomicInteger(0);
        for (int i = 0; i < t.length; i++) {
            t[i] = new Thread(new Runnable() {

                public void run() {
                    components.get(incr.getAndIncrement()).runClock();
                }
            });
            t[i].start();
        }
        for (int i = 0; i < t.length; i++) {
            t[i].join();
        }

    }
}