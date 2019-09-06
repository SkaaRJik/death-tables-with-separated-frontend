SELECT *
FROM (death_tables.public.death_note
        left join death_tables.public.death_data_male
        on death_tables.public.death_note.fk_death_data_male_id = death_tables.public.death_data_male.id)
        left join death_tables.public.death_data_female
        on death_tables.public.death_note.fk_death_data_female_id = death_tables.public.death_data_female.id
WHERE (death_tables.public.death_note.birth_year between 2000-27 and 2000-18)
        AND
        (death_tables.public.death_note.age between 18 and 27)