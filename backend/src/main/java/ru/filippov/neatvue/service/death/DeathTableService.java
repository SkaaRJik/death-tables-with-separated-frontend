package ru.filippov.neatvue.service.death;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.filippov.neatvue.domain.death.DeathData;
import ru.filippov.neatvue.domain.death.DeathNote;
import ru.filippov.neatvue.domain.death.Location;
import ru.filippov.neatvue.repository.DeathNoteRepository;
import ru.filippov.neatvue.repository.LocationRepository;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DeathTableService {

    public enum DataType{
        MALE,
        FEMALE,
        TOTAL,
        VILLAGER,
        CITY_DWELLER
    }

    public enum Mode {
        BIRTH_YEAR,
        YEAR
    }

    @Autowired
    private DeathNoteRepository deathNoteRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Transactional
    public void addDeathNoteInfo(DeathNote newDeathNote){
        deathNoteRepository.save(newDeathNote);
    }


    public Map getDeathNoteByLocationAndBirthYearsAndAges(List<DataType> dataTypes, Location location, List<Short> yearsForQuerry, List<Short> targetYears, List<Byte> ages, Mode mode) throws SQLDataException {

        List<DeathNote> deathNotes = deathNoteRepository.findAllByLocationAndBirthYearInAndAgeIn(location, yearsForQuerry, ages).orElseThrow(() -> new SQLDataException("Нет соответсвий данному запросу"));

        return packDataToMap(dataTypes, deathNotes, yearsForQuerry, targetYears,ages, mode);
    }

    public Map packDataToMap(List<DataType> dataTypes, List<DeathNote> deathNotes, List<Short> years, List<Short> targetYears, List<Byte> ages, Mode mode){
        Map<String, Map<Short, Map<Byte, DeathData>>> data = new HashMap<>(dataTypes.size());
        Map<Short, Map<Byte, DeathData>> yearsMap;
        Map<Byte, DeathData> agesMap;
        for (DataType dataType : dataTypes) {
            yearsMap = new HashMap<>(years.size());
            if(mode == Mode.YEAR){
                short newYear;

                for(short year : targetYears) {

                    for (byte age : ages) {
                        newYear = (short) (year - age);
                        if(yearsMap.containsKey(newYear)){
                            agesMap = yearsMap.get(newYear);
                        } else {
                            agesMap = new HashMap<>(ages.size());
                            yearsMap.put(newYear, agesMap);
                        }
                        agesMap.put(age, null);
                    }
                }
            } else {
                for (int i = 0; i < years.size(); i++) {
                    yearsMap.put(years.get(i), new HashMap<>(ages.size()));
                }
            }
            data.put(dataType.name(), yearsMap);
        }

        for(DeathNote deathNote : deathNotes) {
            for (DataType dataType : dataTypes) {
                switch (dataType) {
                    case TOTAL:
                        if(mode == Mode.YEAR){
                            agesMap = data.get(dataType.name()).get(deathNote.getBirthYear());
                            if(agesMap.containsKey(deathNote.getAge())){
                                agesMap.put(deathNote.getAge(), deathNote.getDeathDataTotal());
                            }
                        } else {
                            data.get(dataType.name()).get(deathNote.getBirthYear()).put(deathNote.getAge(), deathNote.getDeathDataTotal());
                        }
                        break;
                    case MALE:
                        if(mode == Mode.YEAR){
                            agesMap = data.get(dataType.name()).get(deathNote.getBirthYear());
                            if(agesMap.containsKey(deathNote.getAge())){
                                agesMap.put(deathNote.getAge(), deathNote.getDeathDataMale());
                            }
                        } else {
                            data.get(dataType.name()).get(deathNote.getBirthYear()).put(deathNote.getAge(), deathNote.getDeathDataMale());
                        }
                        break;
                    case FEMALE:
                        if(mode == Mode.YEAR){
                            agesMap = data.get(dataType.name()).get(deathNote.getBirthYear());
                            if(agesMap.containsKey(deathNote.getAge())){
                                agesMap.put(deathNote.getAge(), deathNote.getDeathDataFemale());
                            }
                        } else {
                            data.get(dataType.name()).get(deathNote.getBirthYear()).put(deathNote.getAge(), deathNote.getDeathDataFemale());
                        }
                        break;
                    case VILLAGER:
                        if(mode == Mode.YEAR){
                            agesMap = data.get(dataType.name()).get(deathNote.getBirthYear());
                            if(agesMap.containsKey(deathNote.getAge())){
                                agesMap.put(deathNote.getAge(), deathNote.getDeathDataVillager());
                            }
                        } else {
                            data.get(dataType.name()).get(deathNote.getBirthYear()).put(deathNote.getAge(), deathNote.getDeathDataVillager());
                        }
                        break;
                    case CITY_DWELLER:
                        if(mode == Mode.YEAR){
                            agesMap = data.get(dataType.name()).get(deathNote.getBirthYear());
                            if(agesMap.containsKey(deathNote.getAge())){
                                agesMap.put(deathNote.getAge(), deathNote.getDeathDataCityDweller());
                            }
                        } else {
                            data.get(dataType.name()).get(deathNote.getBirthYear()).put(deathNote.getAge(), deathNote.getDeathDataCityDweller());
                        }
                        break;
                }
            }
        }



        return data;

    }

    public List<Byte> getAllAges() throws SQLDataException {
        return this.deathNoteRepository.findDistinctAges().orElseThrow(()-> new SQLDataException("Нет соответсвий данному запросу"));


    }

    public List<Short> getAllBirthYears() throws SQLDataException {
        return this.deathNoteRepository.findDistinctBirthYears().orElseThrow(()-> new SQLDataException("Нет соответсвий данному запросу"));

    }

    public List<Location> getAllLocations() throws SQLDataException {
        //return this.locationRepository.findAllLocations().orElseThrow(()-> new SQLDataException("Нет соответсвий данному запросу"));
        return this.locationRepository.findAll();
    }
}
