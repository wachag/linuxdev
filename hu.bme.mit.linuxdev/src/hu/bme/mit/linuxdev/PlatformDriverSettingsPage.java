package hu.bme.mit.linuxdev;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

public class PlatformDriverSettingsPage extends AbstractWizardDataPage implements IWizardDataPage {
	private Combo compatibleCombo;
	private Text nameText;
	public PlatformDriverSettingsPage() {
		super("Platform driver settings", "Platform driver settings", null);

	}

	@Override
	public void createControl(Composite parent) {
		Composite contents = new Composite(parent, SWT.NONE);
		setControl(contents);
		contents.setLayout(new GridLayout(2, false));
		Label lblNewLabel_3 = new Label(contents, SWT.NONE);
		lblNewLabel_3.setText("Driver name");
		nameText = new Text(contents, SWT.BORDER);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNewLabel_4 = new Label(contents, SWT.NONE);
		lblNewLabel_4.setText("Compatible device");

		Composite composite1 = new Composite(contents, SWT.NONE);
		composite1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		composite1.setLayout(new GridLayout(2, false));
		compatibleCombo = new Combo(composite1, SWT.NONE);
		compatibleCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		Button selectDts = new Button(composite1, SWT.NONE);
		selectDts.setText("...");
		selectDts.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					FileDialog dlg = new FileDialog(parent.getShell(), SWT.OPEN);
					dlg.setText("Search for device tree source");
					String[] ext = new String[1];
					ext[0] = "*.dts";
					dlg.setFilterExtensions(ext);
					String[] extName = new String[1];
					extName[0] = "Device tree source";
					dlg.setFilterNames(extName);
					String s = dlg.open();
					if (s != null) {
						DtsParser parser = new DtsParser(s);
						List<String> list = parser.getCompatibles();
						for (String elem : list)
							compatibleCombo.add(elem);
						compatibleCombo.select(0);
					}
					break;

				}
			}
		});

	}

	@Override
	public Map<String, String> getPageData() {
		// TODO Auto-generated method stub
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("platform.compatible", compatibleCombo.getText());
		data.put("platform.name", nameText.getText());
		return data;
	}

}
