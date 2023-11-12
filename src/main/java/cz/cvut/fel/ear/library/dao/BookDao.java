package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.User;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDao extends BaseDao<Book>{

    public BookDao() {
        super(Book.class);
    }

    public List<Book> findAllFromUser(User user) {
        return em.createNamedQuery("Book.findByUser",Book.class).setParameter("idUser",user.getId()).getResultList();
    }

    public List<Book> findAllFromUser(User user, boolean visibleOnly) {
        if (visibleOnly) {
            return em.createNamedQuery("Book.findVisibleByUser",Book.class).setParameter("idUser",user.getId()).getResultList();
        } else {
            return findAllFromUser(user);
        }
    }

    public List<Book> findByName(String name) {
        return em.createNamedQuery("Book.findByName",Book.class).setParameter("name","%"+name+"%").getResultList();
    }

    public List<Book> findByName(String name, boolean visibleOnly) {
        if (visibleOnly) {
            return em.createNamedQuery("Book.findVisibleByName", Book.class).setParameter("name","%"+name+"%").getResultList();
        } else {
            return findByName(name);
        }
    }

    public List<Book> findAllVisible(boolean visibleOnly) {
        if (visibleOnly) {
            return em.createNamedQuery("Book.findVisibleOnly", Book.class).getResultList();
        } else {
            return findAll();
        }
    }
}
