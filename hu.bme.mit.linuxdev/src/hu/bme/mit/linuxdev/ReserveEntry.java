package hu.bme.mit.linuxdev;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class ReserveEntry{
	long address;
	long size;
	public ReserveEntry(ByteBuffer buf) {
		buf.order(ByteOrder.BIG_ENDIAN);
		address=buf.getLong();
		size=buf.getLong();
	}
	public long getAddress() {
		return address;
	}
	public long getSize() {
		return size;
	}
}