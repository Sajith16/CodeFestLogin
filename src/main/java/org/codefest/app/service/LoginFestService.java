package org.codefest.app.service;

import org.codefest.app.service.dto.LoginFestDTO;
import java.util.List;

/**
 * Service Interface for managing LoginFest.
 */
public interface LoginFestService {

    /**
     * Save a loginFest.
     *
     * @param loginFestDTO the entity to save
     * @return the persisted entity
     */
    LoginFestDTO save(LoginFestDTO loginFestDTO);

    /**
     *  Get all the loginFests.
     *
     *  @return the list of entities
     */
    List<LoginFestDTO> findAll();

    /**
     *  Get the "id" loginFest.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LoginFestDTO findOne(Long id);

    /**
     *  Delete the "id" loginFest.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
