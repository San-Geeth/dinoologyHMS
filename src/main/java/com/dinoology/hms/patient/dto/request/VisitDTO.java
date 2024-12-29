package com.dinoology.hms.patient.dto.request;

import com.dinoology.hms.patient.model.Patient;
import com.dinoology.hms.service.model.GeneralService;
import jakarta.persistence.*;
import lombok.Data;

/*
 * Created by: sangeethnawa
 * Date:2024 12/29/2024
 * Copyright © 2024 DinooLogy
 */
@Data
public class VisitDTO {
    private String reasonForVisit;
    private String visitNotes;
    private Integer service_id;
    private Integer doc_id;
}
