package ru.filippov.neatvue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.filippov.neatvue.domain.death.DeathNote;
import ru.filippov.neatvue.domain.death.Location;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeathNoteRepository extends JpaRepository<DeathNote, Long> {
    Optional<List<DeathNote>> findAllByLocationAndAge(Location location, byte age);
    Optional<List<DeathNote>> findAllByLocationAndAgeBetween( Location location, byte ageFrom, byte ageTo);
    Optional<List<DeathNote>> findAllByLocationAndBirthYearAndAgeBetween(Location location, short birthYear, byte ageFrom, byte ageTo);

    Optional<List<DeathNote>> findAllByLocationAndBirthYearInAndAgeBetween( Location location, List<Short> birthYears, byte ageFrom, byte ageTo);
    Optional<List<DeathNote>> findAllByLocationAndBirthYearBetweenAndAgeIn( Location location, short birthYearFrom, short birthYearTo, List<Byte> ages);
    Optional<List<DeathNote>> findAllByLocationAndBirthYearBetweenAndAgeBetween( Location location, short birthYearFrom, short birthYearTo, byte ageFrom, byte ageTo);
    Optional<List<DeathNote>> findAllByLocationAndBirthYearInAndAgeIn( Location location, List<Short> birthYears, List<Byte> ages);


    @Query("SELECT DISTINCT u.birthYear FROM DeathNote u ORDER BY u.birthYear")
    Optional<List<Short>> findDistinctBirthYears();

    @Query("SELECT DISTINCT u.age FROM DeathNote u ORDER BY u.age")
    Optional<List<Byte>> findDistinctAges();

}
