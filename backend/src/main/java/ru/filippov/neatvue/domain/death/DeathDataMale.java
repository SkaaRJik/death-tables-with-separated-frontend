package ru.filippov.neatvue.domain.death;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "death_data_male")
@Entity
@Data
@SuperBuilder(toBuilder = true)
public class DeathDataMale extends DeathData{

    public DeathDataMale(@NotNull DataType dataType, @NotNull Integer numberOfLiving, Integer numberOfDying, Double probabilityToDie, Double probabilityToSurvive, Integer averageLiving, Integer agesToLiveForPopulation, Double expectedAgesOfAPerson, DeathNote deathNote) {
        super(dataType, numberOfLiving, numberOfDying, probabilityToDie, probabilityToSurvive, averageLiving, agesToLiveForPopulation, expectedAgesOfAPerson, deathNote);
    }

    public DeathDataMale() {
    }

    public DeathDataMale(Long id, DataType dataType) {
        super(id, dataType);
    }
}
