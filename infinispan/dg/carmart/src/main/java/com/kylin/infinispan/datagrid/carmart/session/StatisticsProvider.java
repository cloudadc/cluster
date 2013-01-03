package com.kylin.infinispan.datagrid.carmart.session;

/**
 * 
 * Provides cache statistics.
 */
public interface StatisticsProvider {

    public void getStatsObject();

    public String getRetrievals();

    public String getStores();

    public String getCurrentEntries();

    public String getHits();

    public String getMisses();

    public String getRemoveHits();

}
