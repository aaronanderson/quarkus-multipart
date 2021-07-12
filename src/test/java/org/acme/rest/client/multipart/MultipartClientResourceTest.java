package org.acme.rest.client.multipart;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.http.Header;
import io.restassured.specification.MultiPartSpecification;

@QuarkusTest
public class MultipartClientResourceTest {

    @Test
    public void testMultipartEndpoint() {
        MultiPartSpecification multipartSpec = new MultiPartSpecBuilder("Test file contents.".getBytes()).fileName("file.txt").controlName("file").mimeType("text/plain").build();

        given()
                .header(new Header("content-type", "multipart/form-data"))
                .multiPart(multipartSpec)
                .log().body()
                .when().post("/multipart")
                .then()                
                .log().body()
                .statusCode(200)
                .body(is("Success"));
    }

}