package com.dinoology.hms.config.ModelMapper;
import org.modelmapper.ModelMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Created by: sangeethnawa
 * Date:2024 12/29/2024
 * Copyright © 2024 DinooLogy
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}