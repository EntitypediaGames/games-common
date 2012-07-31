package org.entitypedia.games.gameframework.common.model;

import java.util.List;

/**
 * Page with results.
 *
 * @param <T> type of result item
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class ResultsPage<T> extends Page {

    /**
     * Overall count of items in the result set.
     */
    private long overallCount;

    /**
     * Overall count of pages in the result set.
     */
    private long pagesCount;

    /**
     * List of items in the page.
     */
    List<T> items;

    public ResultsPage(int pageNo, int pageSize) {
        super(pageNo, pageSize);
    }

    public ResultsPage(Page page) {
        super(page.getPageNo(), page.getPageSize());
    }

    public ResultsPage(int pageNo, int pageSize, long overallCount, long pagesCount) {
        this(pageNo, pageSize);
        this.overallCount = overallCount;
        this.pagesCount = pagesCount;
    }

    public ResultsPage(Page page, long overallCount, long pagesCount) {
        this(page);
        this.overallCount = overallCount;
        this.pagesCount = pagesCount;
    }

    public long getOverallCount() {
        return overallCount;
    }

    public long getPagesCount() {
        return pagesCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}