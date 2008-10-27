// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.core.ui.editor.update;

import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.talend.core.model.update.UpdateResult;
import org.talend.core.model.update.UpdatesConstants;
import org.talend.designer.core.i18n.Messages;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class UpdateDetectionDialog extends SelectionDialog {

    private static final String ITEMS = Messages.getString("UpdateDetectionDialog.Items"); //$NON-NLS-1$

    private static final String OPERATIONS = Messages.getString("UpdateDetectionDialog.Operations"); //$NON-NLS-1$

    private static final String REMARKS = Messages.getString("UpdateDetectionDialog.Remarks"); //$NON-NLS-1$

    private static final String DEFAULT_TITLE = Messages.getString("UpdateDetectionDialog.Title"); //$NON-NLS-1$

    private static final String DEFAULT_MESSAGE = Messages.getString("UpdateDetectionDialog.Messages"); //$NON-NLS-1$

    private static final String WARNING_MESSAGE = Messages.getString("UpdateDetectionDialog.WarningMessage"); //$NON-NLS-1$
    
    private static final String READ_ONLY_JOB_WARNING_MESSAGE = Messages.getString("ProcessUpdateManager.ReadOnlyProcessUpdateWarningMessages"); //$NON-NLS-1$

    // sizing constants
    private static final int SIZING_SELECTION_WIDGET_HEIGHT = 400;

    private static final int SIZING_SELECTION_WIDGET_WIDTH = 550;

    private static final int SIZING_COLUMN_WIDTH = 25;

    private List<UpdateResult> inputElement;

    private CheckboxTreeViewer viewer;

    private UpdateViewerHelper helper;

    private Button selectButton;

    private Label messLabel;

    private Label imageLabe;

    private boolean canCancel = true;

    private boolean isJobReadOnly = false;

    /**
     * ggu UpdateCheckDialog constructor comment.
     * 
     * @param parentShell
     */
    public UpdateDetectionDialog(Shell parentShell, List<UpdateResult> input) {
        this(parentShell, input, null);
        setTitle(DEFAULT_TITLE);
        setMessage(DEFAULT_MESSAGE);
    }

    public UpdateDetectionDialog(Shell parentShell, List<UpdateResult> input, String message) {
        super(parentShell);
        setHelpAvailable(false);
        setTitle(UpdatesConstants.EMPTY);
        setShellStyle(SWT.TITLE | SWT.RESIZE | SWT.APPLICATION_MODAL | getDefaultOrientation());
        inputElement = input;
        if (message != null) {
            setMessage(message);
        } else {
            setMessage(UpdatesConstants.EMPTY);
        }
        helper = new UpdateViewerHelper(this);
        checkInitialSelections();
    }

    protected UpdateViewerHelper getViewerHelper() {
        return this.helper;
    }

    public List<UpdateResult> getInputElements() {
        return this.inputElement;
    }

    public CheckboxTreeViewer getViewer() {
        return this.viewer;
    }

    @Override
    protected boolean canHandleShellCloseEvent() {
        return false;
    }

    private void checkInitialSelections() {
        for (UpdateResult result : getInputElements()) {
            result.setChecked(true);
            if (result.isReadOnlyProcess()) {
                result.setChecked(false);
                this.isJobReadOnly = true;
            }
            switch (result.getResultType()) {
            case RENAME:
            case RELOAD:
            case JOBLET_UPDATE:
                this.canCancel = false;
                return;
            default:
                this.canCancel = true;
            }
        }

    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        // Ok
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
        if (canCancel) {
            createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
        }
    }

    public void setSelectButtonLabel(String label) {
        if (selectButton != null && label != null) {
            selectButton.setText(label);
        }
    }

    private Composite createBottomButtonArea(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout gridLayout = new GridLayout();
        gridLayout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING * 4);
        composite.setLayout(gridLayout);
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.HORIZONTAL_ALIGN_END));

        // expand
        Button expandButton = createButton(composite, 99, Messages.getString("UpdateDetectionDialog.Expand"), false); //$NON-NLS-1$
        expandButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (getViewer() != null) {
                    getViewer().expandAll();
                }
            }
        });
        // collapse
        Button collapseButton = createButton(composite, 98, Messages.getString("UpdateDetectionDialog.Collapse"), false); //$NON-NLS-1$
        collapseButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (getViewer() != null) {
                    getViewer().collapseAll();
                }
            }
        });
        // "select all" button
        selectButton = createButton(composite, IDialogConstants.SELECT_ALL_ID, WorkbenchMessages.SelectionDialog_selectLabel,
                false);
        // init label;
        if (getViewerHelper() != null) {
            getViewerHelper().refreshSelectButton();
        }

        selectButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (getViewerHelper() != null) {
                    boolean state = false;

                    if (!isJobReadOnly && WorkbenchMessages.SelectionDialog_selectLabel.equals(selectButton.getText())) {
                        state = true;
                    }
                    getViewerHelper().selectAll(state);
                }
            }
        });

        return composite;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        initializeDialogUnits(composite);

        GridData data = new GridData(GridData.FILL_BOTH);
        data.heightHint = SIZING_SELECTION_WIDGET_HEIGHT;
        data.widthHint = SIZING_SELECTION_WIDGET_WIDTH;
        composite.setLayoutData(data);

        createHeadMessage(composite);
        createTreeTableView(composite);
        createBottomButtonArea(composite);

        if (getViewerHelper() != null) {
            getViewerHelper().initViewerState();
        }
        return composite;
    }

    @Override
    protected Label createMessageArea(Composite composite) {
        Label label = new Label(composite, SWT.WRAP);
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
        label.setLayoutData(gridData);
        label.setFont(composite.getFont());
        return label;
    }

    private Composite createHeadMessage(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(2, false));
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        imageLabe = new Label(composite, SWT.NONE);
        imageLabe.setImage(Display.getDefault().getSystemImage(SWT.ICON_WARNING));
        // imageLabe.setImage(ImageProvider.getImage(EImage.WARNING_ICON));

        messLabel = createMessageArea(composite);

        Label label = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR | SWT.SHADOW_OUT);
        label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        return composite;
    }

    /**
     * create table view.
     */
    private Composite createTreeTableView(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        viewer = new CheckboxTreeViewer(composite, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

        viewer.setContentProvider(new UpdateContentProvider());
        viewer.setLabelProvider(new UpdateLabelProvider());
        viewer.setSorter(new UpdateViewerSorter());
        viewer.setInput(getInputElements());
        // viewer.setColumnProperties(new String[] { ITEMS, OPERATIONS, REMARKS });
        // viewer.setCellEditors(new CellEditor[] { null, null, null });
        // viewer.setCellModifier(new UpdateCellModifier());

        final Tree tree = viewer.getTree();
        tree.setHeaderVisible(true);
        tree.setLinesVisible(true);
        tree.setLayoutData(new GridData(GridData.FILL_BOTH));
        addViewerListener();
        createColumns(tree);

        return composite;
    }

    /**
     * create table columns.
     */
    private void createColumns(final Tree tree) {
        TreeColumn column = new TreeColumn(tree, SWT.NONE);
        column.setText(ITEMS);
        column.setWidth(SIZING_COLUMN_WIDTH * 10);
        tree.setSortColumn(column);

        // column = new TreeColumn(tree, SWT.NONE);
        // column.setText("property");
        // column.setWidth(SIZING_COLUMN_WIDTH * 4);

        column = new TreeColumn(tree, SWT.NONE);
        column.setText(OPERATIONS);
        column.setWidth(SIZING_COLUMN_WIDTH * 7);

        column = new TreeColumn(tree, SWT.NONE);
        column.setText(REMARKS);
        column.setWidth(SIZING_COLUMN_WIDTH * 6);
    }

    private void addViewerListener() {
        viewer.addCheckStateListener(new ICheckStateListener() {

            public void checkStateChanged(CheckStateChangedEvent event) {
                if (readOnlyCheck(event)) {
                    updateReadOnlyJobWarnMessage();
                    return;
                }

                if (getViewerHelper() != null) {
                    getViewerHelper().updateCheckedState(event.getElement(), event.getChecked());
                }
            }

            /**
             * Checks if current job is read-only mode or not.
             * 
             * @param event
             */
            private boolean readOnlyCheck(CheckStateChangedEvent event) {
                Job currentJob = null;
                if (event.getElement() instanceof Job) {
                    currentJob = (Job) event.getElement();
                } else if (event.getElement() instanceof Category) {
                    currentJob = ((Category) event.getElement()).getParent();
                } else if (event.getElement() instanceof Item) {
                    currentJob = ((Item) event.getElement()).getParent().getParent();
                }

                if (currentJob == null) {
                    return false;
                }

                if (currentJob.isReadOnlyProcess()) {
                    event.getCheckable().setChecked(event.getElement(), false);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void okPressed() {

        Tree tree = viewer.getTree();
        if (tree.isDisposed()) {
            return;
        }
        setResult(getInputElements());
        super.okPressed();

    }

    public void updateWarnMessage() {
        imageLabe.setVisible(true);
        messLabel.setText("\n" + WARNING_MESSAGE); //$NON-NLS-1$
    }

    public void updateNomarlMessage() {
        imageLabe.setVisible(false);
        messLabel.setText(DEFAULT_MESSAGE);
    }

    /**
     * DOC Administrator Comment method "updateReadOnlyJobWarnMessage".
     */
    public void updateReadOnlyJobWarnMessage() {
        imageLabe.setVisible(true);
        messLabel.setText(READ_ONLY_JOB_WARNING_MESSAGE);
        
    }
}
