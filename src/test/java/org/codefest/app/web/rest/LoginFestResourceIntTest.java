package org.codefest.app.web.rest;

import org.codefest.app.CodeFestApp;

import org.codefest.app.domain.LoginFest;
import org.codefest.app.repository.LoginFestRepository;
import org.codefest.app.service.LoginFestService;
import org.codefest.app.service.dto.LoginFestDTO;
import org.codefest.app.service.mapper.LoginFestMapper;
import org.codefest.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LoginFestResource REST controller.
 *
 * @see LoginFestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CodeFestApp.class)
public class LoginFestResourceIntTest {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_RIGHTS = "AAAAAAAAAA";
    private static final String UPDATED_RIGHTS = "BBBBBBBBBB";

    @Autowired
    private LoginFestRepository loginFestRepository;

    @Autowired
    private LoginFestMapper loginFestMapper;

    @Autowired
    private LoginFestService loginFestService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLoginFestMockMvc;

    private LoginFest loginFest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoginFestResource loginFestResource = new LoginFestResource(loginFestService);
        this.restLoginFestMockMvc = MockMvcBuilders.standaloneSetup(loginFestResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoginFest createEntity(EntityManager em) {
        LoginFest loginFest = new LoginFest()
            .userName(DEFAULT_USER_NAME)
            .password(DEFAULT_PASSWORD)
            .rights(DEFAULT_RIGHTS);
        return loginFest;
    }

    @Before
    public void initTest() {
        loginFest = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoginFest() throws Exception {
        int databaseSizeBeforeCreate = loginFestRepository.findAll().size();

        // Create the LoginFest
        LoginFestDTO loginFestDTO = loginFestMapper.toDto(loginFest);
        restLoginFestMockMvc.perform(post("/api/login-fests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginFestDTO)))
            .andExpect(status().isCreated());

        // Validate the LoginFest in the database
        List<LoginFest> loginFestList = loginFestRepository.findAll();
        assertThat(loginFestList).hasSize(databaseSizeBeforeCreate + 1);
        LoginFest testLoginFest = loginFestList.get(loginFestList.size() - 1);
        assertThat(testLoginFest.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testLoginFest.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testLoginFest.getRights()).isEqualTo(DEFAULT_RIGHTS);
    }

    @Test
    @Transactional
    public void createLoginFestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loginFestRepository.findAll().size();

        // Create the LoginFest with an existing ID
        loginFest.setId(1L);
        LoginFestDTO loginFestDTO = loginFestMapper.toDto(loginFest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoginFestMockMvc.perform(post("/api/login-fests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginFestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LoginFest in the database
        List<LoginFest> loginFestList = loginFestRepository.findAll();
        assertThat(loginFestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLoginFests() throws Exception {
        // Initialize the database
        loginFestRepository.saveAndFlush(loginFest);

        // Get all the loginFestList
        restLoginFestMockMvc.perform(get("/api/login-fests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loginFest.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].rights").value(hasItem(DEFAULT_RIGHTS.toString())));
    }

    @Test
    @Transactional
    public void getLoginFest() throws Exception {
        // Initialize the database
        loginFestRepository.saveAndFlush(loginFest);

        // Get the loginFest
        restLoginFestMockMvc.perform(get("/api/login-fests/{id}", loginFest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loginFest.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.rights").value(DEFAULT_RIGHTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLoginFest() throws Exception {
        // Get the loginFest
        restLoginFestMockMvc.perform(get("/api/login-fests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoginFest() throws Exception {
        // Initialize the database
        loginFestRepository.saveAndFlush(loginFest);
        int databaseSizeBeforeUpdate = loginFestRepository.findAll().size();

        // Update the loginFest
        LoginFest updatedLoginFest = loginFestRepository.findOne(loginFest.getId());
        updatedLoginFest
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .rights(UPDATED_RIGHTS);
        LoginFestDTO loginFestDTO = loginFestMapper.toDto(updatedLoginFest);

        restLoginFestMockMvc.perform(put("/api/login-fests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginFestDTO)))
            .andExpect(status().isOk());

        // Validate the LoginFest in the database
        List<LoginFest> loginFestList = loginFestRepository.findAll();
        assertThat(loginFestList).hasSize(databaseSizeBeforeUpdate);
        LoginFest testLoginFest = loginFestList.get(loginFestList.size() - 1);
        assertThat(testLoginFest.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testLoginFest.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testLoginFest.getRights()).isEqualTo(UPDATED_RIGHTS);
    }

    @Test
    @Transactional
    public void updateNonExistingLoginFest() throws Exception {
        int databaseSizeBeforeUpdate = loginFestRepository.findAll().size();

        // Create the LoginFest
        LoginFestDTO loginFestDTO = loginFestMapper.toDto(loginFest);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLoginFestMockMvc.perform(put("/api/login-fests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginFestDTO)))
            .andExpect(status().isCreated());

        // Validate the LoginFest in the database
        List<LoginFest> loginFestList = loginFestRepository.findAll();
        assertThat(loginFestList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLoginFest() throws Exception {
        // Initialize the database
        loginFestRepository.saveAndFlush(loginFest);
        int databaseSizeBeforeDelete = loginFestRepository.findAll().size();

        // Get the loginFest
        restLoginFestMockMvc.perform(delete("/api/login-fests/{id}", loginFest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LoginFest> loginFestList = loginFestRepository.findAll();
        assertThat(loginFestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoginFest.class);
        LoginFest loginFest1 = new LoginFest();
        loginFest1.setId(1L);
        LoginFest loginFest2 = new LoginFest();
        loginFest2.setId(loginFest1.getId());
        assertThat(loginFest1).isEqualTo(loginFest2);
        loginFest2.setId(2L);
        assertThat(loginFest1).isNotEqualTo(loginFest2);
        loginFest1.setId(null);
        assertThat(loginFest1).isNotEqualTo(loginFest2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoginFestDTO.class);
        LoginFestDTO loginFestDTO1 = new LoginFestDTO();
        loginFestDTO1.setId(1L);
        LoginFestDTO loginFestDTO2 = new LoginFestDTO();
        assertThat(loginFestDTO1).isNotEqualTo(loginFestDTO2);
        loginFestDTO2.setId(loginFestDTO1.getId());
        assertThat(loginFestDTO1).isEqualTo(loginFestDTO2);
        loginFestDTO2.setId(2L);
        assertThat(loginFestDTO1).isNotEqualTo(loginFestDTO2);
        loginFestDTO1.setId(null);
        assertThat(loginFestDTO1).isNotEqualTo(loginFestDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(loginFestMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(loginFestMapper.fromId(null)).isNull();
    }
}
