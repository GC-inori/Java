package gc.webPro.pojo;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public class Pages<T> {

    public static final int PAGE_SIZE = 4;
    public static final int PAGE_RANGE = 5;

    private int pageNum;
    private int pageSize = PAGE_SIZE;
    private int pageCountTotal;//一共有多少记录
    private int pageRecordTotal;//一共分成多少页

    private int pageRange = PAGE_RANGE;

    private List<T> pageBook;

    private String pageUrl;

    public Pages() {

    }

    public int getPageRange() {
        return pageRange;
    }

    public void setPageRange(int pageRange) {
        if (pageRecordTotal < pageRange) //如果总页数小于分页范围 必须重新定义分页范围
            pageRange = pageRecordTotal;
        this.pageRange = pageRange;
    }

    public int[] getPageList() {
        return getPageRangeArray();
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        if (pageNum < 1)
            pageNum = 1;

        if (pageNum > pageRecordTotal)//这个总页数必须在目标页数前面执行 不然是总页数是0
            pageNum = pageRecordTotal;

        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCountTotal() {
        return pageCountTotal;
    }

    public void setPageCountTotal(int pageCountTotal) {
        this.pageCountTotal = pageCountTotal;
    }

    public int getPageRecordTotal() {
        return pageRecordTotal;
    }

    public void setPageRecordTotal(int pageRecordTotal) {
        this.pageRecordTotal = pageRecordTotal;
    }

    public List<T> getPageBook() {
        return pageBook;
    }

    public void setPageBook(List<T> pageBook) {
        this.pageBook = pageBook;
    }

    private int[] getPageRangeArray(){

        if(pageRange == 0)
            return null;

        int rangeCount = 0;
        int prePage, nextPage;
        prePage = nextPage = pageNum;

        int[] pageList = new int[pageRange];

        pageList[0] = pageNum;
        while (rangeCount < pageRange - 1) {
            if (prePage - 1 > 0) {
                pageList[++rangeCount] = --prePage;
            }
            if (nextPage + 1 <= pageRecordTotal && rangeCount < pageRange - 1) {
                pageList[++rangeCount] = ++nextPage;
            }
        }
        Arrays.sort(pageList);

        return pageList;
    }
}
