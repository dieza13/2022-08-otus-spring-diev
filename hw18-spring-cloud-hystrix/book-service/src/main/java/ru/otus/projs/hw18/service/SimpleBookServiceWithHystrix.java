package ru.otus.projs.hw18.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.otus.projs.hw18.model.Book;
import ru.otus.projs.hw18.model.dto.AlternativeBook;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimpleBookServiceWithHystrix implements BookService {

    private final BookService bookService;

    @HystrixCommand(commandKey = "commonBookListKey", fallbackMethod = "sorryListOfBookFallback")
    @Override
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    }, fallbackMethod = "sorryBookFallback")
    @Override
    public Book getBookById(Long id) {
        getDelayOnEvenId(id);
        return bookService.getBookById(id);
    }

    @HystrixCommand(commandKey = "commonBookListKey")
    @Override
    public Book saveBook(Book book) {
        return bookService.saveBook(book);
    }

    @HystrixCommand(commandKey = "commonBookListKey")
    @Override
    public void deleteBook(long id) {
        bookService.deleteBook(id);
    }

    @HystrixCommand(commandKey = "commonBookListKey", fallbackMethod = "sorryListOfBookByAuthorFallback")
    @Override
    public List<Book> getBookByAuthor(long id) {
        getDelayOnEvenId(id);
        return bookService.getBookByAuthor(id);
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
