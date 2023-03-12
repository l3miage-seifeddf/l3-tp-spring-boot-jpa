package fr.uga.l3miage.library.data.repo;

import fr.uga.l3miage.library.data.domain.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Calendar;
import java.util.List;

@Repository
public class UserRepository implements CRUDRepository<String, User> {

    private final EntityManager entityManager;

    @Autowired
    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User save(User entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public User get(String id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void delete(User entity) {
        entityManager.remove(entity);
    }

    @Override
    public List<User> all() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

/**
     * Trouve tous les utilisateurs ayant plus de l'age passé
     * @param age l'age minimum de l'utilisateur
     * @return
     */
    public List<User> findAllOlderThan(int age) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -age);
        Date dateLimite = cal.getTime();
        String jpql = "select u from User u where u.birth <= :dateLimite";
        return entityManager.createQuery(jpql, User.class)
        .setParameter("dateLimite", dateLimite)
        .getResultList();
    }

}
