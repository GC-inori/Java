package gc.webPro.service.impl;

import gc.webPro.dao.impl.BookDaoImpl;
import gc.webPro.pojo.Book;
import gc.webPro.pojo.Pages;
import gc.webPro.service.BookService;
import gc.webPro.utils.JDBCUtil;

import java.sql.Connection;
import java.util.List;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public class BookServiceImpl implements BookService {

    private static final BookServiceImpl bookServiceImpl = new BookServiceImpl();

    private static final BookDaoImpl bookDaoImpl = BookDaoImpl.getUserDaoImplInstance();

    private BookServiceImpl() {

    }

    public static BookServiceImpl getBookServiceImplInstance() {
        return bookServiceImpl;
    }

    @Override
    public boolean addBook(Book book) {
        Connection conn = null;
        boolean isSuccess = false;
        try {
            conn = JDBCUtil.getMysqlConnect();
            conn.setAutoCommit(false);

            isSuccess = bookDaoImpl.executeAdd(conn, book);

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            JDBCUtil.closeResources(conn, null, null);
        }
        return isSuccess;
    }

    @Override
    public boolean updateBook(Book book) {
        Connection conn = null;
        boolean isSuccess = false;
        try {
            conn = JDBCUtil.getMysqlConnect();
            conn.setAutoCommit(false);

            isSuccess = bookDaoImpl.executeUpdate(conn, book);

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            JDBCUtil.closeResources(conn, null, null);
        }
        return isSuccess;
    }

    @Override
    public boolean deleteBook(Integer id) {
        Connection conn = null;
        boolean isSuccess = false;
        try {
            conn = JDBCUtil.getMysqlConnect();
            conn.setAutoCommit(false);

            isSuccess = bookDaoImpl.executeDelete(conn, id);

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResources(conn, null, null);
        }
        return isSuccess;
    }

    @Override
    public Book getSingleBookById(Integer id) {
        Connection conn = null;
        Book book = null;
        try {
            conn = JDBCUtil.getMysqlConnect();
            conn.setAutoCommit(false);

            book = bookDaoImpl.getSingle(conn, id);

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResources(conn, null, null);
        }
        return book;
    }

    @Override
    public List<Book> listAllBook() {
        Connection conn = null;
        List<Book> list = null;
        try {
            conn = JDBCUtil.getMysqlConnect();
            conn.setAutoCommit(false);

            list = bookDaoImpl.executeListAll(conn);

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResources(conn, null, null);
        }
        return list;
    }


    @Override
    public Pages<Book> getBookPage(Integer pageNum, Integer pageSize, Integer pageRange, String url, Double minPrice, Double maxPrice) {
        Connection conn = null;
        List<Book> list;
        Pages<Book> pages = null;
        try {
            conn = JDBCUtil.getMysqlConnect();
            conn.setAutoCommit(false);

            pages = new Pages<>();
            int count = getBookCount(conn, minPrice, maxPrice);

            pages.setPageCountTotal(count);
            pages.setPageSize(pageSize);

            count = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
            pages.setPageRecordTotal(count);//先检查总页数

            pages.setPageNum(pageNum);//录入当前页数 里面有检测

            pages.setPageRange(pageRange);

            StringBuilder stringBuilder = new StringBuilder(url);

            if (minPrice != null && maxPrice != null)
                stringBuilder.append("&minPrice=").append(minPrice).append("&maxPrice=").append(maxPrice);

            pages.setPageUrl(stringBuilder.toString());

            list = getPageList(conn, pages.getPageNum(), pageSize, minPrice, maxPrice);//检测完再交给dao处理

            pages.setPageBook(list);

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResources(conn, null, null);
        }
        return pages;
    }


    private int getBookCount(Connection conn) throws Exception {
        int count = 0;

        count = bookDaoImpl.getCount(conn);

        return count;

    }

    private List<Book> getPageList(Connection conn, Integer pageNum, Integer pageSize) throws Exception {
        List<Book> list;

        list = bookDaoImpl.getPageList(conn, pageNum, pageSize);
        return list;
    }

    private int getBookCount(Connection conn, Double minPrice, Double maxPrice) throws Exception {

        int count = 0;
        if (minPrice != null && maxPrice != null)
            count = bookDaoImpl.getCount(conn, minPrice, maxPrice);
        else
            count = getBookCount(conn);
        return count;

    }

    private List<Book> getPageList(Connection conn, Integer pageNum, Integer pageSize, Double minPrice, Double maxPrice) throws Exception {
        List<Book> list;
        if (minPrice != null && maxPrice != null) {
            list = bookDaoImpl.getPageList(conn, pageNum, pageSize, minPrice, maxPrice);
            list.sort((book1, book2) -> Double.compare(book1.getPrice(), book2.getPrice()) == 0 ? book1.getSales() > book2.getSales() ? -1 : 1 : Double.compare(book1.getPrice(), book2.getPrice()));
        }
        else
            list = getPageList(conn, pageNum, pageSize);

        return list;
    }
}
