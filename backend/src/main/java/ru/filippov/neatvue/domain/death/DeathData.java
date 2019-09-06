package ru.filippov.neatvue.domain.death;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.filippov.neatvue.domain.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class DeathData extends BaseEntity {



    @Column(name = "data_type")
    @NotNull
    @Enumerated(EnumType.STRING)
    protected DataType dataType;

    @NotNull
    protected Integer numberOfLiving;


    protected Integer numberOfDying;

    protected Double probabilityToDie;

    protected Double probabilityToSurvive;

    protected Integer averageLiving;

    protected Integer agesToLiveForPopulation;

    @Column(name = "expected_ages_of_a_person")
    protected Double expectedAgesOfAPerson;

    @OneToOne(mappedBy = "deathDataCityDweller")
    @JoinColumn(name = "fk_death_note_id")
    protected DeathNote deathNote;



    public DeathData(Long id, DataType dataType) {
        super(id);
        this.dataType = dataType;
    }
}
