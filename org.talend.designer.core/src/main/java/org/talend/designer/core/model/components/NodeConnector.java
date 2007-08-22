// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2007 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License.
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
package org.talend.designer.core.model.components;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.IConnectionProperty;
import org.talend.core.model.process.INodeConnector;

/**
 * Defines connector type and name for each component. <br/>
 * 
 * $Id$
 * 
 */
public class NodeConnector implements INodeConnector {

    private EConnectionType defaultConnectionType;

    private int maxLinkOutput = -1;

    private int minLinkOutput = 0;

    private int maxLinkInput = -1;

    private int minLinkInput = 0;

    private int curLinkNbOutput = 0;

    private int curLinkNbInput = 0;

    private boolean builtIn = false;

    private String name;

    private String menuName;

    private String linkName;
    
    private String baseSchema;

    private Map<EConnectionType, IConnectionProperty> propertyMap = new HashMap<EConnectionType, IConnectionProperty>();

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.components.INodeConnector#getConnectionType()
     */
    public EConnectionType getDefaultConnectionType() {
        return this.defaultConnectionType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.components.INodeConnector#setConnectionType(org.talend.core.model.designer.EConnectionType)
     */
    public void setDefaultConnectionType(final EConnectionType defaultConnectionType) {
        this.defaultConnectionType = defaultConnectionType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.components.INodeConnector#isBuiltIn()
     */
    public boolean isBuiltIn() {
        return this.builtIn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.components.INodeConnector#setBuiltIn(boolean)
     */
    public void setBuiltIn(final boolean builtIn) {
        this.builtIn = builtIn;
    }

    public int getCurLinkNbInput() {
        return this.curLinkNbInput;
    }

    public void setCurLinkNbInput(int curLinkNbInput) {
        this.curLinkNbInput = curLinkNbInput;
    }

    public int getCurLinkNbOutput() {
        return this.curLinkNbOutput;
    }

    public void setCurLinkNbOutput(int curLinkNbOutput) {
        this.curLinkNbOutput = curLinkNbOutput;
    }

    public int getMaxLinkInput() {
        return this.maxLinkInput;
    }

    public void setMaxLinkInput(int maxLinkInput) {
        this.maxLinkInput = maxLinkInput;
    }

    public int getMaxLinkOutput() {
        return this.maxLinkOutput;
    }

    public void setMaxLinkOutput(int maxLinkOutput) {
        this.maxLinkOutput = maxLinkOutput;
    }

    public int getMinLinkInput() {
        return this.minLinkInput;
    }

    public void setMinLinkInput(int minLinkInput) {
        this.minLinkInput = minLinkInput;
    }

    public int getMinLinkOutput() {
        return this.minLinkOutput;
    }

    public void setMinLinkOutput(int minLinkOutput) {
        this.minLinkOutput = minLinkOutput;
    }

    /**
     * Getter for linkName.
     * 
     * @return the linkName
     */
    public String getLinkName() {
        return linkName;
    }

    /**
     * Sets the linkName.
     * 
     * @param linkName the linkName to set
     */
    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    /**
     * Getter for menuName.
     * 
     * @return the menuName
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * Sets the menuName.
     * 
     * @param menuName the menuName to set
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * Getter for name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public void addConnectionProperty(EConnectionType type, Color color, Integer lineStyle) {
        propertyMap.put(type, new ConnectionProperty(color, lineStyle));
    }
    
    public IConnectionProperty getConnectionProperty(EConnectionType type) {
        return propertyMap.get(type);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (name.equals(defaultConnectionType.getName())) {
            return name + ": inputs(" + curLinkNbInput + "/" + maxLinkInput + "), outputs(" + curLinkNbOutput + "/" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                    + maxLinkOutput + ")"; //$NON-NLS-1$
        }
        return name + "(" + defaultConnectionType.getName() + ")" + ": inputs(" + curLinkNbInput + "/" + maxLinkInput //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                + "), outputs(" + curLinkNbOutput + "/" + maxLinkOutput + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * DOC nrousseau NodeConnector class global comment. Detailled comment
     * <br/>
     *
     */
    private class ConnectionProperty implements IConnectionProperty {

        private Integer lineStyle;

        private Color color;

        public ConnectionProperty(Color color, Integer lineStyle) {
            super();
            this.lineStyle = lineStyle;
            this.color = color;
        }

        /**
         * Getter for color.
         * 
         * @return the color
         */
        public Color getColor() {
            return color;
        }

        /**
         * Sets the color.
         * 
         * @param color the color to set
         */
        public void setColor(Color color) {
            this.color = color;
        }

        /**
         * Getter for lineStyle.
         * 
         * @return the lineStyle
         */
        public Integer getLineStyle() {
            return lineStyle;
        }

        /**
         * Sets the lineStyle.
         * 
         * @param lineStyle the lineStyle to set
         */
        public void setLineStyle(Integer lineStyle) {
            this.lineStyle = lineStyle;
        }
    }

    
    /**
     * Getter for baseSchema.
     * @return the baseSchema
     */
    public String getBaseSchema() {
        return baseSchema;
    }

    
    /**
     * Sets the baseSchema.
     * @param baseSchema the baseSchema to set
     */
    public void setBaseSchema(String baseSchema) {
        this.baseSchema = baseSchema;
    }
}
