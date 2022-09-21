package gc.webPro.pojo;

/**
 * @Author: GC
 * @Description: BookBean
 * @Version: 1.0
 */
public class Book {
    private int id;
    private String name;
    private String author;
    private double price;
    private int sales;
    private int stock;
    private String img_path = "static/img/default.jpg";

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        if (img_path == null || "".equals(img_path))
            return;
        this.img_path = img_path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (Double.compare(book.price, price) != 0) return false;
        if (sales != book.sales) return false;
        if (stock != book.stock) return false;
        if (!name.equals(book.name)) return false;
        if (!author.equals(book.author)) return false;
        return img_path.equals(book.img_path);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + author.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + sales;
        result = 31 * result + stock;
        result = 31 * result + img_path.hashCode();
        return result;
    }
}
