package hu.bme.mit.linuxdev;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.cdt.internal.core.dom.parser.cpp.semantics.EvalUtil.Pair;
import org.eclipse.cdt.ui.templateengine.IPagesAfterTemplateSelectionProvider;
import org.eclipse.cdt.ui.templateengine.IWizardDataPage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import java.util.AbstractMap.SimpleEntry;
public class KernelSettingsPageProvider implements IPagesAfterTemplateSelectionProvider {
	IWizardDataPage[] pages; 
	@Override
	public IWizardDataPage[] createAdditionalPages(IWorkbenchWizard wizard, IWorkbench workbench,
			IStructuredSelection selection) {
		// TODO Auto-generated method stub
		pages = new IWizardDataPage[1];
		pages[0]=new KernelSettingsPage();
		return pages;
	}

	@Override
	public IWizardDataPage[] getCreatedPages(IWorkbenchWizard wizard) {
		// TODO Auto-generated method stub
		return pages;
	}

}
