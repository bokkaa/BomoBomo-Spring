package com.example.bomobomo.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class AdminEventVo {
    private Long eventNumber;
    private String eventName;
    private Integer eventPrice;
    private String eventDetailName;
    private String eventDetailUploadPath;
    private String eventDetailUuid;
    private String eventImgName;
    private String eventImgUploadPath;
    private String eventImgUuid;
    private double avg;
}
