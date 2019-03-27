package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherServerMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;
import net.gegy1000.slyther.util.Log;

public class MessagePlayerDeath extends SlytherServerMessageBase {
    @Override
    public void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
    }

    @Override
    public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
        //int type = buffer.readUInt8();
        //Snake player = client.player;
        client.gameOver();
        Log.debug("Final length: {}", client.gameStatistic.getLength());
    }

    @Override
    public int[] getMessageIds() {
        return new int[] { 'v' };
    }
}
