package ru.filippov.neatvue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.filippov.neatvue.domain.death.DeathNote;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeathNoteRepository extends JpaRepository<DeathNote, Long> {
    Optional<List<DeathNote>> findAllByAge( byte age);
    Optional<List<DeathNote>> findAllByAgeBetween( byte ageFrom, byte ageTo);
    Optional<List<DeathNote>> findAllByBirthYearAndAgeBetween(short birthYear, byte ageFrom, byte ageTo);
    Optional<List<DeathNote>> findAllByBirthYearBetweenAndAgeBetween(short birthYearFrom, short birthYearTo, byte ageFrom, byte ageTo);
    Optional<List<DeathNote>> findAllByBirthYear( short birthYear);
    Optional<List<DeathNote>> findAllByBirthYearBetween( short birthYearFrom, short birthYearTo);
    Optional<DeathNote> findByBirthYearAndAge(short birthYear, byte age);

    @Query("SELECT DISTINCT u.birthYear FROM DeathNote u ORDER BY u.birthYear")
    Optional<List<Short>> findDistinctBirthYears();

    @Query("SELECT DISTINCT u.age FROM DeathNote u ORDER BY u.age")
    Optional<List<Byte>> findDistinctAges();

}
