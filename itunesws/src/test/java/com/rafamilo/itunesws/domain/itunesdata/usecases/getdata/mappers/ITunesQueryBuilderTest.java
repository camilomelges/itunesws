package com.rafamilo.itunesws.domain.itunesdata.usecases.getdata.mappers;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
class ITunesQueryBuilderTest {

    @Test
    public void shouldBeReturnCorrectString() {
        final String query = "\\.[]{}()<>*+-=!?^$|, foo+bar jack++johndoe 123";
        final String correctString = "+++++++++++++++++++++foo+bar+jack++johndoe+123&limit=200";
        Assert.assertEquals(correctString, ITunesQueryBuilder.run(query));
    }
}