package gc.webPro.service;

import gc.webPro.pojo.Book;
import gc.webPro.pojo.Pages;

import java.util.List;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public interface BookService {
    boolean addBook(Book book);

    boolean updateBook(Book book);

    boolean deleteBook(Integer id);

    Book getSingleBookById(Integer id);

    List<Book> listAllBook();

    Pages<Book> getBookPage(Integer pageNum, Integer PageSize, Integer pageRange, String url,Double minPrice, Double maxPrice);
}


