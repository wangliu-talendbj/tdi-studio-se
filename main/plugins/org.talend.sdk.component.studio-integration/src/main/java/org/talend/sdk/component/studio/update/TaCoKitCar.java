// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.sdk.component.studio.update;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.lang.StringUtils;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.sdk.component.studio.util.TaCoKitUtil;
import org.talend.sdk.component.studio.util.TaCoKitUtil.GAV;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class TaCoKitCar implements Comparable<Object> {

    public static final String TALEND_INF_FOLDER = "TALEND-INF"; //$NON-NLS-1$

    public static final String TALEND_INF_PROPERTIES_FILE = "metadata.properties"; //$NON-NLS-1$

    public static final String PROPERTY_COMPONENT_COORDINATES = "component_coordinates"; //$NON-NLS-1$

    public static final String PROPERTY_CAR_VERSION = "version"; //$NON-NLS-1$

    public static final String PROPERTY_DATE = "date"; //$NON-NLS-1$

    private File carFile;

    private List<GAV> components;

    private String dateString;

    private String carVersion;

    private String name;

    private String description;

    public TaCoKitCar(File carFile) throws Exception {
        this.carFile = carFile;
        load();
    }

    private void load() throws Exception {
        Properties props = loadTalendInfProperties(getCarFile());
        if (props == null) {
            throw new Exception("Can't find " + TALEND_INF_FOLDER + "/" + TALEND_INF_PROPERTIES_FILE + " from CAR file.");
        }
        String componentCoordinates = props.getProperty(PROPERTY_COMPONENT_COORDINATES);
        if (StringUtils.isBlank(componentCoordinates)) {
            throw new Exception("There is no " + PROPERTY_COMPONENT_COORDINATES + " configured in the CAR file.");
        }
        components = TaCoKitUtil.convert2GAV(componentCoordinates);
        setCarVersion(props.getProperty(PROPERTY_CAR_VERSION));
        setDateString(props.getProperty(PROPERTY_DATE));
        name = "TaCoKit component";
        if (0 < components.size()) {
            GAV gav = components.get(0);
            name = gav.getArtifactId();
            description = gav.getGroupId() + ":" + gav.getArtifactId() + ":" + gav.getVersion();
        }
    }

    public static boolean isCar(File file) throws Exception {
        boolean isCar = false;

        Properties props = loadTalendInfProperties(file);
        if (props != null) {
            String componentCoordinates = props.getProperty(PROPERTY_COMPONENT_COORDINATES);
            if (StringUtils.isNotBlank(componentCoordinates)) {
                isCar = true;
            }
        }

        return isCar;
    }

    public static Properties loadTalendInfProperties(File carFile) throws Exception {
        Properties props = null;
        ZipFile zipFile = null;
        InputStream propsInputStream = null;
        try {
            zipFile = new ZipFile(carFile);
            ZipEntry talendInfPropsEntry = zipFile.getEntry(TALEND_INF_FOLDER + "/" + TALEND_INF_PROPERTIES_FILE); //$NON-NLS-1$
            if (talendInfPropsEntry != null && !talendInfPropsEntry.isDirectory()) {
                propsInputStream = zipFile.getInputStream(talendInfPropsEntry);
                props = new Properties();
                props.load(propsInputStream);
            }
        } finally {
            if (propsInputStream != null) {
                try {
                    propsInputStream.close();
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            }
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            }
        }
        return props;
    }

    @Override
    public int compareTo(Object arg0) {
        if (!(arg0 instanceof TaCoKitCar)) {
            return -1;
        }
        TaCoKitCar oCar = (TaCoKitCar) arg0;
        String dateString = this.getDateString();
        if (dateString == null) {
            dateString = ""; //$NON-NLS-1$
        }
        String oDateString = oCar.getDateString();
        if (oDateString == null) {
            oDateString = ""; //$NON-NLS-1$
        }
        return dateString.compareTo(oDateString);
    }

    public File getCarFile() {
        return this.carFile;
    }

    public void setCarFile(File carFile) {
        this.carFile = carFile;
    }

    public List<GAV> getComponents() {
        return this.components;
    }

    public void setComponents(List<GAV> components) {
        this.components = components;
    }

    public String getDateString() {
        return this.dateString;
    }

    public void setDateString(String date) {
        this.dateString = date;
    }

    public String getCarVersion() {
        return this.carVersion;
    }

    public void setCarVersion(String carVersion) {
        this.carVersion = carVersion;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
