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
        CERTAIN_YEAR
    }

    @Autowired
    private DeathNoteRepository deathNoteRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Transactional
    public void addDeathNoteInfo(DeathNote newDeathNote){
        deathNoteRepository.save(newDeathNote);
    }


    public Map getDeathNoteByLocationAndCertainYearAndAges(List<DataType> dataTypes, Location location, List<Short> yearsForQuerry, Short year, List<Byte> ages) throws SQLDataException {
        List<DeathNote> deathNotes = deathNoteRepository.findAllByLocationAndBirthYearInAndAgeIn(location, yearsForQuerry, ages).orElseThrow(() -> new SQLDataException("Нет соответсвий данному запросу"));
        List<Short> targetYears = new ArrayList<>(1);
        targetYears.add(year);
        return packDataToMap(dataTypes, deathNotes, yearsForQuerry, targetYears,ages, Mode.CERTAIN_YEAR);
    }

    public Map<String, Object> getDeathNoteByLocationAndBirthYearAndAges(List<DataType> dataTypes, Location location, Short year, List<Byte> ages) throws SQLDataException {
        List<DeathNote> deathNotes = deathNoteRepository.findAllByLocationAndBirthYearAndAgeIn(location, year, ages).orElseThrow(() -> new SQLDataException("Нет соответсвий данному запросу"));
        List<Short> targetYears = new ArrayList<>(1);
        List<Short> yearsForQuerry = new ArrayList<>(1);
        targetYears.add(year);
        yearsForQuerry.add(year);
        return packDataToMap(dataTypes, deathNotes, yearsForQuerry, targetYears,ages, Mode.BIRTH_YEAR);

    }


    public Map getDeathNoteByLocationAndBirthYearsAndAges(List<DataType> dataTypes, Location location, List<Short> yearsForQuerry, List<Short> targetYears, List<Byte> ages, Mode mode) throws SQLDataException {

        List<DeathNote> deathNotes = deathNoteRepository.findAllByLocationAndBirthYearInAndAgeIn(location, yearsForQuerry, ages).orElseThrow(() -> new SQLDataException("Нет соответсвий данному запросу"));

        return packDataToMap(dataTypes, deathNotes, yearsForQuerry, targetYears,ages, mode);
    }

    public Map packDataToMap(List<DataType> dataTypes, List<DeathNote> deathNotes, List<Short> years, List<Short> targetYears, List<Byte> ages, Mode mode){
        Map<String, Object> data = new HashMap<>(dataTypes.size());

        Map<Short, Map<Byte, DeathData>> yearsMap;
        Map<Byte, DeathData> agesMap;
        List<Short> tempYears =  mode == Mode.CERTAIN_YEAR ? targetYears : years;

        for (DataType dataType : dataTypes) {
            yearsMap = new HashMap<>(years.size());
            /*if(mode == Mode.CERTAIN_YEAR){


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
                for (int i = 0; i < tempYears.size(); i++) {
                    yearsMap.put(tempYears.get(i), new HashMap<>(ages.size()));
                }
            }*/

            for (int i = 0; i < tempYears.size(); i++) {
                yearsMap.put(tempYears.get(i), new HashMap<>(ages.size()));
            }

            Map<Short, Map<Byte, DeathData>> finalYearsMap = yearsMap;
            data.put(dataType.name(), new HashMap<>(2){{put("data", finalYearsMap);}});

        }

        for(DeathNote deathNote : deathNotes) {

            for (DataType dataType : dataTypes) {
                switch (dataType) {
                    case TOTAL:
                        ((Map)data.get(dataType.name())).put("name", "Общий");
                        if(deathNote.getDeathDataTotal() != null) {
                            if (mode == Mode.CERTAIN_YEAR) {
                                yearsMap = (Map<Short, Map<Byte, DeathData>>) ((Map) data.get(dataType.name())).get("data");

                                if (yearsMap.containsKey((short) (deathNote.getBirthYear() + deathNote.getAge()))) {
                                    yearsMap.get((short) (deathNote.getBirthYear() + deathNote.getAge())).put(deathNote.getAge(), deathNote.getDeathDataTotal());
                                }
                            } else {
                                ((Map<Short, Map<Byte, DeathData>>) data
                                        .get(dataType.name()))
                                        .get(deathNote.getBirthYear())
                                        .put(deathNote.getAge(), deathNote.getDeathDataTotal());
                            }
                        }
                        break;
                    case MALE:
                        ((Map)data.get(dataType.name())).put("name", "Мужичны");
                        if(deathNote.getDeathDataMale() != null) {
                            if (mode == Mode.CERTAIN_YEAR) {

                                yearsMap = (Map<Short, Map<Byte, DeathData>>) ((Map) data.get(dataType.name())).get("data");
                                if (yearsMap.containsKey((short) (deathNote.getBirthYear() + deathNote.getAge()))) {
                                    yearsMap.get((short) (deathNote.getBirthYear() + deathNote.getAge())).put(deathNote.getAge(), deathNote.getDeathDataMale());
                                }
                            } else {
                                ((Map<Short, Map<Byte, DeathData>>) data.get(dataType.name())).get(deathNote.getBirthYear()).put(deathNote.getAge(), deathNote.getDeathDataMale());
                            }
                        }
                        break;
                    case FEMALE:
                        ((Map)data.get(dataType.name())).put("name", "Женщины");
                        if(deathNote.getDeathDataFemale() != null) {
                            if (mode == Mode.CERTAIN_YEAR) {
                                yearsMap = (Map<Short, Map<Byte, DeathData>>) ((Map) data.get(dataType.name())).get("data");
                                if (yearsMap.containsKey((short) (deathNote.getBirthYear() + deathNote.getAge()))) {
                                    yearsMap.get((short) (deathNote.getBirthYear() + deathNote.getAge())).put(deathNote.getAge(), deathNote.getDeathDataFemale());
                                }
                            } else {
                                ((Map<Short, Map<Byte, DeathData>>) data
                                        .get(dataType.name()))
                                        .get(deathNote.getBirthYear()).put(deathNote.getAge(), deathNote.getDeathDataFemale());
                            }
                        }
                        break;

                    case VILLAGER:
                        ((Map)data.get(dataType.name())).put("name", "Сельские");
                        if(deathNote.getDeathDataVillager() != null) {
                            if (mode == Mode.CERTAIN_YEAR) {
                                yearsMap = (Map<Short, Map<Byte, DeathData>>) ((Map) data.get(dataType.name())).get("data");
                                if (yearsMap.containsKey((short) (deathNote.getBirthYear() + deathNote.getAge()))) {
                                    yearsMap.get((short) (deathNote.getBirthYear() + deathNote.getAge())).put(deathNote.getAge(), deathNote.getDeathDataVillager());
                                }
                            } else {
                                ((Map<Short, Map<Byte, DeathData>>) data.get(dataType.name())).get(deathNote.getBirthYear()).put(deathNote.getAge(), deathNote.getDeathDataVillager());
                            }
                        }
                        break;
                    case CITY_DWELLER:
                        ((Map)data.get(dataType.name())).put("name", "Городские");
                        if(deathNote.getDeathDataCityDweller() != null) {
                            if (mode == Mode.CERTAIN_YEAR) {
                                yearsMap = (Map<Short, Map<Byte, DeathData>>) ((Map) data.get(dataType.name())).get("data");
                                if (yearsMap.containsKey((short) (deathNote.getBirthYear() + deathNote.getAge()))) {
                                    yearsMap.get((short) (deathNote.getBirthYear() + deathNote.getAge())).put(deathNote.getAge(), deathNote.getDeathDataCityDweller());
                                }
                            } else {
                                ((Map<Short, Map<Byte, DeathData>>) data.get(dataType.name())).get(deathNote.getBirthYear()).put(deathNote.getAge(), deathNote.getDeathDataCityDweller());
                            }
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
