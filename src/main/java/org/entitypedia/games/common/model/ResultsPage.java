package org.entitypedia.games.common.model;

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
     * List of items in the page.
     */
    List<T> items;

    public ResultsPage(int pageNo, int pageSize) {
        super(pageNo, pageSize);
    }

    public ResultsPage(Page page) {
        super(page.getPageNo(), page.getPageSize());
    }

    public ResultsPage(int pageNo, int pageSize, long overallCount) {
        this(pageNo, pageSize);
        this.overallCount = overallCount;

        if (getPagesCount() <= pageNo) {
            throw new IndexOutOfBoundsException("pageNo is greater than the available number of pages:" + getPagesCount());
        }
    }

    public ResultsPage(Page page, long overallCount, long pagesCount) {
        this(page.getPageNo(), page.getPageSize(), overallCount);
    }

    public long getOverallCount() {
        return overallCount;
    }

    public long getPagesCount() {
        return (long) Math.ceil(overallCount / (double) pageSize);
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}