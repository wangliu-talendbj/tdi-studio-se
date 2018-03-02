/**
 * Copyright (C) 2006-2018 Talend Inc. - www.talend.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.talend.sdk.component.studio.metadata.node;

import org.eclipse.swt.graphics.Image;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.repository.model.IRepositoryNode;
import org.talend.repository.model.RepositoryNode;
import org.talend.sdk.component.server.front.model.ConfigTypeNode;
import org.talend.sdk.component.studio.util.TaCoKitUtil;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public abstract class AbsTaCoKitRepositoryNode extends RepositoryNode implements ITaCoKitRepositoryNode {

    private ITaCoKitRepositoryNode parentTaCoKitNode;

    private ConfigTypeNode configTypeNode;

    private Image image;

    public AbsTaCoKitRepositoryNode(final IRepositoryViewObject repViewObject, final RepositoryNode parent,
            final ITaCoKitRepositoryNode parentTaCoKitNode, final String label, final ConfigTypeNode configTypeNode)
            throws Exception {
        super(repViewObject, parent, IRepositoryNode.ENodeType.SYSTEM_FOLDER);
        this.parentTaCoKitNode = parentTaCoKitNode;
        this.configTypeNode = configTypeNode;
        this.setProperties(EProperties.LABEL, label);
        this.setProperties(EProperties.CONTENT_TYPE, TaCoKitUtil.getOrCreateERepositoryObjectType(configTypeNode));
    }

    public void setContentType(final ERepositoryObjectType contentType) {
        this.setProperties(EProperties.CONTENT_TYPE, contentType);
    }

    @Override
    public boolean isLeafNode() {
        return false;
    }

    @Override
    public boolean isFolderNode() {
        return false;
    }

    @Override
    public boolean isFamilyNode() {
        return false;
    }

    @Override
    public boolean isConfigNode() {
        return false;
    }

    @Override
    public ITaCoKitRepositoryNode getParentTaCoKitNode() {
        return this.parentTaCoKitNode;
    }

    @Override
    public ConfigTypeNode getConfigTypeNode() {
        return this.configTypeNode;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    public void setParentTaCoKitNode(final ITaCoKitRepositoryNode parentTaCoKitNode) {
        this.parentTaCoKitNode = parentTaCoKitNode;
    }

    public void setConfigTypeNode(final ConfigTypeNode configTypeNode) {
        this.configTypeNode = configTypeNode;
    }

    public void setImage(final Image image) {
        this.image = image;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AbsTaCoKitRepositoryNode))
            return false;
        final AbsTaCoKitRepositoryNode other = (AbsTaCoKitRepositoryNode) o;
        if (!other.canEqual(this))
            return false;
        if (!super.equals(o))
            return false;
        final Object this$configTypeNode = this.getConfigTypeNode();
        final Object other$configTypeNode = other.getConfigTypeNode();
        if (this$configTypeNode == null ? other$configTypeNode != null : !this$configTypeNode.equals(other$configTypeNode))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AbsTaCoKitRepositoryNode;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $configTypeNode = this.getConfigTypeNode();
        result = result * PRIME + ($configTypeNode == null ? 43 : $configTypeNode.hashCode());
        return result;
    }
}
