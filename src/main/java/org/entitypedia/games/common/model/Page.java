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
    private int pageNo;

    /**
     * The maximum number of results to be returned in the page.
     */
    private int pageSize;

    /**
     * Default constructor.
     *
     * @param pageNo   page index, 0-based
     * @param pageSize page size
     */
    public Page(int pageNo, int pageSize) {
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