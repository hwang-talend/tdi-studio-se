// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.sqlbuilder.dbstructure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.utils.data.container.Container;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.builder.connection.DatabaseConnection;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.metadata.builder.connection.QueriesConnection;
import org.talend.core.model.metadata.builder.connection.Query;
import org.talend.core.model.metadata.builder.connection.TableHelper;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.Folder;
import org.talend.core.model.repository.IRepositoryObject;
import org.talend.core.model.repository.RepositoryObject;
import org.talend.repository.model.IRepositoryFactory;
import org.talend.repository.model.RepositoryFactoryProvider;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.RepositoryNode.ENodeType;
import org.talend.repository.model.RepositoryNode.EProperties;
import org.talend.repository.ui.views.RepositoryContentProvider;
import org.talend.repository.ui.views.RepositoryView;
import org.talend.sqlbuilder.SqlBuilderPlugin;
import org.talend.sqlbuilder.repository.utility.SQLBuilderRepositoryNodeManager;
import org.talend.sqlbuilder.util.ConnectionParameters;
import org.talend.sqlbuilder.util.ImageUtil;

/**
 * Detailled comment for this class. <br/>
 * $Id:  DBTreeProvider.java Version 1.0 Nov 6, 2006 2:29:19 AM $
 * @author Hou Peiqin
 * 
 */
