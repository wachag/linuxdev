package hu.bme.mit.linuxdev;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public class KernelFinder {
	public List<Entry<String, String>> getKernels() {
		List<Entry<String, String>> kernels = new ArrayList<Entry<String, String>>();
		String directory = File.separator + "lib" + File.separator + "modules";
		File dir = new File(directory);
		File[] kernelDirs = dir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				File fil = new File(dir + File.separator + name);
				Boolean result = fil.isDirectory();
				File build = new File(fil.getAbsoluteFile() + File.separator + "build");
				return build.exists();
			}
		});
		if (kernelDirs != null)
			for (File f : kernelDirs) {
				try {
					Entry<String, String> item = new SimpleEntry<String, String>(f.getName(),
							f.getCanonicalPath() + File.separator + "build");
					kernels.add(item);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		return kernels;
	}
}
