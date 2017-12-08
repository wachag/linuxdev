package hu.bme.mit.linuxdev;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.List;

public class DtbParser {

	private String dtbFileName;
	private DtbHeader hdr;

	public DtbParser(String dtsFileName) {
		this.dtbFileName = dtsFileName;
	}

	public final int FDT_BEGIN_NODE = 1;
	public final int FDT_END_NODE = 2;
	public final int FDT_PROP = 3;
	public final int FDT_NOP = 4;
	public final int FDT_END = 9;
	public final int FDT_PAD = 0;

	private void writeSpaces(int n) {
		int i;
		for (i = 0; i < n; i++)
			System.out.print(" ");
	}

	private void parseStructure(ByteBuffer buf) {
		int start = hdr.getOff_dt_struct();
		buf.position(start);
		int indent = 0;
		while (true) {
			int pad = buf.position();
			if (pad % 4 > 0) {
				// System.out.print(pad + " " + (pad % 4) + " ");
				buf.position(pad + 4 - (pad % 4));
				// System.out.println(buf.position());
			}
			if (buf.position() >= hdr.off_dt_struct + hdr.size_dt_struct)
				return;
			int opcode = buf.getInt();
			switch (opcode) {
			case FDT_END:
				return;
			case FDT_BEGIN_NODE:
				String name = "";
				byte c;
				while ((c = buf.get()) != 0) {
					name = name + (char)c;
				}
				writeSpaces(indent);
				System.out.println(name + "{");
				indent++;
				break;
			case FDT_END_NODE:
				indent--;
				writeSpaces(indent);
				System.out.println("}");
				break;
			case FDT_PROP:
				int len = buf.getInt();
				int nameOff = buf.getInt();
				byte[] propData = new byte[len];
				buf.get(propData);
				int pos = buf.position();

				buf.position(hdr.off_dt_strings+nameOff);
				String propName = "";
				while ((c = buf.get()) != 0) {
					propName = propName + (char)c;
				}
				writeSpaces(indent);
				System.out.print(propName);
				if(propName.equals("compatible")) {
					System.out.print(" = ");
					System.out.println(new String(propData));
				}else
				{
					System.out.println();
				}
				buf.position(pos);

				break;
			case FDT_NOP:
			case FDT_PAD:
				break;
			default:
				// System.out.println("Invalid opcode: " + opcode);
				break;

			}
		}
	}

	private List<ReserveEntry> parseMemReserve(ByteBuffer buf) {
		int start = hdr.getOff_mem_rsvmap();
		List<ReserveEntry> list = new ArrayList<ReserveEntry>();
		ReserveEntry entry;
		buf.position(start);
		do {
			entry = new ReserveEntry(buf);
			list.add(entry);
			System.out.println(entry.getAddress() + " " + entry.getSize());
		} while (entry.getAddress() != 0 && entry.getSize() != 0);
		return list;
	}

	public Boolean parse() {
		try {
			FileInputStream fIn = new FileInputStream(dtbFileName);
			FileChannel fChan = fIn.getChannel();

			ByteBuffer buf = ByteBuffer.allocate((int) fChan.size());
			fChan.read(buf);
			buf.rewind();
			buf.order(ByteOrder.BIG_ENDIAN);
			hdr = new DtbHeader(buf);
			System.out.println("Parsing header");
			System.out.println(Integer.toHexString(hdr.getMagic()));
			System.out.println(hdr.getOff_dt_strings());
			System.out.println(hdr.getOff_dt_struct());
			System.out.println("Parsing reserve entries");
			parseMemReserve(buf);
			System.out.println("Parsing structure");
			parseStructure(buf);
			return hdr.getMagic() == 0xd00dfeed;
		} catch (IOException e) {
			return false;
		}
	}

	public List<String> getCompatibles() {
		ArrayList<String> list = new ArrayList<String>();
		try {
			List<String> lines = Files.readAllLines(Paths.get(dtbFileName));
			for (String line : lines) {
				if (line.contains("compatible")) {
					String[] compat = line.split("=");
					if (compat.length >= 2) {
						String result;
						result = compat[1].replace(" ", "").replace("\";", "").replace("\"", "").replace("\'", "");
						list.add(result);
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

}
