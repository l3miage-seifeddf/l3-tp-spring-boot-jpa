package fr.uga.l3miage.library.data.domain;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@NamedQueries(
    {
        @NamedQuery(name = "find-current-borrow", query = "select b from Borrow b join b.borrower bw where bw.id = :userId and b.finished=false"),
        @NamedQuery(name = "count-books-borrowed-user", query = "select sum(size(b.books)) from Borrow b join b.borrower bw where bw.id = :userId"),
        @NamedQuery(name = "count-current-books-borrowed-user", query = "select sum(size(b.books)) from Borrow b join b.borrower bw where bw.id = :userId and b.finished=false"),
        @NamedQuery(name = "find-all-late-borrows", query = "SELECT b FROM Borrow b WHERE b.requestedReturn < :currentDate ORDER BY b.requestedReturn"),
        @NamedQuery(name = "late-borrows-in-days", query = "SELECT b FROM Borrow b WHERE b.requestedReturn <= :dueDate")
    }
)
@Entity
@Table(name = "Borrow")
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToMany
    @JoinColumn(name = "borrow_id")
    private List<Book> books;

    @Temporal(TemporalType.DATE)
    private Date start;

    @Temporal(TemporalType.DATE)
    private Date requestedReturn;

    @OneToOne
    private User borrower;

    @OneToOne
    private Librarian librarian;

    @Column(name = "finished")
    private boolean finished;

    public Long getId() {
        return id;
    }


    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getRequestedReturn() {
        return requestedReturn;
    }

    public void setRequestedReturn(Date end) {
        this.requestedReturn = end;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrow borrow = (Borrow) o;
        return finished == borrow.finished && Objects.equals(id, borrow.id) && Objects.equals(books, borrow.books) && Objects.equals(start, borrow.start) && Objects.equals(requestedReturn, borrow.requestedReturn) && Objects.equals(borrower, borrow.borrower) && Objects.equals(librarian, borrow.librarian);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, books, start, requestedReturn, borrower, librarian, finished);
    }
}
