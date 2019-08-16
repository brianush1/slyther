package net.gegy1000.slyther.network.message.toserver;

import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherClientMessageBase;

public class MessageSetTurn extends SlytherClientMessageBase {
    public byte direction;

    public MessageSetTurn() {
    }

    public MessageSetTurn(byte direction) {
        this.direction = direction;
    }

    @Override
    public void write(MessageByteBuffer buffer, SlytherClient client) {
        buffer.writeUInt8(252);
        buffer.writeUInt8(direction);
    }

//    @Override
//    public void read(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
//        client.snake.turnDirection = direction;
//    }
}
