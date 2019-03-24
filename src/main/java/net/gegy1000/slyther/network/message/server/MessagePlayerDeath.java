package net.gegy1000.slyther.network.message.server;

import java.util.Date;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.game.entity.Snake;
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
        Snake player = client.player;
        client.finalLength = player.getLength();
        client.gameRunTime = (int)((new Date().getTime() - client.gameStartTime.getTime()) / 1000);
        Log.info("Final length: {}", client.finalLength);
    }

    @Override
    public int[] getMessageIds() {
        return new int[] { 'v' };
    }
}
