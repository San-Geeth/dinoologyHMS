package com.dinoology.hms.staff.dto.request;

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
public class GetAllStaffMembers {
    private Integer page;
    private Integer size;
}
