package ua.mlgmag.springboot.dota2rest.dto;

import lombok.Data;

@Data
public class HeroDto {
    private Integer id;
    private String name;
    private String localized_name;
    private String primary_attr;
    private String attack_type;
    private String[] roles;
}
