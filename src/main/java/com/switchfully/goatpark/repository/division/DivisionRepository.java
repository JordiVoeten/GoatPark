package com.switchfully.goatpark.repository.division;

import com.switchfully.goatpark.service.domain.division.Division;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class DivisionRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public Division save(Division divisionToSave) {
        entityManager.persist(divisionToSave);
        return divisionToSave;
    }

    public Division findDivisionByName(String name) {
        return entityManager.createQuery("select d from Division d where d.name = :name", Division.class)
                .setParameter("name", name)
                .getResultList().stream().findFirst().orElse(null);

    }

    public Division findDivisionByOriginalName(String originalName) {
        return entityManager.createQuery("select d from Division d where d.originalName = :originalName", Division.class)
                .setParameter("originalName", originalName)
                .getResultList().stream().findFirst().orElse(null);

    }
}
