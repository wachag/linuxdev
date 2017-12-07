package hu.bme.mit.linuxdev;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICLanguageSettingEntry;
import org.eclipse.cdt.core.settings.model.ICSettingEntry;
import org.eclipse.cdt.core.settings.model.util.CDataUtil;
import org.w3c.dom.Element;
import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.envvar.IEnvironmentVariable;
import org.eclipse.cdt.core.language.settings.providers.LanguageSettingsSerializableProvider;
import org.eclipse.core.internal.content.Activator;
import org.eclipse.core.resources.IResource;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.core.runtime.Status;

public class KernelLanguageSettingsProvider extends LanguageSettingsSerializableProvider {
	public KernelLanguageSettingsProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void serializeEntries(Element elementProvider) {
		// TODO Auto-generated method stub
		super.serializeEntries(elementProvider);
	}

	@Override
	public void loadEntries(Element providerNode) {
		// TODO Auto-generated method stub
		super.loadEntries(providerNode);
	}

	@Override
	public synchronized List<ICLanguageSettingEntry> getSettingEntries(ICConfigurationDescription configDesc,
			IResource rc, String languageId) {
		List<ICLanguageSettingEntry> list = new ArrayList<ICLanguageSettingEntry>();
		if (!getLanguageScope().contains(languageId))
			return null;
		IEnvironmentVariable kdir_var = CCorePlugin.getDefault().getBuildEnvironmentManager().getVariable("KDIR",
				configDesc, true);
		IEnvironmentVariable arch_var = CCorePlugin.getDefault().getBuildEnvironmentManager().getVariable("ARCH",
				configDesc, true);
		if (kdir_var == null || arch_var == null)
			return null;
		String kdir = kdir_var.getValue();
		String arch = arch_var.getValue();
		if (arch == null)
			arch = "x86";
		if (kdir == null || arch == null)
			return null;
		try {
			File incDir = new File(kdir + File.separator + "include");
			File kconfigFile = new File(
					kdir + File.separator + "include" + File.separator + "linux" + File.separator + "kconfig.h");
			File archIncDir = new File(
					kdir + File.separator + "arch" + File.separator + arch + File.separator + "include");
			File asmGenDir = new File(kdir + File.separator + "include" + File.separator + "asm-generic");
			ICLanguageSettingEntry entry = CDataUtil.createCIncludePathEntry(incDir.getCanonicalPath(),
					ICSettingEntry.READONLY | ICSettingEntry.RESOLVED);
			list.add(entry);
			entry = CDataUtil.createCIncludePathEntry(asmGenDir.getCanonicalPath(),
					ICSettingEntry.READONLY | ICSettingEntry.RESOLVED);
			list.add(entry);
			entry = CDataUtil.createCIncludePathEntry(archIncDir.getCanonicalPath(),
					ICSettingEntry.READONLY | ICSettingEntry.RESOLVED);
			list.add(entry);
			entry = CDataUtil.createCMacroFileEntry(kconfigFile.getCanonicalPath(),
					ICSettingEntry.READONLY | ICSettingEntry.RESOLVED);
			list.add(entry);

			entry = CDataUtil.createCMacroEntry("__KERNEL__", "1", ICSettingEntry.READONLY | ICSettingEntry.RESOLVED);
			list.add(entry);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
}
