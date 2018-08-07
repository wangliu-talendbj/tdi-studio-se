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
import java.net.URI;
import java.util.EnumSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.sdk.component.studio.util.TaCoKitConst;
import org.talend.sdk.component.studio.util.TaCoKitUtil;
import org.talend.sdk.component.studio.util.TaCoKitUtil.GAV;
import org.talend.updates.runtime.model.ITaCoKitCarFeature;
import org.talend.updates.runtime.model.UpdateSiteLocationType;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class TaCoKitCarFeature implements ITaCoKitCarFeature {

    private TaCoKitCar car;

    public TaCoKitCarFeature(TaCoKitCar car) throws Exception {
        this.car = car;
    }

    @Override
    public boolean isInstalled(IProgressMonitor progress) throws Exception {
        boolean isInstalled = false;
        List<GAV> installedGavs = TaCoKitUtil.getInstalledComponents(progress);
        if (installedGavs != null && !installedGavs.isEmpty()) {
            List<GAV> newComponents = getCar().getComponents();
            if (newComponents != null) {
                isInstalled = true;
                for (GAV newComponent : newComponents) {
                    // 1. as long as there is one component not be installed, we consider the car is not
                    // installed<br/>
                    // 2. if there is a newer version component installed, we consider the component is installed.
                    for (GAV installedGav : installedGavs) {
                        if (!isInstalled) {
                            break;
                        }
                        try {
                            if (StringUtils.equals(installedGav.getGroupId() + ":" + installedGav.getArtifactId(),
                                    newComponent.getGroupId() + ":" + newComponent.getArtifactId())) {
                                String installedVersionString = installedGav.getVersion();
                                String newVersionString = newComponent.getVersion();
                                String[] installedVersion = installedVersionString.split("\\.");
                                String[] newVersion = newVersionString.split("\\.");
                                for (int i = 0; i < 3; ++i) {
                                    int iVersion = Integer.valueOf(installedVersion[i]);
                                    int nVersion = Integer.valueOf(newVersion[i]);
                                    if (iVersion < nVersion) {
                                        isInstalled = false;
                                        break;
                                    }
                                }
                            }
                        } catch (Exception e) {
                            ExceptionHandler.process(e);
                        }
                    }
                }
            }
        }
        return isInstalled;
    }

    @Override
    public IStatus install(IProgressMonitor progress, List<URI> allRepoUris) throws Exception {
        IStatus status = null;
        try {
            if (isInstalled(progress)) {
                status = new Status(IStatus.WARNING, TaCoKitConst.BUNDLE_ID,
                        getName() + " has been installed, not need to install again.");
            } else {
                boolean succeed = install(progress);
                if (succeed) {
                    status = new Status(IStatus.OK, TaCoKitConst.BUNDLE_ID, getName() + " is installed successfully!");
                } else {
                    status = new Status(IStatus.ERROR, TaCoKitConst.BUNDLE_ID, "Failed to install " + getName());
                }
            }
        } catch (InterruptedException e) {
            status = new Status(IStatus.CANCEL, TaCoKitConst.BUNDLE_ID, "User cancelled to install " + getName());
        } catch (Exception e) {
            status = new Status(IStatus.ERROR, TaCoKitConst.BUNDLE_ID, "Failed to install " + getName(), e);
        }
        return status;
    }

    public boolean install(IProgressMonitor progress) throws Exception {
        TaCoKitCar tckCar = getCar();
        String[] carCmd = new String[] { "java", "-jar", "\"" + tckCar.getCarFile().getAbsolutePath() + "\"", "studio-deploy", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
                "\"" + Platform.getInstallLocation().getURL().toString() + "\"" }; //$NON-NLS-1$ //$NON-NLS-2$
        Process exec = Runtime.getRuntime().exec(carCmd);
        while (exec.isAlive()) {
            Thread.sleep(100);
            if (progress.isCanceled()) {
                break;
            }
        }
        if (progress.isCanceled()) {
            throw new InterruptedException();
        }
        return exec.exitValue() == 0;
    }

    @Override
    public int compareTo(Object o) {
        TaCoKitCar sTckCar = getCar();
        TaCoKitCar oTckCar = null;
        if (o instanceof TaCoKitCarFeature) {
            oTckCar = ((TaCoKitCarFeature) o).getCar();
        } else if (o instanceof TaCoKitCar) {
            oTckCar = (TaCoKitCar) o;
        } else {
            if (o == null) {
                return 1;
            } else {
                return -1;
            }
        }
        if (oTckCar == null || sTckCar == null) {
            if (oTckCar != null) {
                return -1;
            } else if (sTckCar != null) {
                return 1;
            } else {
                return 0;
            }
        }
        return sTckCar.compareTo(oTckCar);
    }

    @Override
    public EnumSet<UpdateSiteLocationType> getUpdateSiteCompatibleTypes() {
        return EnumSet.allOf(UpdateSiteLocationType.class);
    }

    @Override
    public boolean mustBeInstalled() {
        return false;
    }

    @Override
    public boolean needRestart() {
        return false;
    }

    @Override
    public String getName() {
        return getCar().getName();
    }

    @Override
    public String getDescription() {
        return getCar().getDescription();
    }

    @Override
    public String getVersion() {
        return getCar().getCarVersion();
    }

    @Override
    public File getCarFile() {
        return getCar().getCarFile();
    }

    private TaCoKitCar getCar() {
        return this.car;
    }

}
