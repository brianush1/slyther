package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.SlytherServerMessageBase;

public class MessageRemoveSectorBase extends SlytherServerMessageBase {

    public MessageRemoveSectorBase() {
    }


    @Override
    final public int[] getMessageIds() {
        return new int[] { 'w' };
    }
}
