package fr.uga.l3miage.library.data.repo;

import fr.uga.l3miage.library.data.domain.Borrow;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Repository
public class BorrowRepository implements CRUDRepository<String, Borrow> {

    private final EntityManager entityManager;

    @Autowired
    public BorrowRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Borrow save(Borrow entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Borrow get(String id) {
        return entityManager.find(Borrow.class, id);
    }

    @Override
    public void delete(Borrow entity) {
        entityManager.remove(entity);
    }

    @Override
    public List<Borrow> all() {
        return entityManager.createQuery("from Borrow", Borrow.class).getResultList();
    }

 /**
     * Trouver des emprunts en cours pour un emprunteur donné
     *
     * @param userId l'id de l'emprunteur
     * @return la liste des emprunts en cours
     */
    public List<Borrow> findInProgressByUser(Long userId) {
        
        return entityManager.createNamedQuery("find-current-borrow", Borrow.class)
        .setParameter("userId", userId).getResultList();
    }

 /**
     * Compte le nombre total de livres emprunté par un utilisateur.
     *
     * @param userId l'id de l'emprunteur
     * @return le nombre de livre
     */
    public int countBorrowedBooksByUser(Long userId) {
        int bo = entityManager.createNamedQuery("count-books-borrowed-user",Long.class)
        .setParameter("userId", userId).getSingleResult().intValue();
        return bo;
    }

 /**
     * Compte le nombre total de livres non rendu par un utilisateur.
     *
     * @param userId l'id de l'emprunteur
     * @return le nombre de livre
     */
    public int countCurrentBorrowedBooksByUser(Long userId) {
        int bo = entityManager.createNamedQuery("count-current-books-borrowed-user",Long.class)
        .setParameter("userId", userId).getSingleResult().intValue();
        return bo;
    }

/**
     * Recherche tous les emprunt en retard trié
     *
     * @return la liste des emprunt en retard
     */
    public List<Borrow> foundAllLateBorrow() {
        LocalDate localDate = LocalDate.now();

        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());


        return entityManager.createNamedQuery("find-all-late-borrows", Borrow.class)
        .setParameter("currentDate", date).getResultList();
    }

/**
     * Calcul les emprunts qui seront en retard entre maintenant et x jours.
     *
     * @param days le nombre de jour avant que l'emprunt soit en retard
     * @return les emprunt qui sont bientôt en retard
     */
    public List<Borrow> findAllBorrowThatWillLateWithin(int days) {
        LocalDate localDate = LocalDate.now();
        
        LocalDate dueDate = localDate.plusDays(days);
        Date date = Date.from(dueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return entityManager.createNamedQuery("late-borrows-in-days", Borrow.class).setParameter("dueDate", date).getResultList();
    }

}
