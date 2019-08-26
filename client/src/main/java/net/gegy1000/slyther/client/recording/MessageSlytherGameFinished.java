/**
 * 
 */
package net.gegy1000.slyther.client.recording;


import net.gegy1000.slyther.client.db.GameStatistic;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.util.Log;

/** Write the statistics at the end of the record file once the game is over.<br>
 * ReplayMan reads this metadata to display on its screen.
 * @author dick
 * @see {@link GameRecorder}
 *
 */
public class MessageSlytherGameFinished extends TimedMessage {
	private MessageByteBuffer bb = new MessageByteBuffer();
	
	public MessageSlytherGameFinished(GameStatistic gameStatistic) {
		bb.writeUInt8('^');
		Long l = gameStatistic.getGamedate().getTime();
		bb.writeInt64(l);
		bb.writeInt32(gameStatistic.getDuration());
		bb.writeInt32(gameStatistic.getKills());
		bb.writeInt32(gameStatistic.getLength());
		bb.writeInt32(gameStatistic.getRank());
		bb.writeASCIIBytes("SLYT");
		int length = bb.position();
		bb.writeUInt16(length-1);
		length = bb.position();
		payload = new byte[length];
		System.arraycopy(bb.array(), 0, payload, 0, length);
		Log.info("bb = {}", payload);
	}
}
