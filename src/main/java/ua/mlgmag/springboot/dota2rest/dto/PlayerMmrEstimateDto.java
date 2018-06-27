package ua.mlgmag.springboot.dota2rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerMmrEstimateDto {
    private int estimate;
    private int stdDev;
    private int n;
}
