package net.gegy1000.slyther.network.message.toserver;

import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherClientMessageBase;


public class MessageClientRiddleAnswer extends SlytherClientMessageBase {
	private String answer;

	public MessageClientRiddleAnswer() {
	}

	public MessageClientRiddleAnswer(int[] data) {
		byte[] result = decodeSecret(data);
		StringBuilder sb = new StringBuilder(24);
		for (byte b : result) {
			sb.append((char)b);
		}
		answer = sb.toString();
		return;
	}
	
	private static byte[] decodeSecret(int[] secret) {

		byte[] result = new byte[24];

		int globalValue = 0;
		for (int i = 0; i < 24; i++) {
			int value1 = secret[17 + i * 2];
			if (value1 <= 96) {
				value1 += 32;
			}
			value1 = (value1 - 98 - i * 34) % 26;
			if (value1 < 0) {
				value1 += 26;
			}

			int value2 = secret[18 + i * 2];
			if (value2 <= 96) {
				value2 += 32;
			}
			value2 = (value2 - 115 - i * 34) % 26;
			if (value2 < 0) {
				value2 += 26;
			}

			int interimResult = (value1 << 4) | value2;
			int offset = interimResult >= 97 ? 97 : 65;
			interimResult -= offset;
			if (i == 0) {
				globalValue = 2 + interimResult;
			}
			result[i] = (byte) ((interimResult + globalValue) % 26 + offset);
			globalValue += 3 + interimResult;
		}

		return result;
	}

	@Override
	public void write(MessageByteBuffer buffer, SlytherClient client) {
		buffer.writeASCIIBytes(this.answer);
	}

//	@Override
//	public void read(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
//	}
}