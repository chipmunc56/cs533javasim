/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cs533.newprocessor.components.memorysubsystem.l2cache;

/**
 *
 * @author amit
 */
public class L2CacheLine {

    public byte[] data;
    public boolean isDirty;

    public L2CacheLine(byte[] data, boolean isDirty) {
        this.data = data;
        this.isDirty = isDirty;
    }

    public boolean isIsDirty() {
        return isDirty;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setIsDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }
}