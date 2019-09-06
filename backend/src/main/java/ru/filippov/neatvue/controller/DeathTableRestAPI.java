package ru.filippov.neatvue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.filippov.neatvue.service.death.DeathTableService;

import java.io.IOException;
import java.sql.SQLDataException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/public/death_table")
@Slf4j
public class DeathTableRestAPI {

    @Autowired
    DeathTableService deathTableService;

    @GetMapping
    public ResponseEntity<Object> getDeathTable(@RequestParam Map<String, String> allParams){

        try {
            Map<String,Object> filter =
                    new ObjectMapper().readValue(allParams.values().toString().substring(1, allParams.values().toString().length()-1), HashMap.class);
            if(filter.containsKey("birthYear")){

                return ResponseEntity.ok(deathTableService.getSexDeathTableByBirthAge( Short.valueOf(String.valueOf(filter.get("birthYear")))));


            } else if(filter.containsKey("year") && filter.containsKey("ageFrom") && filter.containsKey("ageTo") ){
                return ResponseEntity.ok(deathTableService.getDeathTableOnYearBetweenAges(
                        Short.valueOf(String.valueOf(filter.get("year"))),
                        Byte.valueOf(String.valueOf(filter.get("ageFrom"))),
                        Byte.valueOf(String.valueOf(filter.get("ageTo")))));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity("Не удалось распознать запрос",
                    HttpStatus.BAD_REQUEST);
        } catch (SQLDataException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity("Не удалось распознать запрос",
                HttpStatus.BAD_REQUEST);


    }

    @GetMapping("/birth_years")
    public ResponseEntity<Object> getBirthYears(){
        try {
            return ResponseEntity.ok(this.deathTableService.getAllBirthYears());
        } catch (SQLDataException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/ages")
    public ResponseEntity<Object> getAges(){
        try {
            return ResponseEntity.ok(this.deathTableService.getAllAges());
        } catch (SQLDataException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }
    }


}
