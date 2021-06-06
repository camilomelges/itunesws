package com.rafamilo.itunesws.domain.itunesdata.entrypoint.controllers;

import com.rafamilo.itunesws.configurations.TestRestTemplateConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TestRestTemplateConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ITunesDataControllerTest {

    @Value("${itunesws.properties.auth.basic.username}")
    private String username;

    @Value("${itunesws.properties.auth.basic.password}")
    private String password;

    @Value("${server.port}")
    private String port;

    @Autowired
    private RestTemplate testRestTemplate;

    @Test
    void shouldBeReturn401WhenNotPassBasicAuth() {
        final HttpHeaders headers = createHeaders(null);
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getRequestURL());
        headers.remove(HttpHeaders.AUTHORIZATION);
        final HttpEntity<?> entity = new HttpEntity<>(headers);
        builder.queryParam("query", "jack johnson");

        Assert.assertThrows(HttpClientErrorException.Unauthorized.class, () -> {
            testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, List.class);
        });
    }

    @Test
    void shouldBeReturn401WhenNotPassNullBasicAuth() {
        final HttpHeaders headers = createHeaders("null");
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getRequestURL());
        final HttpEntity<?> entity = new HttpEntity<>(headers);
        builder.queryParam("query", "jack johnson");

        Assert.assertThrows(HttpClientErrorException.Unauthorized.class, () -> {
            testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, List.class);
        });
    }

    @Test
    void shouldBeReturn401WhenNotPassEmptyBasicAuth() {
        final HttpHeaders headers = createHeaders("");
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getRequestURL());
        final HttpEntity<?> entity = new HttpEntity<>(headers);
        builder.queryParam("query", "jack johnson");

        Assert.assertThrows(HttpClientErrorException.Unauthorized.class, () -> {
            testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, List.class);
        });
    }

    @Test
    void shouldBeReturn401WhenNotPassBlankBasicAuth() {
        final HttpHeaders headers = createHeaders(" ");
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getRequestURL());
        final HttpEntity<?> entity = new HttpEntity<>(headers);
        builder.queryParam("query", "jack johnson");

        Assert.assertThrows(HttpClientErrorException.Unauthorized.class, () -> {
            testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, List.class);
        });
    }

    @Test
    void shouldBeReturn401WhenNotPassWrongBasicAuth() {
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getRequestURL());
        builder.queryParam("query", "jack johnson");

        Assert.assertThrows(HttpClientErrorException.Unauthorized.class, () -> {
            final HttpEntity<?> entity = new HttpEntity<>(createHeaders("Basic "));
            testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, List.class);
        });

        Assert.assertThrows(HttpClientErrorException.Unauthorized.class, () -> {
            final HttpEntity<?> entity = new HttpEntity<>(createHeaders("Basic ".concat(Base64.getEncoder().encodeToString(("foo".concat(":").concat("bar")).getBytes()))));
            testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, List.class);
        });
    }

    @Test
    void shouldBeReturn400WhenNotPassQueryString() {
        final HttpHeaders headers = createHeaders(null);
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getRequestURL());
        final HttpEntity<?> entity = new HttpEntity<>(headers);

        Assert.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, List.class);
        });
    }

    @Test
    void shouldBeReturn400WhenNotQueryStringIsNull() {
        final HttpHeaders headers = createHeaders(null);
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getRequestURL());
        final HttpEntity<?> entity = new HttpEntity<>(headers);
        builder.queryParam("query", (Object) null);

        Assert.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, List.class);
        });
    }

    @Test
    void shouldBeReturn400WhenNotQueryStringIsEmpty() {
        final HttpHeaders headers = createHeaders(null);
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getRequestURL());
        final HttpEntity<?> entity = new HttpEntity<>(headers);
        builder.queryParam("query", "");

        Assert.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, List.class);
        });
    }

    @Test
    void shouldBeReturn400WhenNotQueryStringIsBlank() {
        final HttpHeaders headers = createHeaders(null);
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getRequestURL());
        final HttpEntity<?> entity = new HttpEntity<>(headers);
        builder.queryParam("query", " ");

        Assert.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, List.class);
        });
    }

    @Test
    void shouldBeReturn200WhenPassParams() {
        final HttpHeaders headers = createHeaders(null);
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getRequestURL());
        final HttpEntity<?> entity = new HttpEntity<>(headers);
        builder.queryParam("query", "jack johnson");

        final ResponseEntity<List> response = testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    private HttpHeaders createHeaders(final String basicAuth) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(
                HttpHeaders.AUTHORIZATION,
                basicAuth == null
                        ? "Basic ".concat(Base64.getEncoder().encodeToString((username.concat(":").concat(password)).getBytes()))
                        : basicAuth
        );
        return headers;
    }

    private String getRequestURL() {
        return "http://localhost:" + port + "/itunes-data";
    }
}