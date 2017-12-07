package hu.bme.mit.linuxdev;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public class GccFinder {
	public List<Entry<String, String>> getCrossGcc() {
		List<Entry<String, String>> gccs = new ArrayList<Entry<String, String>>();
		String path = System.getenv("PATH");
		String[] directories = path.split(File.pathSeparator);
		for (String directory : directories) {
			File dir = new File(directory);
			File[] gcc = dir.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith("-gcc");
				}
			});
			if (gcc != null)
				for (File f : gcc) {
					try {
						Entry<String, String> item = new SimpleEntry<String, String>(f.getName(), f.getCanonicalPath());
						gccs.add(item);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		}
		return gccs;
	}

}
