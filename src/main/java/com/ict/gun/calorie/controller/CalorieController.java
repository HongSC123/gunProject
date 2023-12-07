package com.ict.gun.calorie.controller;

import com.ict.gun.calorie.service.CalorieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
public class CalorieController {


    private final CalorieService calorieService;

//    @GetMapping("/{}")
//    public Calorie

}
