package org.talend.designer.business.diagram.custom.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.image.EImage;
import org.talend.commons.ui.image.ImageProvider;
import org.talend.core.CorePlugin;
import org.talend.designer.business.diagram.custom.commands.DeleteAssignmentCommand;
import org.talend.designer.business.diagram.custom.edit.parts.BusinessItemShapeEditPart;
import org.talend.designer.business.diagram.i18n.Messages;
import org.talend.designer.business.model.business.BusinessItem;
import org.talend.designer.business.model.business.diagram.part.BusinessDiagramEditor;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.ui.actions.AContextualAction;

public class DeleteAssignmentAction extends AContextualAction {

    public DeleteAssignmentAction() {
        super();
        this.setImageDescriptor(ImageProvider.getImageDesc(EImage.DELETE_ICON));
        setText(Messages.getString("DeleteAssignmentAction.DeleteAssignment")); //$NON-NLS-1$
    }

    @Override
    protected void doRun() {
        ISelection selection = getSelection();
        Object obj = ((IStructuredSelection) selection).getFirstElement();
        RepositoryNode node = (RepositoryNode) obj;

        IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
        if (activeEditor instanceof BusinessDiagramEditor) {
            BusinessDiagramEditor editor = (BusinessDiagramEditor) activeEditor;
            ISelection selection2 = editor.getSelection();

            Object firstElement = ((IStructuredSelection) selection2).getFirstElement();
            if (firstElement instanceof BusinessItemShapeEditPart) {
                BusinessItemShapeEditPart editpart = (BusinessItemShapeEditPart) firstElement;
                EObject element = ((Node) editpart.getModel()).getElement();
                if (element instanceof BusinessItem) {
                    BusinessItem businessItem = (BusinessItem) element;
                    DeleteAssignmentCommand command = new DeleteAssignmentCommand(businessItem, node);
                    try {
                        command.execute(null, null);
                    } catch (ExecutionException e) {
                        ExceptionHandler.process(e);
                    }
                    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
                    for (IEditorReference editors : page.getEditorReferences()) {
                        CorePlugin.getDefault().getDiagramModelService().refreshBusinessModel(editors);
                    }
                }

            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.commons.ui.swt.actions.ITreeContextualAction#init(org.eclipse.jface.viewers.TreeViewer,
     * org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(TreeViewer viewer, IStructuredSelection selection) {
        setEnabled(true);
    }

}
