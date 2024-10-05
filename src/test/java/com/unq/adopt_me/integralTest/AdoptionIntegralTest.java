package com.unq.adopt_me.integralTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unq.adopt_me.AdoptMeApplicationTests;
import com.unq.adopt_me.common.GeneralResponse;
import com.unq.adopt_me.dao.AdoptionDao;
import com.unq.adopt_me.dao.UserDao;
import com.unq.adopt_me.dto.security.LoginDto;
import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.factory.AdoptionFactory;
import com.unq.adopt_me.factory.PetFactory;
import com.unq.adopt_me.util.PetAge;
import com.unq.adopt_me.util.PetGender;
import com.unq.adopt_me.util.PetSize;
import com.unq.adopt_me.util.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdoptionIntegralTest extends AdoptMeApplicationTests {


    private final String ADOPTION_URL = HTTP_LOCALHOST + "8085" + "/adoption";

    public static final String SEARCH_PATH = "/search";
    public LoginDto loginUser;
    public static final String SUCCESS_SEARCH = "Adoption successfully retrieved";
    public static final String SUCCESS_CREATION = "Adoption successfully created";
    ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AdoptionDao adoptionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthHelper authHelper;

    @BeforeEach
    void setUp() {
        Optional<User> userLogin = userDao.findByEmail("test.user@gmail.com");
        loginUser = new LoginDto(userLogin.get().getEmail(), "Asda1234");
        authHelper.setData(HTTP_LOCALHOST, port, restTemplate);
    }

    private ResponseEntity<GeneralResponse> httpCall(String url, HttpMethod httpMethod, Object objectBody) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authHelper.getToken(loginUser));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(objectBody, headers);
        return restTemplate.exchange(url, httpMethod, entity, GeneralResponse.class);
    }

    @Test
    void search_all_adoptions_get_status_OK_and_4_items() throws IOException {
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL + SEARCH_PATH, HttpMethod.GET, null );
        assertEquals(SUCCESS_SEARCH, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Adoption> responseList = (List<Adoption>) response.getBody().getData();
        assertEquals(4, responseList.size());
    }

    @Test
    void search_adoptions_with_type_filter_get_status_OK_and_we_expect_1() throws IOException {
        String dogTypeFilter = "?type="+ PetType.CAT;
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL + SEARCH_PATH +dogTypeFilter, HttpMethod.GET, null );
        assertEquals(SUCCESS_SEARCH, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Adoption> responseList = (List<Adoption>) response.getBody().getData();
        assertEquals(1, responseList.size());
    }

    @Test
    void search_adoptions_with_gender_filter_get_status_OK_and_we_expect_2() throws IOException {
        String femaleFilter = "?gender="+ PetGender.MALE;
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL + SEARCH_PATH + femaleFilter, HttpMethod.GET, null );
        assertEquals(SUCCESS_SEARCH, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Adoption> responseList = (List<Adoption>) response.getBody().getData();
        assertEquals(2, responseList.size());
    }

    @Test
    void search_adoptions_with_age_filter_get_status_OK_and_we_expect_1() throws IOException {
        String puppyFilter = "?age="+ PetAge.PUPPY;
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL + SEARCH_PATH + puppyFilter, HttpMethod.GET, null );
        assertEquals(SUCCESS_SEARCH, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Adoption> responseList = (List<Adoption>) response.getBody().getData();
        assertEquals(1, responseList.size());
    }

    @Test
    void search_adoptions_with_size_filter_get_status_OK_and_we_expect_2() throws IOException {
        String sizeFilter = "?size="+ PetSize.SMALL;
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL + SEARCH_PATH + sizeFilter, HttpMethod.GET, null );
        assertEquals(SUCCESS_SEARCH, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Adoption> responseList = (List<Adoption>) response.getBody().getData();
        assertEquals(2, responseList.size());
    }

    @Test
    void create_adoptions_status_OK_and_we_expect_same_object() throws IOException {
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL, HttpMethod.POST, AdoptionFactory.anyAdoptionRequest());
        assertEquals(SUCCESS_CREATION, Objects.requireNonNull(response.getBody()).getMessage());
        Adoption adoption = mapper.convertValue(response.getBody().getData(), Adoption.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        User owner = userDao.findById(Long.valueOf(AdoptionFactory.anyAdoptionRequest().getUserId())).get();
        assertEquals(owner.getName(), adoption.getOwner().getName());
        assertEquals("Boni", adoption.getPet().getName());
    }

    @Test
    void create_adoptions_status_BAD_REQUEST_on_6_fields_and_we_expect_same_object() throws IOException {
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL, HttpMethod.POST, AdoptionFactory.adoptionRequestWithWrongParameter());
        String expectedMessage = "La imágen es obligatoria, " +
                                 "el género es obligatorio, " +
                                 "el tamaño es obligatorio, " +
                                 "el tipo es obligatorio, " +
                                 "la descripción es obligatoria, " +
                                 "la edad debe ser mayor o igual a 0";

        List<String> expectedErrors = Arrays.asList(expectedMessage.toLowerCase().split(", "));
        List<String> actualErrors = Arrays.asList(Objects.requireNonNull(response.getBody()).getMessage().toLowerCase().split(", "));

        Collections.sort(expectedErrors);
        Collections.sort(actualErrors);

        assertEquals(expectedErrors, actualErrors, "Los mensajes de error coinciden");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void create_adoptions_status_BAD_REQUEST_cause_age_is_under_zero() throws IOException {
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL, HttpMethod.POST, AdoptionFactory.anyAdoptionWithPet_(PetFactory.petDtoWithNegativeAge()));
        String expectedMessage = "La edad debe ser mayor o igual a 0";

        assertEquals(expectedMessage, (Objects.requireNonNull(response.getBody())).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void create_adoptions_status_BAD_REQUEST_cause_have_no_description() throws IOException {
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL, HttpMethod.POST, AdoptionFactory.anyAdoptionWithPet_(PetFactory.petDtoWithNoDescription()));
        String expectedMessage = "La descripción es obligatoria";

        assertEquals(expectedMessage, (Objects.requireNonNull(response.getBody())).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void create_adoptions_status_BAD_REQUEST_cause_have_no_type() throws IOException {
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL, HttpMethod.POST, AdoptionFactory.anyAdoptionWithPet_(PetFactory.petDtoWithNoType()));
        String expectedMessage = "El tipo es obligatorio";

        assertEquals(expectedMessage, (Objects.requireNonNull(response.getBody())).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void create_adoptions_status_BAD_REQUEST_cause_have_no_size() throws IOException {
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL, HttpMethod.POST, AdoptionFactory.anyAdoptionWithPet_(PetFactory.petDtoWithNoSize()));
        String expectedMessage = "El tamaño es obligatorio";

        assertEquals(expectedMessage, (Objects.requireNonNull(response.getBody())).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void create_adoptions_status_BAD_REQUEST_cause_have_no_gender() throws IOException {
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL, HttpMethod.POST, AdoptionFactory.anyAdoptionWithPet_(PetFactory.petDtoWithNoGender()));
        String expectedMessage = "El género es obligatorio";

        assertEquals(expectedMessage, (Objects.requireNonNull(response.getBody())).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void create_adoptions_status_BAD_REQUEST_cause_have_no_image() throws IOException {
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL, HttpMethod.POST, AdoptionFactory.anyAdoptionWithPet_(PetFactory.petDtoWithNoImage()));
        String expectedMessage = "La imágen es obligatoria";

        assertEquals(expectedMessage, (Objects.requireNonNull(response.getBody())).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void create_adoptions_status_BAD_REQUEST_cause_have_no_name() throws IOException {
        ResponseEntity<GeneralResponse> response = httpCall(ADOPTION_URL, HttpMethod.POST, AdoptionFactory.anyAdoptionWithPet_(PetFactory.petDtoWithNoName()));
        String expectedMessage = "El nombre es obligatorio";

        assertEquals(expectedMessage, (Objects.requireNonNull(response.getBody())).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }



}
