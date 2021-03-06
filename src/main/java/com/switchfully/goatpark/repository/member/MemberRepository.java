package com.switchfully.goatpark.repository.member;

import com.switchfully.goatpark.domain.person.Person;
import com.switchfully.goatpark.exception.PersonDoesNotExistException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository

public class MemberRepository {

    private final EntityManager manager;

    public MemberRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Transactional
    public Person registerMember(Person person) {
        manager.persist(person);
        String sql = "SELECT p " +
                "FROM Person p " +
                "WHERE p.emailAddress = :email";
        return manager.createQuery(sql, Person.class)
                .setParameter("email", person.getEmailAddress())
                .getSingleResult();
    }

    public List<Person> getAllMembers() {
        String sql = "SELECT p " +
                "FROM Person p " +
                "WHERE p.membership IS NOT NULL";
        return manager.createQuery(sql, Person.class)
                .getResultList();
    }

    public Person getMemberById(int personId) {
        String sql = "select p " +
                "from Person p" +
                " where p.id  = :personId";
        return manager.createQuery(sql, Person.class)
                .setParameter("personId", personId)
                .getResultList().stream()
                .findFirst()
                .orElseThrow(() -> new PersonDoesNotExistException("Could not find this person"));
    }
}
