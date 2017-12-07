package hu.bme.mit.linuxdev;

import org.eclipse.cdt.ui.templateengine.IPagesAfterTemplateSelectionProvider;
import org.eclipse.cdt.ui.templateengine.IWizardDataPage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

public class PlatformDriverSettingsPageProvider implements IPagesAfterTemplateSelectionProvider {
	IWizardDataPage[] pages; 
	@Override
	public IWizardDataPage[] createAdditionalPages(IWorkbenchWizard wizard, IWorkbench workbench,
			IStructuredSelection selection) {
		// TODO Auto-generated method stub
		pages = new IWizardDataPage[2];
		pages[0]=new KernelSettingsPage();
		pages[1]=new PlatformDriverSettingsPage();
		return pages;
	}

	@Override
	public IWizardDataPage[] getCreatedPages(IWorkbenchWizard wizard) {
		return pages;
	}


}
