package net.gegy1000.slyther.network.message.toserver;

import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherClientMessageBase;

public class MessageStartLogin extends SlytherClientMessageBase {
    @Override
    public void write(MessageByteBuffer buffer, SlytherClient client) {
        buffer.writeUInt8('c');
    }

}