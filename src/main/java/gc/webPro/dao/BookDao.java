package gc.webPro.dao;


import gc.webPro.pojo.Book;

import java.sql.Connection;
import java.util.List;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public interface BookDao {

    boolean executeAdd(Connection conn, Book book);

    boolean executeUpdate(Connection conn, Book boo);

    boolean executeDelete(Connection conn, Integer id);

    Book getSingle(Connection conn, Integer id);

    List<Book> executeListAll(Connection conn);

    int getCount(Connection conn);

    List<Book> getPageList(Connection connection,Integer pageNum,Integer PageSize);

}


