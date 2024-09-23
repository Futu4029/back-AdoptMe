package com.unq.adopt_me;

import com.unq.adopt_me.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class AdoptMeApplicationTests {

	protected static final String HTTP_LOCALHOST = "http://localhost:";

	@LocalServerPort
    protected int port;

	@Autowired
	private TestRestTemplate restTemplate;


}
