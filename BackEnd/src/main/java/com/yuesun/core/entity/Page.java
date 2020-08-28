
package com.yuesun.core.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Page<T> implements Serializable {
	
	private static final long serialVersionUID = 3412800354094709366L;
	public static final int DEFAULT_PAGE_SIZE=20;
	public static final String ORDER_ASC = "asc";
	public static final String ORDER_DESC = "desc";

	private Map<String, String> orderMap = null;
	
	private long page = 1;

	private long pageSize = DEFAULT_PAGE_SIZE;


	protected boolean autoCount = true;

	protected long total = -1;

	protected List<T> rows;

	public Page() {
		rows = Collections.emptyList();
	}
	public Page(int page, int pageSize) {
		this.page = page;
		this.pageSize = pageSize;
	}

	public long getStartIndex() {
		return page < 1 ? 0 : (page - 1) * pageSize;
	}

	public long getPageSize() {
		return pageSize;
	}

	public Map<String, String> getOrderMap() {
		return orderMap;
	}

	public void addOrder(String propertyName, String orderType) {
		if (orderMap == null)
			orderMap = new LinkedHashMap<String, String>();
		orderMap.put(propertyName, orderType);
	}

	public void addAscOrder(String propertyName) {
		if (orderMap== null)
			orderMap = new LinkedHashMap<String, String>();
		orderMap.put(propertyName,ORDER_ASC);
	}

	public void addDescOrder(String propertyName) {
		if (orderMap== null)
			orderMap = new LinkedHashMap<String, String>();
		orderMap.put(propertyName,ORDER_DESC);
	}

	public boolean isOrder() {
		return orderMap != null && !orderMap.isEmpty();
	}

	public boolean isAutoCount() {
		return autoCount;
	}

	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}

	public Page<T> autoCount(final boolean autoCount) {
		setAutoCount(autoCount);
		return this;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(final List<T> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(final long total) {
		this.total = total;
	}

	public long getTotalPage() {
		if (total < 0) {
			return -1;
		}
		long count = total / getPageSize();
		if (total % getPageSize() > 0) {
			count++;
		}
		return count;
	}

	public boolean isHasNext() {
		return page <= this.getTotalPage() - 1;
	}

	public boolean isHasPre() {
		return (page - 1 >= 1);
	}

	public long getNextPage() {
		if (isHasNext()) {
			return page + 1;
		} else {
			return page;
		}
	}

	public long getPrePage() {
		if (isHasPre()) {
			return page - 1;
		} else {
			return page;
		}
	}
	public long getPage() {
		return page;
	}
	public void setPage(long page) {
		this.page = page;
	}
	
}
