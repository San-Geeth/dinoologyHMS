package com.dinoology.hms.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Created by: sangeethnawa
 * Date:2024 12/16/2024
 * Copyright © 2024 DinooLogy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllUsers {
    private Integer page;
    private Integer size;
}
