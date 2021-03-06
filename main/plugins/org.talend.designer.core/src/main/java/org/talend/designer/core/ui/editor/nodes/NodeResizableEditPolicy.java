// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.core.ui.editor.nodes;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.talend.designer.core.ui.editor.TalendScalableFreeformRootEditPart;
import org.talend.designer.core.ui.editor.cmd.ResizeNodeCommand;
import org.talend.designer.core.ui.editor.nodecontainer.NodeContainer;

/**
 */
public class NodeResizableEditPolicy extends ResizableEditPolicy {

    @Override
    protected Command getResizeCommand(ChangeBoundsRequest request) {
        Object parent = getHost().getParent().getModel();
        if (!(parent instanceof NodeContainer)) {
            return null;
        }

        Node node = (Node) getHost().getModel();
        if (node.isReadOnly()) {
            return null;
        }

        TalendScalableFreeformRootEditPart rootEditPart= (TalendScalableFreeformRootEditPart) getHost().getRoot();
        double scale = 1/rootEditPart.getZoomManager().getZoom();
        return new ResizeNodeCommand(node, new Dimension(node.getSize().width + request.getSizeDelta().getScaled(scale).width,
                node.getSize().height + request.getSizeDelta().getScaled(scale).height));
    }

}
