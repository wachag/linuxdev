package hu.bme.mit.linuxdev;

public class TestDtb {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DtbParser dtb=new DtbParser("/home/wachag/Projects/kintex7/linux/sw/linux.dtb");
		dtb.parse();
	}

}
