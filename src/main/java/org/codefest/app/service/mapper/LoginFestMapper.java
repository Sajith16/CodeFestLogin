package org.codefest.app.service.mapper;

import org.codefest.app.domain.*;
import org.codefest.app.service.dto.LoginFestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LoginFest and its DTO LoginFestDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LoginFestMapper extends EntityMapper <LoginFestDTO, LoginFest> {
    
    
    default LoginFest fromId(Long id) {
        if (id == null) {
            return null;
        }
        LoginFest loginFest = new LoginFest();
        loginFest.setId(id);
        return loginFest;
    }
}
