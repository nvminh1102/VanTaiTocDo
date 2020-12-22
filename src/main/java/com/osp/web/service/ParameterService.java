/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.web.service;

import com.osp.common.PagingResult;
import com.osp.model.Parameter;
import java.util.Optional;

/**
 *
 * @author Vu Van Lich
 */
public interface ParameterService {
    public Optional<PagingResult> page(PagingResult page, String paramKey);

    public boolean addParameter(Parameter paramItem, String ipClient);

    public boolean isExits(Parameter paramItem);

    public boolean editParameter(Parameter paramItem, String ipClient);

    public Parameter getParamById(Long id);

    public boolean deleteParameter(Parameter parameterDel, String ipClient);
}
