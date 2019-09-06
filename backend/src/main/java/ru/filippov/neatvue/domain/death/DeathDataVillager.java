package ru.filippov.neatvue.domain.death;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "death_data_villager")
@Entity
@Data
@SuperBuilder(toBuilder = true)
public class DeathDataVillager extends DeathData{

    public DeathDataVillager(@NotNull DataType dataType, @NotNull Integer numberOfLiving, Integer numberOfDying, Double probabilityToDie, Double probabilityToSurvive, Integer averageLiving, Integer agesToLiveForPopulation, Double expectedAgesOfAPerson, DeathNote deathNote) {
        super(dataType, numberOfLiving, numberOfDying, probabilityToDie, probabilityToSurvive, averageLiving, agesToLiveForPopulation, expectedAgesOfAPerson, deathNote);
    }

    public DeathDataVillager() {
    }

    public DeathDataVillager(Long id, DataType dataType) {
        super(id, dataType);
    }
}
