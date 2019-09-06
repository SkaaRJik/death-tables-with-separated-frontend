package ru.filippov.neatvue.service.death;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.filippov.neatvue.domain.death.DeathNote;
import ru.filippov.neatvue.repository.DeathNoteRepository;
import ru.filippov.neatvue.service.death.table.DeathTable;
import ru.filippov.neatvue.service.death.table.DeathTableCreator;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DeathTableService {

    @Autowired
    private DeathNoteRepository deathNoteRepository;

    @Transactional
    public void addDeathNoteInfo(DeathNote newDeathNote){

        deathNoteRepository.save(newDeathNote);

    }


    public List<DeathNote> getDeathNotesByBirthYearAndBetweenAges(short birthYear,
                                                                  byte ageFrom,
                                                                  byte ageTo) throws SQLDataException {
        return deathNoteRepository
                .findAllByBirthYearAndAgeBetween(birthYear, ageFrom, ageTo)
                .orElseThrow(() -> new SQLDataException("Нет соответсвий данному запросу"));
    }



    public DeathTable getSexDeathTableByBirthAge(short birthYear) throws SQLDataException {

        List<DeathNote> deathNotes = deathNoteRepository
                .findAllByBirthYear(birthYear)
                .orElseThrow(() -> new SQLDataException("Нет соответсвий данному запросу"));

            return DeathTableCreator.createTableForOneYearAndSexCategory(deathNotes);
    }

    public List<Map> getDeathTableOnYearBetweenAges(short year,
                                                    byte ageFrom,
                                                    byte ageTo) throws SQLDataException {

        List<DeathNote> deathNotes = deathNoteRepository
                .findAllByBirthYearBetweenAndAgeBetween((short) (year-ageTo), (short) (year - ageFrom), ageFrom, ageTo)
                .orElseThrow(()-> new SQLDataException("Нет соответсвий данному запросу"));

        byte currentAge = ageFrom;

        List<Map> result = new ArrayList<>(ageTo-ageFrom);



        for (byte i = ageFrom; i < ageTo; i++) {
            for(DeathNote deathNote : deathNotes){
                if(deathNote.getAge() == i && deathNote.getBirthYear() == year - i){
                    Map<String, Object> jsonObj = new HashMap<>(8);

                    jsonObj.put("location", deathNote.getLocation().getName());

                    jsonObj.put("age", deathNote.getAge());

                    jsonObj.put("birthYear", deathNote.getBirthYear());

                    if(deathNote.getDeathDataFemale() != null) {
                        jsonObj.put("female", deathNote.getDeathDataFemale());
                    }

                    if(deathNote.getDeathDataMale() != null) {
                        jsonObj.put("male", deathNote.getDeathDataMale());
                    }

                    if(deathNote.getDeathDataTotal() != null) {
                        jsonObj.put("total", deathNote.getDeathDataTotal());
                    }

                    if(deathNote.getDeathDataCityDweller() != null) {
                        jsonObj.put("citizen", deathNote.getDeathDataCityDweller());
                    }

                    if(deathNote.getDeathDataVillager() != null) {
                        jsonObj.put("villager", deathNote.getDeathDataVillager());
                    }

                    result.add(jsonObj);
                }
            }
        }

        return result;



    }

    public List<Byte> getAllAges() throws SQLDataException {
        return this.deathNoteRepository.findDistinctAges().orElseThrow(()-> new SQLDataException("Нет соответсвий данному запросу"));


    }

    public List<Short> getAllBirthYears() throws SQLDataException {
        return this.deathNoteRepository.findDistinctBirthYears().orElseThrow(()-> new SQLDataException("Нет соответсвий данному запросу"));

    }
}
