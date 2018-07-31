package ua.mlgmag.springboot.dota2rest.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "heroes")
public class Hero {

    @Id
    @Column(name = "id", columnDefinition = "INTEGER", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String name;

    @Column(name = "icon_url", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String iconUrl;

}
