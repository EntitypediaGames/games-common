package org.entitypedia.games.common.model;

/**
 * Bean used to represent the how a result set page should be retrieved.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class Page {

    /**
     * Page index, 0-based.
     */
    protected int pageNo;

    /**
     * The maximum number of results to be returned in the page.
     */
    protected int pageSize;

    public static Page create() {
        return new Page(0, 20);
    }

    public static Page create(Integer pageNo, Integer pageSize) {
        if (null == pageSize) {
            pageSize = 20;
        }
        if (null == pageNo) {
            pageNo = 0;
        }
        return new Page(pageNo, pageSize);
    }

    /**
     * Default constructor.
     *
     * @param pageNo   page index, 0-based
     * @param pageSize page size
     */
    public Page(int pageNo, int pageSize) {
        if (0 == pageSize) {
            pageSize = 20;
        }
        if (pageNo < 0) {
            throw new IllegalArgumentException("pageNo must be a positive integer.");
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("pageSize must be a positive integer.");
        }
        if (100 < pageSize) {
            throw new IllegalArgumentException("pageSize must be less than 100.");
        }

        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    /**
     * Page index, 0-based
     *
     * @return page index, 0-based
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * Returns the maximum number of results allowed by this page.
     *
     * @return The maximum number of results allowed by this page
     */
    public int getPageSize() {
        return pageSize;
    }
}