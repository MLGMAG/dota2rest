package ua.mlgmag.springboot.dota2rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.mlgmag.springboot.dota2rest.model.Hero;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Integer> {
}
