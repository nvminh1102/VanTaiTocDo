/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.Parameter;
import com.osp.model.VtPartner;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Vu Van Lich
 */
public interface ParameterDAO {
    
    public Optional<PagingResult> page(PagingResult page, String paramKey);

    public boolean addParam(Parameter paramItem);

    public boolean isExits(Parameter paramItem);

    public boolean editParam(Parameter paramItem);

    public Parameter getParamById(Long id);

    public boolean deleteParam(Parameter parameterDel);
    
}
