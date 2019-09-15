package ru.filippov.neatvue.domain.death;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.filippov.neatvue.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "location")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location extends BaseEntity {

    @Column(unique = true)
    private String name;



}
