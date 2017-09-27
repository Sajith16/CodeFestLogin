package org.codefest.app.service.impl;

import org.codefest.app.service.LoginFestService;
import org.codefest.app.domain.LoginFest;
import org.codefest.app.repository.LoginFestRepository;
import org.codefest.app.service.dto.LoginFestDTO;
import org.codefest.app.service.mapper.LoginFestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing LoginFest.
 */
@Service
@Transactional
public class LoginFestServiceImpl implements LoginFestService{

    private final Logger log = LoggerFactory.getLogger(LoginFestServiceImpl.class);

    private final LoginFestRepository loginFestRepository;

    private final LoginFestMapper loginFestMapper;

    public LoginFestServiceImpl(LoginFestRepository loginFestRepository, LoginFestMapper loginFestMapper) {
        this.loginFestRepository = loginFestRepository;
        this.loginFestMapper = loginFestMapper;
    }

    /**
     * Save a loginFest.
     *
     * @param loginFestDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LoginFestDTO save(LoginFestDTO loginFestDTO) {
        log.debug("Request to save LoginFest : {}", loginFestDTO);
        LoginFest loginFest = loginFestMapper.toEntity(loginFestDTO);
        loginFest = loginFestRepository.save(loginFest);
        return loginFestMapper.toDto(loginFest);
    }

    /**
     *  Get all the loginFests.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LoginFestDTO> findAll() {
        log.debug("Request to get all LoginFests");
        return loginFestRepository.findAll().stream()
            .map(loginFestMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one loginFest by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LoginFestDTO findOne(Long id) {
        log.debug("Request to get LoginFest : {}", id);
        LoginFest loginFest = loginFestRepository.findOne(id);
        return loginFestMapper.toDto(loginFest);
    }

    /**
     *  Delete the  loginFest by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LoginFest : {}", id);
        loginFestRepository.delete(id);
    }
}
