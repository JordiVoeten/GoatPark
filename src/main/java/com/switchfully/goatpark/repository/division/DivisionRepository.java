package com.switchfully.goatpark.repository.division;

import com.switchfully.goatpark.domain.division.Division;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
        String sql = "select d " +
                "from Division d " +
                "where d.name = :name";
        return entityManager.createQuery(sql, Division.class)
                .setParameter("name", name)
                .getResultList().stream()
                .findFirst()
                .orElse(null);
    }

    public Division findDivisionByOriginalName(String originalName) {
        String sql = "select d " +
                "from Division d " +
                "where d.originalName = :originalName";
        return entityManager.createQuery(sql, Division.class)
                .setParameter("originalName", originalName)
                .getResultList().stream()
                .findFirst()
                .orElse(null);
    }

    public List<Division> getAllDivisions() {
        String sql = "SELECT d FROM Division d";
        return entityManager.createQuery(sql, Division.class)
                .getResultList();
    }

    public Division getById(int id) {
        String sql = "SELECT d FROM Division d WHERE d.id = :id";
        return entityManager.createQuery(sql, Division.class)
                .setParameter("id", id)
                .getResultList().stream()
                .findFirst()
                .orElse(null);
    }
}
