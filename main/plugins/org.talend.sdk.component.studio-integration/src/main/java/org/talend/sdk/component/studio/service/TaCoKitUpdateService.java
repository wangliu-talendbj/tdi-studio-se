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
package org.talend.sdk.component.studio.service;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.sdk.component.studio.update.TaCoKitCar;
import org.talend.sdk.component.studio.update.TaCoKitCarFeature;
import org.talend.updates.runtime.model.ITaCoKitCarFeature;
import org.talend.updates.runtime.service.ITaCoKitUpdateService;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class TaCoKitUpdateService implements ITaCoKitUpdateService {

    @Override
    public ITaCoKitCarFeature generateExtraFeature(File file, IProgressMonitor monitor) throws Exception {
        TaCoKitCar car = new TaCoKitCar(file);
        return new TaCoKitCarFeature(car);
    }

    @Override
    public boolean installCars(File carFolder, IProgressMonitor monitor) throws Exception {
        if (carFolder.exists() && carFolder.isDirectory()) {
            final File[] listFiles = carFolder.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                monitor.beginTask("Installing cars...", listFiles.length);

                List<ITaCoKitCarFeature> carFeatures = new LinkedList<>();
                for (File carFile : listFiles) {
                    try {
                        ITaCoKitCarFeature carFeature = generateExtraFeature(carFile, monitor);
                        if (carFeature != null) {
                            carFeatures.add(carFeature);
                        }
                    } catch (Exception e) {
                        ExceptionHandler.process(e);
                    }
                }

                Collections.sort(carFeatures);

                for (ITaCoKitCarFeature carFeature : carFeatures) {
                    try {
                        if (!carFeature.isInstalled(monitor)) {
                            IStatus installStatus = carFeature.install(monitor, Collections.EMPTY_LIST);
                        }
                    } catch (Exception e) {
                        ExceptionHandler.process(e);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isCar(File file, IProgressMonitor monitor) throws Exception {
        return TaCoKitCar.isCar(file);
    }
}
