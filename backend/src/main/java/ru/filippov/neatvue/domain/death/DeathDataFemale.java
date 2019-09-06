package ru.filippov.neatvue.domain.death;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "death_data_female")
@Entity
@Data
@SuperBuilder(toBuilder = true)
public class DeathDataFemale extends DeathData{

    public DeathDataFemale(@NotNull DataType dataType, @NotNull Integer numberOfLiving, Integer numberOfDying, Double probabilityToDie, Double probabilityToSurvive, Integer averageLiving, Integer agesToLiveForPopulation, Double expectedAgesOfAPerson, DeathNote deathNote) {
        super(dataType, numberOfLiving, numberOfDying, probabilityToDie, probabilityToSurvive, averageLiving, agesToLiveForPopulation, expectedAgesOfAPerson, deathNote);
    }

    public DeathDataFemale() {
    }

    public DeathDataFemale(Long id, DataType dataType) {
        super(id, dataType);
    }
}
