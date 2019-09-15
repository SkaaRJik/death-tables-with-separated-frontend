package ru.filippov.neatvue.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.filippov.neatvue.service.death.DeathTableService;

import java.sql.SQLDataException;

@RestController
@RequestMapping(value = "/api")
public class TestController {

    @GetMapping(value = "/public/test")
    public ResponseEntity getHome() {
        return ResponseEntity.ok("Welcome to Your home page");
    }

    @GetMapping(value = "/secured/admin/welcome")
    public ResponseEntity getSecuredAdmin() {
        return ResponseEntity.ok("Welcome to the secured page. Only for Admin");
    }

    @GetMapping(value = "/secured/user/welcome")
    public ResponseEntity getSecured() {
        return ResponseEntity.ok("Welcome to the secured page");
    }

    @PostMapping(value = "/secured/user/postdata")
    public ResponseEntity postData() {
        return ResponseEntity.ok("Data is saved");
    }




}
