package ru.otus.projs.hw13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.projs.hw13.model.Book;
import ru.otus.projs.hw13.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleBookService implements BookService {

    private final BookRepository bookRepository;
    private final MutableAclService aclService;

    @PostFilter("hasPermission(filterObject, 'READ')")
    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        try {
            return bookRepository.findAll();
        } catch (Exception e) {
            throw new FindAllBookException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Book getBookById(Long id) {
        try {
            return bookRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            throw new GetBookByIdException(id, e);
        }
    }

    @Transactional
    @Override
    public Book saveBook(Book book) {
        try {
            Book resBook = bookRepository.save(book);
            defaultCreatePermissionForAdult(resBook);
            return resBook;
        } catch (Exception e) {
            throw new SaveBookException(book, e);
        }
    }

    @Transactional
    @Override
    public void deleteBook(long id) {
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeleteBookException(id, e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBookByAuthor(long id) {
        try {
            return bookRepository.getByAuthorId(id);
        } catch (Exception e) {
            throw new GetBookByAuthorException(id, e);
        }
    }

    /**
     * Книги может создавать только MANAGER, т е Печкин, книги создаются по умолчанию с доступом для взрослых ADULT
     * @param book
     */
    private void defaultCreatePermissionForAdult(Book book) {
        final Sid sid = new GrantedAuthoritySid("ROLE_ADULT");
        ObjectIdentity oi = new ObjectIdentityImpl(book.getClass(), book.getId());
        MutableAcl acl = null;
        try {
            acl = (MutableAcl) aclService.readAclById(oi);
        } catch (Exception e) {
            acl = aclService.createAcl(oi);
        }
        Permission permission = BasePermission.READ;
        acl.insertAce(acl.getEntries().size(), permission, sid, true);
        aclService.updateAcl(acl);
    }

}
