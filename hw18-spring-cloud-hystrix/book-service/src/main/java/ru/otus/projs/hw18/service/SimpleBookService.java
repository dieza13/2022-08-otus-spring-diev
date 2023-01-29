package ru.otus.projs.hw18.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.projs.hw18.exception.service.*;
import ru.otus.projs.hw18.model.Book;
import ru.otus.projs.hw18.model.dto.AlternativeBook;
import ru.otus.projs.hw18.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimpleBookService implements BookService {

    private final BookRepository bookRepository;

    @HystrixCommand(commandKey = "commonBookListKey", fallbackMethod = "sorryListOfBookFallback")
    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        try {
            return bookRepository.findAll();
        } catch (Exception e) {
            throw new FindAllBookException(e);
        }
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    }, fallbackMethod = "sorryBookFallback")
    @Transactional(readOnly = true)
    @Override
    public Book getBookById(Long id) {
        getDelayOnEvenId(id);
        try {
            return bookRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            throw new GetBookByIdException(id, e);
        }
    }

    @HystrixCommand(commandKey = "commonBookListKey")
    @Transactional
    @Override
    public Book saveBook(Book book) {
        try {
            return bookRepository.save(book);
        } catch (Exception e) {
            throw new SaveBookException(book, e);
        }
    }

    @HystrixCommand(commandKey = "commonBookListKey")
    @Transactional
    @Override
    public void deleteBook(long id) {
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeleteBookException(id, e);
        }
    }

    @HystrixCommand(commandKey = "commonBookListKey", fallbackMethod = "sorryListOfBookByAuthorFallback")
    @Transactional(readOnly = true)
    @Override
    public List<Book> getBookByAuthor(long id) {
        getDelayOnEvenId(id);
        try {
            return bookRepository.getByAuthorId(id);
        } catch (Exception e) {
            throw new GetBookByAuthorException(id, e);
        }
    }



    private List<Book> sorryListOfBookFallback() {
        return List.of(getBookById(1L));
    }

    private List<Book> sorryListOfBookByAuthorFallback(long id) {
        return findAll()
                .stream()
                .filter(p -> p.getAuthor().getId() == id)
                .findFirst().stream()
                .collect(Collectors.toList());
    }

    private Book sorryBookFallback(Long id) {
        return new AlternativeBook(getBookById(1L), "К сожалению, Вашу книгу с id " + id + "  пока не нашли, " +
                "но мы ищем, пока можем предложить другую");
    }

    @SneakyThrows
    private void getDelayOnEvenId(Long id) {
        if (id == null || id % 2 == 0) {
            Thread.sleep(2500);
        }
    }

}
