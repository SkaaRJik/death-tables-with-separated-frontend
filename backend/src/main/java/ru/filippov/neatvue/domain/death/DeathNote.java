package ru.filippov.neatvue.domain.death;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.filippov.neatvue.domain.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "death_note")
@Entity
@SuperBuilder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeathNote extends BaseEntity {


    @Column(name = "birth_year")
    @NotNull
    protected Short birthYear;

    @NotNull
    protected Byte age;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_location_id")
    @NotNull
    protected Location location;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="fk_death_data_total_id")
    protected DeathDataTotal deathDataTotal;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="fk_death_data_male_id")
    protected DeathDataMale deathDataMale;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="fk_death_data_female_id")
    protected DeathDataFemale deathDataFemale;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="fk_death_data_city_dweller_id")
    protected DeathDataCityDweller deathDataCityDweller;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="fk_death_data_villager_id")
    protected DeathDataVillager deathDataVillager;



}
