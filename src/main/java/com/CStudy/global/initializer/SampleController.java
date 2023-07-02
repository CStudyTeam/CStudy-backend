package com.CStudy.global.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class SampleController {

    private final SampleInitializer sampleInitializer;

    @GetMapping("sampledata")
    @ResponseStatus(HttpStatus.CREATED)
    public void data(){
        sampleInitializer.init();
    }
}