public class DBTreeProvider extends LabelProvider implements ITableLabelProvider, ITreeContentProvider,
ITableColorProvider {
    private SQLBuilderRepositoryNodeManager repositoryNodeManager = new SQLBuilderRepositoryNodeManager();
    private RepositoryContentProvider repositoryContentProvider;
    private ConnectionParameters connectionParameters;
    private RepositoryView repositoryView;
    private boolean isRefresh;
    private Map<String, Color> colors = new HashMap<String, Color>(); 
    
    public DBTreeProvider(RepositoryView repositoryView, ConnectionParameters connectionParameters) {
        this.connectionParameters = connectionParameters;
        this.repositoryContentProvider = new RepositoryContentProvider(repositoryView);
        this.repositoryView = repositoryView;
        colors.put("COLOR_RED", Display.getDefault().getSystemColor(SWT.COLOR_RED));
        colors.put("COLOR_GRAY", Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
    }
    
    
    public boolean isRefresh()  {
        return isRefresh;
    }

    public void setRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public Image getColumnImage(Object element, int columnIndex) {
        if (columnIndex == 1) {
            return null;
        }
        RepositoryNode node = (RepositoryNode) element;
        return ImageUtil.getImage(((SqlBuilderRepositoryObject) node.getObject()).getImage()); 
    }
    
    public String getColumnText(Object element, int columnIndex) {
        RepositoryNode node = (RepositoryNode) element;
        if (columnIndex == 0) {
            return ((SqlBuilderRepositoryObject) node.getObject()).getSourceName(); 
        } else if (columnIndex == 1) {
            return ((SqlBuilderRepositoryObject) node.getObject()).getRepositoryName();
        }
        
        return null;
    }
    
    @SuppressWarnings("static-access")
    public Object[] getChildren(Object parentElement) {
        if (isRefresh) {
            RepositoryNode repositoryNode = (RepositoryNode) parentElement;
            RepositoryNode rootNode = repositoryNodeManager.getRoot(repositoryNode);
            rootNode.getChildren().clear();
            DatabaseConnection metadataConnection = (DatabaseConnection) ((ConnectionItem) repositoryNode.getObject().getProperty()
                    .getItem()).getConnection();
            createTables(rootNode, rootNode.getObject(), metadataConnection, 
            		((SqlBuilderRepositoryObject) rootNode.getObject()).isBuildIn());
            return repositoryNode.getChildren().toArray();
        } else {
            return ((RepositoryNode) parentElement).getChildren().toArray();
        }
    }

    public Object getParent(Object element) {
        return repositoryContentProvider.getParent(element);
    }

    public boolean hasChildren(Object element) {
        return repositoryContentProvider.hasChildren(element);
    }

    public Object[] getElements(Object inputElement) {
        if (!(inputElement instanceof RepositoryNode)) {
            return null;
        }
        RepositoryNode treeRoot = (RepositoryNode) inputElement;
        initialize(treeRoot);
        return treeRoot.getChildren().toArray();
    }

    private void initialize(RepositoryNode treeRoot) {
        if (!connectionParameters.isRepository()) {
            addNode(treeRoot, repositoryNodeManager.getRepositoryNodeByBuildIn(treeRoot, connectionParameters).getObject(), true);
        }
        IRepositoryFactory factory = RepositoryFactoryProvider.getInstance();
        
        try {
            convert(factory.getMetadataConnection(), treeRoot, ERepositoryObjectType.METADATA_CONNECTIONS);
        } catch (Exception e) {
            SqlBuilderPlugin.log(e.getMessage(), e);
        }
    }
    
    private void convert(Container fromModel, RepositoryNode parent, ERepositoryObjectType type) {
        if (fromModel.isEmpty()) {
            return;
        }

        for (Object obj : fromModel.getSubContainer()) {
            Container container = (Container) obj;
            Folder oFolder = new Folder(container.getId(), container.getLabel());
            oFolder.setPurpose("Images.closedFolder");
            oFolder.setStatusCode(oFolder.getLabel());
            oFolder.setLabel(null);
            RepositoryNode folder = new RepositoryNode(oFolder, parent, ENodeType.SIMPLE_FOLDER);
            folder.setProperties(EProperties.LABEL, container.getLabel());
            //ERepositoryObjectType.FOLDER);
            folder.setProperties(EProperties.CONTENT_TYPE, RepositoryNodeType.FOLDER);
            parent.getChildren().add(folder);
            convert(container, folder, type);
        }

        for (Object obj : fromModel.getMembers()) {
            IRepositoryObject repositoryObject = (IRepositoryObject) obj;
            addNode(parent, repositoryObject, false);
        }
    }
    
    private void addNode(RepositoryNode parent, IRepositoryObject repositoryObject, boolean isBuildIn) {
        IRepositoryFactory factory = RepositoryFactoryProvider.getInstance();
        DatabaseConnection connection = (DatabaseConnection) ((ConnectionItem) repositoryObject
                .getProperty().getItem()).getConnection();
        String sid = connection.getSID();
        MetadataConnectionRepositoryObject  connectionRepositoryObject = new MetadataConnectionRepositoryObject(repositoryObject);
        if (isBuildIn) {
        	connectionRepositoryObject.setRepositoryName("Built-In");
        } else {
        	connectionRepositoryObject.setRepositoryName(repositoryObject.getLabel());
        }
        connectionRepositoryObject.setSourceName((sid == null || sid.trim().equals("")) ? connection.getDatasourceName() : sid);
        if (!isBuildIn) {
        connectionRepositoryObject.setImage("Images.ConnectionIcon");
        } else {
        	connectionRepositoryObject.setImage("Images.DatabaseIcon");
        }
        if (connection.isDivergency() && !isBuildIn) {
            connectionRepositoryObject.setColor("COLOR_RED");
        }
        connectionRepositoryObject.setBuildIn(isBuildIn);
        
        RepositoryNode node = new RepositoryNode(connectionRepositoryObject, parent, ENodeType.REPOSITORY_ELEMENT);
        node.setProperties(EProperties.CONTENT_TYPE, RepositoryNodeType.DATABASE);
        node.setProperties(EProperties.LABEL, repositoryObject.getLabel());
        try {
            if (factory.isDeleted(repositoryObject)) {
                //ignore recycle node
            } else {
                parent.getChildren().add(node);
                repositoryNodeManager.addRepositoryNode(node);
            }
        } catch (PersistenceException e1) {
            e1.printStackTrace();
        }

        DatabaseConnection metadataConnection = (DatabaseConnection) ((ConnectionItem) repositoryObject.getProperty()
                .getItem()).getConnection();
        createTables(node, repositoryObject, metadataConnection, isBuildIn);

        createQueries(node, repositoryObject, metadataConnection, isBuildIn);
    }
    

    private void createQueries(RepositoryNode node, final IRepositoryObject repObj,
            DatabaseConnection metadataConnection, boolean isBuildIn) {
        EList queryConnections = metadataConnection.getQueries();
        
        for (Iterator iter = queryConnections.iterator(); iter.hasNext();) {
            QueriesConnection queriesConnection = (QueriesConnection) iter.next();
            SqlBuilderRepositoryObject repositoryObject = new SqlBuilderRepositoryObject(repObj.getProperty()); 
            repositoryObject.setImage("Images.AppendToEditor");
            repositoryObject.setSourceName("Stored Queries");
            RepositoryNode queriesConnectionNode = new RepositoryNode(repositoryObject, node, ENodeType.REPOSITORY_ELEMENT);
            node.getChildren().add(queriesConnectionNode);
            createQuery(queriesConnectionNode, repObj, queriesConnection);
        }
    }

    private void createQuery(RepositoryNode queriesConnectionNode, IRepositoryObject repObj, QueriesConnection queriesConnection) {
        for (Iterator iter = queriesConnection.getQuery().iterator(); iter.hasNext();) {
        	Query query = (Query) iter.next();
        	SqlBuilderRepositoryObject repositoryObject = new SqlBuilderRepositoryObject(repObj.getProperty());
        	repositoryObject.setImage("Images.SqlEditorIcon");
        	repositoryObject.setSourceName(query.getLabel());
        	RepositoryNode node = new RepositoryNode(repositoryObject, queriesConnectionNode, ENodeType.REPOSITORY_ELEMENT);
        	queriesConnectionNode.getChildren().add(node);
        }
    }


    /**
     * DOC tguiu Comment method "createTables".
     * 
     * @param node
     * @param iMetadataConnection
     * @param metadataConnection
     */
    private void createTables(RepositoryNode node, final IRepositoryObject repObj,
            Connection metadataConnection, boolean isBuildIn) {
        for (Object currentTable : metadataConnection.getTables()) {
            org.talend.core.model.metadata.builder.connection.MetadataTable metadataTable 
            = (org.talend.core.model.metadata.builder.connection.MetadataTable) currentTable;
            RepositoryNode tableNode = createMetatable(node, repObj, metadataTable, isBuildIn);
            if (TableHelper.isDeleted(metadataTable)) {
                //ignore recycle node
            } else {
                node.getChildren().add(tableNode);
            }
            
            //create columns
            createColumns(tableNode, repObj, currentTable, isBuildIn);
        }
    }

    private void createColumns(RepositoryNode tableNode, IRepositoryObject repObj, Object currentTable, boolean isBuildIn) {
        for (Object currentColumn : ((MetadataTable) currentTable).getColumns()) {
            MetadataColumn metadataColumn = (MetadataColumn) currentColumn;
            RepositoryNode columnNode = createMetacolumn(tableNode, repObj, metadataColumn, isBuildIn);

            tableNode.getChildren().add(columnNode);
        }
    }

    private RepositoryNode createMetacolumn(RepositoryNode tableNode, IRepositoryObject repObj, 
    		MetadataColumn metadataColumn, boolean isBuildIn) {
    	MetadataColumnRepositoryObject modelObj = new MetadataColumnRepositoryObject(repObj, metadataColumn);
        modelObj.setRepositoryName(metadataColumn.getLabel());
        //statusCode use for source table name
        modelObj.setSourceName(metadataColumn.getOriginalField());
        //purpose use for Image text.
        if (metadataColumn.isSynchronised()) {
            modelObj.setImage("Images.RefreshIcon");
        } else {
            modelObj.setImage("Images.ColumnNodeIcon");
        }
        //description use for color.
        if (modelObj.getRepositoryName() == null || modelObj.getRepositoryName().trim().equals("")) {
        	modelObj.setColor("COLOR_GRAY");
        }
        if (modelObj.getColumn().isDivergency() && !isBuildIn) {
        	modelObj.setColor("COLOR_RED");
        }
        modelObj.setBuildIn(isBuildIn);
        
        RepositoryNode columnNode = new RepositoryNode(modelObj, tableNode, ENodeType.REPOSITORY_ELEMENT);
        columnNode.setProperties(EProperties.LABEL, metadataColumn.getLabel());
        columnNode.setProperties(EProperties.CONTENT_TYPE, RepositoryNodeType.COLUMN);
        return columnNode;
    }

    /**
     * DOC tguiu Comment method "createMetatable".
     * 
     * @param node
     * @param iMetadataFileDelimited
     * @param table
     * @return
     */
    private RepositoryNode createMetatable(RepositoryNode node, IRepositoryObject repObj,
            final org.talend.core.model.metadata.builder.connection.MetadataTable table, boolean isBuildIn) {
    	MetadataTableRepositoryObject modelObj = new MetadataTableRepositoryObject(repObj, table);
        modelObj.setRepositoryName(table.getLabel());
        //statusCode use for source table name
        modelObj.setSourceName(table.getSourceName());
        //purpose use for Image text.
        modelObj.setImage("Images.TableNodeIcon");
        //description use for color.
        if (modelObj.getRepositoryName() == null || modelObj.getRepositoryName().trim().equals("")) {
        	modelObj.setColor("COLOR_GRAY");
        }
        if (modelObj.getTable().isDivergency() && !isBuildIn) {
        	modelObj.setColor("COLOR_RED");
        }
        modelObj.setBuildIn(isBuildIn);
        
        RepositoryNode tableNode = new RepositoryNode(modelObj, node, ENodeType.REPOSITORY_ELEMENT);
        tableNode.setProperties(EProperties.LABEL, table.getLabel());
        tableNode.setProperties(EProperties.CONTENT_TYPE, RepositoryNodeType.TABLE);
        return tableNode;
    }
    
    /**
     */
    public static class MetadataConnectionRepositoryObject extends SqlBuilderRepositoryObject {

        private IRepositoryObject repObj;

        public MetadataConnectionRepositoryObject(IRepositoryObject repObj) {
        	super(repObj.getProperty());
            this.repObj = repObj;
        }

        public Property getProperty() {
            return repObj.getProperty();
        }

        public String getLabel() {
            return repObj.getLabel();
        }
    }
    /**
     */
    public static class MetadataTableRepositoryObject extends SqlBuilderRepositoryObject {

        private IRepositoryObject repObj;

        private org.talend.core.model.metadata.builder.connection.MetadataTable table;

        public MetadataTableRepositoryObject(IRepositoryObject repObj,
                org.talend.core.model.metadata.builder.connection.MetadataTable table) {
        	super(repObj.getProperty());
        	this.repObj = repObj;
            this.table = table;
        }

        public Property getProperty() {
            return repObj.getProperty();
        }
        public org.talend.core.model.metadata.builder.connection.MetadataTable getTable() {
            return this.table;
        }
    }
    

    /**
     */
    public static class MetadataColumnRepositoryObject extends SqlBuilderRepositoryObject {

        private IRepositoryObject repObj;

        private MetadataColumn column;

        public MetadataColumnRepositoryObject(IRepositoryObject repObj, MetadataColumn column) {
        	super(repObj.getProperty());
            this.repObj = repObj;
            this.column = column;
        }
        public Property getProperty() {
            return repObj.getProperty();
        }
        public MetadataColumn getColumn() {
            return this.column;
        }
    }
    
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        
    }

    public Color getBackground(Object element, int columnIndex) {
        RepositoryNode repositoryNode = (RepositoryNode) element;
        return colors.get(((SqlBuilderRepositoryObject) repositoryNode.getObject()).getColor());
    }

    public Color getForeground(Object element, int columnIndex) {
        return null;
    }

    
}
