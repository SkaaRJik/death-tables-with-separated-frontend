package ru.filippov.neatvue.service.death.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeathTable {

    private short birthAge;

    private List<DeathNote> deathNotesFemale;
    private List<DeathNote> deathNotesMale;
    private List<DeathNote> deathNotesTotal;
    private List<DeathNote> deathNotesVillagers;
    private List<DeathNote> deathNotesСityDwellers;



}
