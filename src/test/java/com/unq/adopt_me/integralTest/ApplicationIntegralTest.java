package com.unq.adopt_me.integralTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unq.adopt_me.AdoptMeApplicationTests;
import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dao.AdoptionDao;
import com.unq.adopt_me.dao.PetDao;
import com.unq.adopt_me.dao.UserDao;
import com.unq.adopt_me.dto.adoption.AdoptionInteractionRequest;
import com.unq.adopt_me.dto.adoption.AdoptionRequest;
import com.unq.adopt_me.dto.adoption.AdoptionResponse;
import com.unq.adopt_me.dto.security.LoginDto;
import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.pet.Pet;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.factory.AdoptionFactory;
import com.unq.adopt_me.factory.PetFactory;
import com.unq.adopt_me.factory.UserFactory;
import com.unq.adopt_me.util.PetAge;
import com.unq.adopt_me.util.PetGender;
import com.unq.adopt_me.util.PetSize;
import com.unq.adopt_me.util.PetType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.util.*;

import static com.unq.adopt_me.integralTest.AdoptionIntegralTest.SEARCH_PATH;
import static com.unq.adopt_me.integralTest.AdoptionIntegralTest.SUCCESS_SEARCH;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationIntegralTest extends AdoptMeApplicationTests {


    private final String APPLICATION_URL = HTTP_LOCALHOST + "8085" + "/application";
    private final String ADOPTION_URL = HTTP_LOCALHOST + "8085" + "/adoption";


    public static final String USER_PATH = "/user";
    public static final String ADOPTION_PATH = "/adoption/";
    public LoginDto loginUser;
    public static final String SUCCESS_CREATION = "Application successfully created";
    public static final String SUCCESS_SEARCH_APPLICATION = "Applications successfully obtained";
    public static final String ERROR_APPLYING_TWICE_CREATION = "There was a problem applying to the adoption, you can't apply for an adoption twice";
    public static final String SUCCESS_BLACKLIST_MESSAGE = "Application successfully blacklisted";

    ObjectMapper mapper = new ObjectMapper();
    public Long idFromToken;
    public List<Adoption> randomAdoptionList;
    public User user;


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AdoptionDao adoptionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PetDao petDao;

    @Autowired
    private AuthHelper authHelper;

    @BeforeEach
    @Transactional
    void setUp() throws IOException {
        Optional<User> userLogin = userDao.findByEmail("test.user@gmail.com");
        loginUser = new LoginDto(userLogin.get().getEmail(), "Asda1234");
        authHelper.setData(HTTP_LOCALHOST, port, restTemplate);
        idFromToken = authHelper.getUserIdFromToken(authHelper.getToken(loginUser));
        randomAdoptionList = (List<Adoption>) httpCall(ADOPTION_URL + SEARCH_PATH, HttpMethod.GET, null ).getBody().getData();
    }

    private ResponseEntity<GeneralResponse> httpCall(String url, HttpMethod httpMethod, Object objectBody) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authHelper.getToken(loginUser));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(objectBody, headers);
        return restTemplate.exchange(url, httpMethod, entity, GeneralResponse.class);
    }

    private AdoptionInteractionRequest getAdoptionInteractionRequest() {
        AdoptionInteractionRequest request = new AdoptionInteractionRequest();
        AdoptionResponse randomAdoption = mapper.convertValue(randomAdoptionList.get(0), AdoptionResponse.class);
        request.setAdoptionId(randomAdoption.getId().toString());
        return request;
    }

    @Test
    @DirtiesContext
    void apply_application_on_adoption() throws IOException {
        AdoptionInteractionRequest request = getAdoptionInteractionRequest();
        ResponseEntity<GeneralResponse> response = httpCall(APPLICATION_URL, HttpMethod.POST, request);
        assertEquals(SUCCESS_CREATION, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DirtiesContext
    void apply_application_on_adoption_twice_return_error() throws IOException {
        AdoptionInteractionRequest request = getAdoptionInteractionRequest();
        httpCall(APPLICATION_URL, HttpMethod.POST, request);
        ResponseEntity<GeneralResponse> response = httpCall(APPLICATION_URL, HttpMethod.POST, request);
        assertEquals(ERROR_APPLYING_TWICE_CREATION, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    @DirtiesContext
    void blacklist_adoption_gets_into_user_blacklist() throws IOException {
        AdoptionInteractionRequest request = getAdoptionInteractionRequest();
        ResponseEntity<GeneralResponse> response = httpCall(APPLICATION_URL, HttpMethod.PUT, request);
        assertEquals(SUCCESS_BLACKLIST_MESSAGE, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        user = userDao.findById(idFromToken).get();
        assertEquals(user.getBlackList().size(), 1);
    }

    @Test
    @DirtiesContext
    void blacklisted_adoption_cant_be_searched() throws IOException {
        AdoptionInteractionRequest request = getAdoptionInteractionRequest();
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL + SEARCH_PATH, HttpMethod.GET, null );
        assertEquals(SUCCESS_SEARCH, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Adoption> responseList = (List<Adoption>) response.getBody().getData();
        assertEquals(3, responseList.size()); //Primer llamado nos devuelve 3
        httpCall(APPLICATION_URL, HttpMethod.PUT, request); // Blacklisteamos la adopción
        ResponseEntity<GeneralResponse> responseSecondCall = httpCall(ADOPTION_URL + SEARCH_PATH, HttpMethod.GET, null );
        assertEquals(SUCCESS_SEARCH, Objects.requireNonNull(responseSecondCall.getBody()).getMessage());
        assertEquals(HttpStatus.OK, responseSecondCall.getStatusCode());
        List<Adoption> responseListSecondCall = (List<Adoption>) responseSecondCall.getBody().getData();
        assertEquals(2, responseListSecondCall.size()); //Segundo llamado nos devuelve 2
    }

    @Test
    @DirtiesContext
    void search_application_by_user_id() throws IOException {
        AdoptionInteractionRequest request = getAdoptionInteractionRequest();
        ResponseEntity<GeneralResponse> response = httpCall(APPLICATION_URL, HttpMethod.POST, request);
        assertEquals(SUCCESS_CREATION, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ResponseEntity<GeneralResponse> responseSearch = httpCall(APPLICATION_URL + USER_PATH, HttpMethod.GET, request);
        assertEquals(SUCCESS_SEARCH_APPLICATION, Objects.requireNonNull(responseSearch.getBody()).getMessage());
        assertEquals(HttpStatus.OK, responseSearch.getStatusCode());
    }

    @Test
    @DirtiesContext
    void search_application_by_adopter_id() throws IOException {
        AdoptionInteractionRequest request = getAdoptionInteractionRequest();
        ResponseEntity<GeneralResponse> response = httpCall(APPLICATION_URL, HttpMethod.POST, request);
        assertEquals(SUCCESS_CREATION, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ResponseEntity<GeneralResponse> responseSearch = httpCall(APPLICATION_URL + ADOPTION_PATH + request.getAdoptionId(), HttpMethod.GET, request);
        assertEquals(SUCCESS_SEARCH_APPLICATION, Objects.requireNonNull(responseSearch.getBody()).getMessage());
        assertEquals(HttpStatus.OK, responseSearch.getStatusCode());
        List<AdoptionResponse> responseListSecondCall = (List<AdoptionResponse>) responseSearch.getBody().getData();
        assertEquals(1, responseListSecondCall.size());
    }

}
