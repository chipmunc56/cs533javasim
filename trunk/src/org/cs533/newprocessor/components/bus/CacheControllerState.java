/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cs533.newprocessor.components.bus;

import org.cs533.newprocessor.components.memorysubsystem.MemoryInstruction;
import org.cs533.newprocessor.components.bus.AbstractBusMessage;

/**
 *
 * @author amit
 */
public abstract class CacheControllerState<Msg extends AbstractBusMessage<Msg>> {

    protected final <V> StateAnd<V, CacheControllerState<Msg>> noJump(V v) {
        return new StateAnd(v, this);
    }

    protected final <V> StateAnd<V, CacheControllerState<Msg>> andJump(V v, CacheControllerState next) {
        return new StateAnd(v, next);
    }

    protected final <V> StateAnd<V, CacheControllerState<Msg>> jumpTo(CacheControllerState next) {
        return new StateAnd(null, next);
    }

    protected final <V> StateAnd<V, CacheControllerState<Msg>> noReply() {
        return new StateAnd(null, this);
    }

    public StateAnd<Msg, CacheControllerState<Msg>> recieveBroadcastMessage(Msg b) {
        return null;
    }

    public StateAnd<Msg, CacheControllerState<Msg>> recieveBusResponse(Msg b) {
        return null;
    }

    public StateAnd<Msg, CacheControllerState<Msg>> snoopMemoryResponse(MemoryInstruction response) {
        return null;
    }

    public StateAnd<Msg, CacheControllerState<Msg>> recieveMemoryResponse(MemoryInstruction response) {
        return null;
    }

    public StateAnd<MemoryInstruction, CacheControllerState<Msg>> recieveClientRequest(MemoryInstruction request) {
        return null;
    }

    public StateAnd<MemoryInstruction, CacheControllerState<Msg>> pollRequestStatus(MemoryInstruction request) {
        return null;
    }


    public StateAnd<Msg, CacheControllerState<Msg>> startTransaction() {
        return null;
    }
}