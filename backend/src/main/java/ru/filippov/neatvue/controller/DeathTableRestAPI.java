package ru.filippov.neatvue.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import ru.filippov.neatvue.domain.death.Location;
import ru.filippov.neatvue.service.death.DeathTableService;

import java.io.IOException;
import java.sql.SQLDataException;
import java.util.*;

@RestController
@RequestMapping("/api/public/death_table")
@Slf4j
public class DeathTableRestAPI {

    @Autowired
    DeathTableService deathTableService;

    @GetMapping
    public ResponseEntity<Object> getDeathTable(@RequestParam MultiValueMap allParams){

        try {
        ObjectMapper objectMapper = new ObjectMapper();

        Location location = objectMapper.readValue((String) allParams.getFirst("location") , Location.class);




        Map<String, Object> responseData = new HashMap(5);

        List<DeathTableService.DataType> dataTypes = new ArrayList<>(5);



            if ("true".equals(allParams.getFirst("MALE"))){
                dataTypes.add(DeathTableService.DataType.MALE);
            }
            if ("true".equals(allParams.getFirst("FEMALE"))){
                dataTypes.add(DeathTableService.DataType.FEMALE);
            }
            if ("true".equals(allParams.getFirst("CITY_DWELLER"))){
                dataTypes.add(DeathTableService.DataType.CITY_DWELLER);
            }
            if ("true".equals(allParams.getFirst("VILLAGER"))){
                dataTypes.add(DeathTableService.DataType.VILLAGER);
            }
            if ("true".equals(allParams.getFirst("TOTAL"))){
                dataTypes.add(DeathTableService.DataType.TOTAL);
            }

            List<Short> years;
            List<Byte> ages;



            String yearSelector = (String) allParams.getFirst("yearSelector");

            if("range".equals(yearSelector)){
                Short yearFrom = Short.parseShort((String)allParams.getFirst("yearFrom"));
                Short yearTo = Short.parseShort((String)allParams.getFirst("yearTo"));
                years = new ArrayList<>(yearTo-yearFrom);
                for (short i = yearFrom; i <= yearTo; i++) {
                    years.add(i);
                }
            } else {

                String[] split = ((String) allParams.getFirst("years")).split(",");

                years = new ArrayList<>(split.length);

                for (int i = 0; i < split.length; i++) {
                    years.add(Short.parseShort(split[i]));
                }
            }

            String ageSelector = (String) allParams.getFirst("ageSelector");

            if("range".equals(ageSelector)){
                Byte ageFrom = Byte.parseByte((String)allParams.getFirst("ageFrom"));
                Byte ageTo = Byte.parseByte((String)allParams.getFirst("ageTo"));
                ages = new ArrayList<>(ageTo-ageFrom);
                for (byte i = ageFrom; i <= ageTo; i++) {
                    ages.add(i);
                }
            } else {

                String[] split = ((String) allParams.getFirst("ages")).split(",");

                ages = new ArrayList<>(split.length);

                for (int i = 0; i < split.length; i++) {
                    ages.add(Byte.parseByte(split[i]));
                }
            }

            String yearMode = (String) allParams.getFirst("yearMode");



            if("birth".equals(yearMode)){
                responseData = deathTableService.getDeathNoteByLocationAndBirthYearsAndAges(dataTypes, location, years,years ,ages, DeathTableService.Mode.BIRTH_YEAR);
            } else {

                List<Short> newYears = new ArrayList<>(years.size()*ages.size());

                for (short year : years) {
                    for (byte age : ages){
                        newYears.add((short)(year-age));
                    }
                }

                responseData = deathTableService.getDeathNoteByLocationAndBirthYearsAndAges(dataTypes, location, newYears, years,ages, DeathTableService.Mode.YEAR);



            }





            //deathTableService.getDeathNoteByLocationAndBirthYearsAndAges(dataTypes, locationId, )

            return ResponseEntity.ok(responseData);

            /* if(filter.containsKey("birthYear")){

                return ResponseEntity.ok(deathTableService.getSexDeathTableByBirthAge( Short.valueOf(String.valueOf(filter.get("birthYear")))));


            } else if(filter.containsKey("year") && filter.containsKey("ageFrom") && filter.containsKey("ageTo") ){
                return ResponseEntity.ok(deathTableService.getDeathTableOnYearBetweenAges(
                        Short.valueOf(String.valueOf(filter.get("year"))),
                        Byte.valueOf(String.valueOf(filter.get("ageFrom"))),
                        Byte.valueOf(String.valueOf(filter.get("ageTo")))));
            }*/
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity("Не удалось распознать запрос",
                    HttpStatus.BAD_REQUEST);
        } /*catch (SQLDataException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }*/

    }

    @GetMapping(value = "/birth_years")
    public ResponseEntity<Object> getBirthYears(){
        try {
            return ResponseEntity.ok(this.deathTableService.getAllBirthYears());
        } catch (SQLDataException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value = "/ages")
    public ResponseEntity<Object> getAges(){
        try {
            return ResponseEntity.ok(this.deathTableService.getAllAges());
        } catch (SQLDataException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/locations")
    public ResponseEntity<Object> getAllLocations(){
        try {
            return ResponseEntity.ok(this.deathTableService.getAllLocations());
        } catch (SQLDataException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }

    }

}
