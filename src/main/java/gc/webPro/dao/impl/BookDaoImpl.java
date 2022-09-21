package gc.webPro.dao.impl;

import gc.webPro.dao.BookDao;
import gc.webPro.dao.basedao.BaseDao;
import gc.webPro.pojo.Book;

import java.sql.Connection;
import java.util.List;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public class BookDaoImpl extends BaseDao<Book> implements BookDao {

    private static final BookDaoImpl bookImpl = new BookDaoImpl();

    private BookDaoImpl() {

    }

    public static BookDaoImpl getUserDaoImplInstance() {
        return bookImpl;
    }

    @Override
    public boolean executeAdd(Connection conn, Book book) {
        String sql = "insert into t_book(name,author,price,sales,stock,img_path) value(?,?,?,?,?,?)";
        return executeCommonQuery(conn, sql,
                book.getName(), book.getAuthor(), book.getPrice(),
                book.getSales(), book.getStock(), book.getImg_path());
    }

    @Override
    public boolean executeUpdate(Connection conn, Book book) {
        String sql = "update t_book set name = ?,author = ?,price = ?,sales =?,stock = ?,img_path =? where id = ? ";
        return executeCommonQuery(conn, sql,
                book.getName(), book.getAuthor(), book.getPrice(),
                book.getSales(), book.getStock(), book.getImg_path(),
                book.getId());
    }

    @Override
    public boolean executeDelete(Connection conn, Integer id) {
        String sql = "delete from t_book where id = ?";
        return executeCommonQuery(conn, sql, id);
    }

    @Override
    public Book getSingle(Connection conn, Integer id) {
        String sql = "select name,author,price,sales,stock from t_book where id = ?";
        return getSingleInstance(conn, sql, id);
    }

    @Override
    public List<Book> executeListAll(Connection conn) {
        String sql = "select id,name,author,price,sales,stock from t_book";
        return executeCustomQueryList(conn, sql);
    }

    @Override
    public int getCount(Connection conn) {
        String sql = "select count(*) from t_book";
        Long value = getValue(conn, sql);
        return value.intValue();
    }

    public int getCount(Connection conn, double minPrice, double maxPrice) {
        String sql = "select count(*) from t_book where price between ? and ?";
        Long value = getValue(conn, sql,minPrice,maxPrice);
        return value.intValue();
    }


    @Override
    public List<Book> getPageList(Connection connection, Integer pageNum, Integer pageSize) {
        String sql = "select id,name,author,price,sales,stock from t_book limit ?,?";
        return executeCustomQueryList(connection, sql, (pageNum - 1) * pageSize, pageSize);
    }

    public List<Book> getPageList(Connection connection, Integer pageNum, Integer pageSize, double minPrice, double maxPrice) {
        String sql = "select id,name,author,price,sales,stock from t_book where price between ? and ? limit ?,?";
        return executeCustomQueryList(connection, sql, minPrice, maxPrice, (pageNum - 1) * pageSize, pageSize);
    }
}
