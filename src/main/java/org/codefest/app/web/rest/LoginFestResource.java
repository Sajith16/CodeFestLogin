package org.codefest.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.codefest.app.service.LoginFestService;
import org.codefest.app.web.rest.util.HeaderUtil;
import org.codefest.app.service.dto.LoginFestDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LoginFest.
 */
@RestController
@RequestMapping("/api")
public class LoginFestResource {

    private final Logger log = LoggerFactory.getLogger(LoginFestResource.class);

    private static final String ENTITY_NAME = "loginFest";

    private final LoginFestService loginFestService;

    public LoginFestResource(LoginFestService loginFestService) {
        this.loginFestService = loginFestService;
    }

    /**
     * POST  /login-fests : Create a new loginFest.
     *
     * @param loginFestDTO the loginFestDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new loginFestDTO, or with status 400 (Bad Request) if the loginFest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/login-fests")
    @Timed
    public ResponseEntity<LoginFestDTO> createLoginFest(@RequestBody LoginFestDTO loginFestDTO) throws URISyntaxException {
        log.debug("REST request to save LoginFest : {}", loginFestDTO);
        if (loginFestDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new loginFest cannot already have an ID")).body(null);
        }
        LoginFestDTO result = loginFestService.save(loginFestDTO);
        return ResponseEntity.created(new URI("/api/login-fests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /login-fests : Updates an existing loginFest.
     *
     * @param loginFestDTO the loginFestDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated loginFestDTO,
     * or with status 400 (Bad Request) if the loginFestDTO is not valid,
     * or with status 500 (Internal Server Error) if the loginFestDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/login-fests")
    @Timed
    public ResponseEntity<LoginFestDTO> updateLoginFest(@RequestBody LoginFestDTO loginFestDTO) throws URISyntaxException {
        log.debug("REST request to update LoginFest : {}", loginFestDTO);
        if (loginFestDTO.getId() == null) {
            return createLoginFest(loginFestDTO);
        }
        LoginFestDTO result = loginFestService.save(loginFestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, loginFestDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /login-fests : get all the loginFests.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of loginFests in body
     */
    @GetMapping("/login-fests")
    @Timed
    public List<LoginFestDTO> getAllLoginFests() {
        log.debug("REST request to get all LoginFests");
        return loginFestService.findAll();
        }

    /**
     * GET  /login-fests/:id : get the "id" loginFest.
     *
     * @param id the id of the loginFestDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the loginFestDTO, or with status 404 (Not Found)
     */
    @GetMapping("/login-fests/{id}")
    @Timed
    public ResponseEntity<LoginFestDTO> getLoginFest(@PathVariable Long id) {
        log.debug("REST request to get LoginFest : {}", id);
        LoginFestDTO loginFestDTO = loginFestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(loginFestDTO));
    }

    /**
     * DELETE  /login-fests/:id : delete the "id" loginFest.
     *
     * @param id the id of the loginFestDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/login-fests/{id}")
    @Timed
    public ResponseEntity<Void> deleteLoginFest(@PathVariable Long id) {
        log.debug("REST request to delete LoginFest : {}", id);
        loginFestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
