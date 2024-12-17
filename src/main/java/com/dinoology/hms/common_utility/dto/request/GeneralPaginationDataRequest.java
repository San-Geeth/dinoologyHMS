package com.dinoology.hms.common_utility.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Created by: sangeethnawa
 * Date:2024 12/17/2024
 * Copyright © 2024 DinooLogy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralPaginationDataRequest {
    private Integer page;
    private Integer size;
}
