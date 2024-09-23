package com.unq.adopt_me.integralTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unq.adopt_me.AdoptMeApplicationTests;
import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dao.AdoptionDao;
import com.unq.adopt_me.dao.UserDao;
import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.factory.AdoptionFactory;
import com.unq.adopt_me.util.PetAge;
import com.unq.adopt_me.util.PetGender;
import com.unq.adopt_me.util.PetSize;
import com.unq.adopt_me.util.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdoptionIntegralTest extends AdoptMeApplicationTests {


    private final String ADOPTION_URL = HTTP_LOCALHOST + "8085" + "/adoption";

    public static final String SEARCH_PATH = "/search";
    
    public static final String SUCCESS_SEARCH = "Adoption successfully retrieved";
    public static final String SUCCESS_CREATION = "Adoption successfully created";
    ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AdoptionDao adoptionDao;

    @Autowired
    private UserDao userDao;

    @BeforeEach
    void setUp() {
    }

    private ResponseEntity<GeneralResponse> httpCall(String url, HttpMethod httpMethod, Object objectBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(objectBody, headers);
        return restTemplate.exchange(url, httpMethod, entity, GeneralResponse.class);
    }

    @Test
    void search_all_adoptions_get_status_OK_and_4_items() {
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL + SEARCH_PATH, HttpMethod.GET, null );
        assertEquals(SUCCESS_SEARCH, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Adoption> responseList = (List<Adoption>) response.getBody().getData();
        assertEquals(4, responseList.size());
    }

    @Test
    void search_adoptions_with_type_filter_get_status_OK_and_we_expect_1() {
        String dogTypeFilter = "?type="+ PetType.CAT;
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL + SEARCH_PATH +dogTypeFilter, HttpMethod.GET, null );
        assertEquals(SUCCESS_SEARCH, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Adoption> responseList = (List<Adoption>) response.getBody().getData();
        assertEquals(3, responseList.size());
    }

    @Test
    void search_adoptions_with_gender_filter_get_status_OK_and_we_expect_1() {
        String femaleFilter = "?gender="+ PetGender.MALE;
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL + SEARCH_PATH + femaleFilter, HttpMethod.GET, null );
        assertEquals(SUCCESS_SEARCH, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Adoption> responseList = (List<Adoption>) response.getBody().getData();
        assertEquals(1, responseList.size());
    }

    @Test
    void search_adoptions_with_age_filter_get_status_OK_and_we_expect_1() {
        String puppyFilter = "?age="+ PetAge.PUPPY;
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL + SEARCH_PATH + puppyFilter, HttpMethod.GET, null );
        assertEquals(SUCCESS_SEARCH, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Adoption> responseList = (List<Adoption>) response.getBody().getData();
        assertEquals(1, responseList.size());
    }

    @Test
    void search_adoptions_with_size_filter_get_status_OK_and_we_expect_1() {
        String sizeFilter = "?size="+ PetSize.SMALL;
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL + SEARCH_PATH + sizeFilter, HttpMethod.GET, null );
        assertEquals(SUCCESS_SEARCH, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Adoption> responseList = (List<Adoption>) response.getBody().getData();
        assertEquals(1, responseList.size());
    }

    @Test
    void create_adoptions_status_OK_and_we_expect_same_object() {
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL, HttpMethod.POST, AdoptionFactory.anyAdoptionRequest());
        assertEquals(SUCCESS_CREATION, Objects.requireNonNull(response.getBody()).getMessage());
        Adoption adoption = mapper.convertValue(response.getBody().getData(), Adoption.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        User owner = userDao.findById(Long.valueOf(AdoptionFactory.anyAdoptionRequest().getUserId())).get();
        assertEquals(owner.getName(), adoption.getOwner().getName());
        assertEquals("Boni", adoption.getPet().getName());
    }

    @Test
    void create_adoptions_status_BAD_REQUEST_and_we_expect_same_object() {
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL, HttpMethod.POST, AdoptionFactory.adoptionRequestWithWrongParameter());
        assertEquals("La imagen es obligatoria, la edad debe ser mayor o igual a 0, el género es obligatorio, el tamaño es obligatorio, la descripción es obligatoria, el tipo es obligatorio".length(), (Objects.requireNonNull(response.getBody()).getMessage().length()));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }



}
