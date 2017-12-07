package hu.bme.mit.linuxdev;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.cdt.ui.templateengine.AbstractWizardDataPage;
import org.eclipse.cdt.ui.templateengine.IWizardDataPage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.RowData;

public class KernelSettingsPage extends AbstractWizardDataPage implements IWizardDataPage {
	private Text authorText;
	private Text descriptionText;
	private Combo licenseCombo;
	private Combo archCombo;
	private Combo compilerCombo;
	private Combo kernelCombo;

	public KernelSettingsPage() {
		super("Kernel module settings", "Kernel module settings", null);

	}

	@Override
	public void createControl(Composite parent) {
		Composite contents = new Composite(parent, SWT.NONE);
		setControl(contents);
		contents.setLayout(new GridLayout(2, false));

		Label lblNewLabel = new Label(contents, SWT.NONE);
		lblNewLabel.setText("Author");

		authorText = new Text(contents, SWT.BORDER);
		authorText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNewLabel_1 = new Label(contents, SWT.NONE);
		lblNewLabel_1.setText("Description");

		descriptionText = new Text(contents, SWT.BORDER);
		descriptionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNewLabel_2 = new Label(contents, SWT.NONE);
		lblNewLabel_2.setText("License");

		licenseCombo = new Combo(contents, SWT.NONE);
		licenseCombo.setItems(new String[] { "GPL", "BSD", "MIT" });
		licenseCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		licenseCombo.select(0);

		Label lblNewLabel_3 = new Label(contents, SWT.NONE);
		lblNewLabel_3.setText("Architecture");

		archCombo = new Combo(contents, SWT.NONE);
		archCombo.setItems(new String[] { "x86", "arm", "arm64", "microblaze" });
		archCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		archCombo.select(0);

		Label lblNewLabel_4 = new Label(contents, SWT.NONE);
		lblNewLabel_4.setText("Compiler");

		Composite composite1 = new Composite(contents, SWT.NONE);
		composite1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		composite1.setLayout(new GridLayout(2, false));
		compilerCombo = new Combo(composite1, SWT.NONE);
		compilerCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		Button selectGcc = new Button(composite1, SWT.NONE);
		selectGcc.setText("...");
		compilerCombo.add("gcc",0);
		compilerCombo.select(0);
		GccFinder finder = new GccFinder();
		for (Entry<String, String> entry : finder.getCrossGcc()) {
			compilerCombo.add(entry.getKey());
		}
		selectGcc.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					FileDialog dlg=new FileDialog(parent.getShell(),SWT.OPEN);
					dlg.setText("Search for gcc");
					String[] ext=new String[1];
					ext[0]="*-gcc";
					dlg.setFilterExtensions(ext);
					String[] extName=new String[1];
					extName[0]="Cross GCC";
					dlg.setFilterNames(extName);
					String s=dlg.open();
					if(s!=null) {
						compilerCombo.add(s, 0);
						compilerCombo.select(0);
					}
					break;

				}
			}
		});

		Label lblNewLabel_5 = new Label(contents, SWT.NONE);
		lblNewLabel_5.setText("Kernel");

		Composite composite2 = new Composite(contents, SWT.NONE);
		composite2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		composite2.setLayout(new GridLayout(2, false));
		kernelCombo = new Combo(composite2, SWT.NONE);
		kernelCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		kernelCombo.select(0);

		Button selectKernel = new Button(composite2, SWT.NONE);
		selectKernel.setText("...");
		selectKernel.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					DirectoryDialog dlg=new DirectoryDialog(parent.getShell());
					dlg.setMessage("Choose kernel directory");
					String s=dlg.open();
					if(s!=null) {
						kernelCombo.add(s, 0);
						kernelCombo.select(0);
					}
					break;

				}
			}
		});
		
		KernelFinder kfinder = new KernelFinder();
		for (Entry<String, String> entry : kfinder.getKernels()) {
			kernelCombo.add(entry.getValue());
		}
	}

	@Override
	public Map<String, String> getPageData() {
		// TODO Auto-generated method stub
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("module.author", authorText.getText());
		data.put("module.description", descriptionText.getText());
		data.put("module.license", licenseCombo.getText());
		data.put("module.arch", archCombo.getText());
		String cc = compilerCombo.getText();
		if (cc.lastIndexOf("gcc") >= 0)
			cc = cc.substring(0, cc.lastIndexOf("gcc"));
		data.put("module.ccprefix", cc);
		data.put("module.kerneldir", kernelCombo.getText());
		return data;
	}

}
