package ua.mlgmag.springboot.dota2rest.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mlgmag.springboot.dota2rest.definition.HeroService;
import ua.mlgmag.springboot.dota2rest.model.Hero;
import ua.mlgmag.springboot.dota2rest.repository.HeroRepository;

import java.util.List;

@Service
@Slf4j
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;

    @Autowired
    public HeroServiceImpl(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Override
    public void save(Hero hero) {
        log.info("save {}", hero);
        heroRepository.save(hero);
    }

    @Override
    public void delete(Hero hero) {
        log.info("delete {}", hero);
        heroRepository.delete(hero);
    }

    @Override
    public Hero findById(Integer integer) {
        log.info("findById {}", integer);
        return heroRepository.findById(integer)
                .orElseThrow(() -> new IllegalStateException("Hero with id \"" + integer + "\" not found"));
    }

    @Override
    public List<Hero> findAll() {
        log.info("findAll");
        return heroRepository.findAll();
    }

    @Override
    public void saveAllEntities(List<Hero> heroes) {
        log.info("saveAllEntities {}", heroes);
        heroRepository.saveAll(heroes);
    }
}
