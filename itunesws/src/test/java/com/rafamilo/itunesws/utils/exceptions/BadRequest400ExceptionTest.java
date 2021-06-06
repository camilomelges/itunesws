package com.rafamilo.itunesws.utils.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
@SpringBootTest
class BadRequest400ExceptionTest {

    @Test
    public void shouldInsertCorrectMessage() {
        final String message = "foo";

        Assertions.assertEquals(message, new BadRequest400Exception(message).getMessage());
    }
}