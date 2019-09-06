package ru.filippov.neatvue.service.death.table;

import ru.filippov.neatvue.domain.death.DeathNote;

import java.util.ArrayList;
import java.util.List;

public class DeathTableCreator {

    public static DeathTable createTableForOneYearAndSexCategory(List<DeathNote> deathNote){
        List<ru.filippov.neatvue.service.death.table.DeathNote> maleDeathNotes = new ArrayList<>(deathNote.size());
        List<ru.filippov.neatvue.service.death.table.DeathNote> femaleDeathNotes = new ArrayList<>(deathNote.size());

        /*deathNote.add(DeathNote.builder()
                .age(deathNote.get(0).getAge())
                .birthYear(deathNote.get(0).getBirthYear())
                .deathDataMale(new DeathDataMale(
                        null,
                        DataType.ACTUAL,
                        0,
                        0,
                        null))
                .build()
        );*/


        DeathNote lastDeathNote = deathNote.get(deathNote.size() - 1);

        ru.filippov.neatvue.service.death.table.DeathNote prevDeathNoteMale = DeathNoteForOneYear.builder().e(0).d(lastDeathNote.getDeathDataMale().getNumberOfLiving()).Lx(0).l(0).p(0).q(1).T(0).x((byte)0).build();
        ru.filippov.neatvue.service.death.table.DeathNote prevDeathNoteFemale = DeathNoteForOneYear.builder().e(0).d(lastDeathNote.getDeathDataFemale().getNumberOfLiving()).Lx(0).l(0).p(0).q(1).T(0).x((byte)0).build();

        for (int i = deathNote.size()-1; i >= 0 ; i--) {

           /* prevDeathNoteMale = createDeathNote(deathNote.get(i).getAge(), deathNote.get(i).getDeathDataMale().getNumberOfLivingMale(), prevDeathNoteMale);
            maleDeathNotes.add( prevDeathNoteMale );

            prevDeathNoteFemale = createDeathNote(deathNote.get(i).getAge(), deathNote.get(i).getDeathDataMale().getNumberOfLivingFemale(), prevDeathNoteFemale);
            femaleDeathNotes.add( prevDeathNoteFemale );*/


        }

        return new DeathTable(lastDeathNote.getAge() ,femaleDeathNotes, maleDeathNotes, null, null, null);

    }

    private static ru.filippov.neatvue.service.death.table.DeathNote createDeathNote(int age, int numberOfLiving, ru.filippov.neatvue.service.death.table.DeathNote prevDeathNote) {
        DeathNoteForOneYear deathNoteForOneYear = DeathNoteForOneYear.builder()
                .x((byte)age)
                .l(numberOfLiving)
                .build();



        deathNoteForOneYear.setD(numberOfLiving - prevDeathNote.getl());

        deathNoteForOneYear.setQ((numberOfLiving == 0) ? 1.0 : ((double)deathNoteForOneYear.getD())/numberOfLiving);

        deathNoteForOneYear.setP(1.0 - deathNoteForOneYear.getQ());

        deathNoteForOneYear.setLx((int)Math.round(((double)(numberOfLiving+prevDeathNote.getl()))/2));

        deathNoteForOneYear.setT(deathNoteForOneYear.getLx()+prevDeathNote.getT());

        deathNoteForOneYear.setE((double) ((deathNoteForOneYear.getl() == 0.0 ? 0.0 : deathNoteForOneYear.getT()/deathNoteForOneYear.getl())));

        return deathNoteForOneYear;
    }





}
