/*
 * @(#)VariableLifecycleServiceImpl.java	May 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package cn.org.bachelor.common.auth;

import cn.org.bachelor.common.auth.vo.UserVo;
import cn.org.bachelor.context.common.ValueHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Team Bachelor
 */
@Service
public class AuthValueHolderService {

    @Autowired
    private ValueHolderService valueHolderService;

    public String getClientID() {
        return clientID;
    }

    @Value("${spring.application.id:}")
    private String clientID;

    private String remoteIP;

    public ValueHolderService getValueHolderService() {
        return valueHolderService;
    }

    public void setCurrentUser(UserVo user) {
        valueHolderService.setRequestAttribute(AuthConstant.USER_KEY, user);
    }

    public UserVo getCurrentUser() {
        Object uo = valueHolderService.getRequestAttribute(AuthConstant.USER_KEY);
        UserVo user = null;
        if (uo != null) {
            user = (UserVo) uo;
            return user;
        }
        return user;
    }

    public String getRemoteIP() {
        return remoteIP;
    }

    public void setRemoteIP(String remoteIP) {
        this.remoteIP = remoteIP;
    }

    public void setValueHolderService(ValueHolderService valueHolderService) {
        this.valueHolderService = valueHolderService;
    }
}
