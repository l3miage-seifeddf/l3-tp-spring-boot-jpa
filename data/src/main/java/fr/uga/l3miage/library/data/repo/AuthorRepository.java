package fr.uga.l3miage.library.data.repo;

import fr.uga.l3miage.library.data.domain.Author;
import fr.uga.l3miage.library.data.domain.Book;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorRepository implements CRUDRepository<Long, Author> {

    private final EntityManager entityManager;

    @Autowired
    public AuthorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Author save(Author author) {
        entityManager.persist(author);
        return author;
    }

    @Override
    public Author get(Long id) {
        return entityManager.find(Author.class, id);
    }


    @Override
    public void delete(Author author) {
        entityManager.remove(author);
    }

/**
     * Renvoie tous les auteurs
     *
     * @return une liste d'auteurs trié par nom
     */
    @Override
    public List<Author> all() {
        String jpql = "select a from Author a order by a.fullName";
        List<Author> res = entityManager.createQuery(jpql, Author.class).getResultList();
        return res;
    }

/**
     * Recherche un auteur par nom (ou partie du nom) de façon insensible  à la casse.
     *
     * @param namePart tout ou partie du nomde l'auteur
     * @return une liste d'auteurs trié par nom
     */
    public List<Author> searchByName(String namePart) {
        String jpql = "select a from Author a where a.fullName ilike concat('%',:namePart,'%')";
        List<Author> res = entityManager.createQuery(jpql, Author.class)
        .setParameter("namePart",namePart)
        .getResultList();
        return res;
    }

/**
     * Recherche si l'auteur a au moins un livre co-écrit avec un autre auteur
     *
     * @return true si l'auteur partage
     */
    public boolean checkAuthorByIdHavingCoAuthoredBooks(long authorId) {
        boolean found = false;
        String jpql = "select a from Author a where a.id = :authorId";
        List<Author> res = entityManager.createQuery(jpql, Author.class)
        .setParameter("authorId",authorId)
        .getResultList();
        Author auth = res.get(0);
        for (Book bo : auth.getBooks()) {
            if (bo.getAuthors().size() > 1){
                found = true;
                break;
            }
        }
        return found;
    }

}
