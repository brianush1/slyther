package net.gegy1000.slyther.network.message.fromserver;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.server.MessagePlayerDeathBase;
import net.gegy1000.slyther.util.Log;

public class MessagePlayerDeath extends MessagePlayerDeathBase implements MessageFromServer {
    @Override
    public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
        //int type = buffer.readUInt8();
        //Snake player = client.player;
        client.gameOver();
        Log.debug("Final length: {}", client.gameStatistic.getLength());
    }

}
