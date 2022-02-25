package com.flier.core.interfaces.facade;

import com.flier.core.application.service.RelolverService;
import com.flier.core.application.service.impl.RelolverServiceImpl;

/**
 * @PROJECT_NAME: relolver
 * @DESCRIPTION:
 * @AUTHOR: lx
 * @DATE: 2022/2/18 13:26
 */
public class RelolverControl {

    private static final RelolverService relolverService;

    static {
        relolverService = new RelolverServiceImpl();
    }

    public static String render(String resource, String templateName, String suffix, String jsonObj) {
        return relolverService.render(resource, templateName, suffix, jsonObj);
    }

}
