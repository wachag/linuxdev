package hu.bme.mit.linuxdev;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class DtbHeader{
	int magic;
	int totalsize;
	int off_dt_struct;
	int off_dt_strings;
	int off_mem_rsvmap;
	int version;
	int last_comp_version;
	int boot_cpuid_phys;
	int size_dt_string;
	int size_dt_struct;
	
	public DtbHeader(ByteBuffer buf) {
		buf.order(ByteOrder.BIG_ENDIAN);
		magic=buf.getInt();
		totalsize=buf.getInt();
		off_dt_struct=buf.getInt();
		off_dt_strings=buf.getInt();
		off_mem_rsvmap=buf.getInt();
		version=buf.getInt();
		last_comp_version=buf.getInt();
		boot_cpuid_phys=buf.getInt();
		size_dt_string=buf.getInt();
		size_dt_struct=buf.getInt();
	}

	public int getMagic() {
		return magic;
	}

	public int getTotalsize() {
		return totalsize;
	}

	public int getOff_dt_struct() {
		return off_dt_struct;
	}

	public int getOff_dt_strings() {
		return off_dt_strings;
	}

	public int getOff_mem_rsvmap() {
		return off_mem_rsvmap;
	}

	public int getVersion() {
		return version;
	}

	public int getLast_comp_version() {
		return last_comp_version;
	}

	public int getBoot_cpuid_phys() {
		return boot_cpuid_phys;
	}

	public int getSize_dt_string() {
		return size_dt_string;
	}

	public int getSize_dt_struct() {
		return size_dt_struct;
	}
}