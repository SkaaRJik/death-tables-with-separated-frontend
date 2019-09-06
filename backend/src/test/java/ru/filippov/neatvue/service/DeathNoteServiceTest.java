package ru.filippov.neatvue.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.test.context.junit4.SpringRunner;
import ru.filippov.neatvue.service.death.DeathTableService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DeathNoteServiceTest {

    @Autowired
    DeathTableService deathNoteService;

    @Test
    public void getDeathNotesByBirthYearAndBetweenAgesTest() throws ChangeSetPersister.NotFoundException {

       // Assertions.assertThat(deathNoteService.getDeathNotesByBirthYearAndBetweenAges((short) 2013, (byte) 5, (byte) 8)).isNot(null);



    }

}
