/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.web.service;

import com.osp.common.Constants;
import com.osp.common.PagingResult;
import com.osp.model.Parameter;
import com.osp.model.User;
import com.osp.web.dao.LogAccessDAO;
import com.osp.web.dao.ParameterDAO;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vu Van Lich
 */
@Service
public class ParameterServiceImpl implements ParameterService {

    @Autowired
    ParameterDAO parameterDAO;
    @Autowired
    LogAccessDAO logAccessDao;

    @Override
    public Optional<PagingResult> page(PagingResult page, String paramKey) {

        return parameterDAO.page(page, paramKey);
    }

    @Override
    @Transactional(value = "transactionManager")
    public boolean addParameter(Parameter paramItem, String ipClient) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            paramItem.setGenDate(new Date());
            paramItem.setLastUpdate(new Date());
            paramItem.setUserId(user.getId());
            paramItem.setLastUserId(user.getId());
            paramItem.setKey(paramItem.getKey().trim());
            boolean isUpdate = parameterDAO.addParam(paramItem);
            if (isUpdate) {
                logAccessDao.addLog("Thêm mới tham số hệ thống " + paramItem.getKey(),Constants.Log.system,  ipClient);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isExits(Parameter paramItem) {
        return parameterDAO.isExits(paramItem);
    }

    @Override
    @Transactional(value = "transactionManager")
    public boolean editParameter(Parameter paramItem, String ipClient) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            paramItem.setLastUpdate(new Date());
            paramItem.setLastUserId(user.getId());
            paramItem.setKey(paramItem.getKey().trim());
            paramItem.setValue(paramItem.getValue().trim());

            boolean isUpdate = parameterDAO.editParam(paramItem);
            if (isUpdate) {
                logAccessDao.addLog("Sửa thông tin tham số hệ thống " + paramItem.getKey(),Constants.Log.system,  ipClient);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Parameter getParamById(Long id) {
        return parameterDAO.getParamById(id);
    }

    @Override
    public boolean deleteParameter(Parameter parameterDel, String ipClient) {
        try {
            boolean isUpdate = parameterDAO.deleteParam(parameterDel);
            if (isUpdate) {
                logAccessDao.addLog("Xóa tham số hệ thống " + parameterDel.getKey(), Constants.Log.system, ipClient);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
