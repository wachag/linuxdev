package hu.bme.mit.linuxdev;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DtsParser {
	private String dtsFileName;
	public DtsParser(String dtsFileName) {
		this.dtsFileName=dtsFileName;
	}
	public List<String> getCompatibles(){
		ArrayList<String> list=new ArrayList<String>();
		try {
			List<String> lines=Files.readAllLines(Paths.get(dtsFileName));
			for(String line: lines) {
				if(line.contains("compatible")) {
					String[] compat=line.split("=");
					if(compat.length>=2) {
						String result;
						result=compat[1].replace(" ", "").replace("\";","").replace("\"","").replace("\'","");
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
