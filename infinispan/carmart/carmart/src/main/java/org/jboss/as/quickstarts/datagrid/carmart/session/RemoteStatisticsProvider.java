package org.jboss.as.quickstarts.datagrid.carmart.session;

import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.ServerStatistics;
import org.jboss.as.quickstarts.datagrid.carmart.session.CacheContainerProvider;
import org.jboss.as.quickstarts.datagrid.carmart.session.CarManager;
import org.jboss.as.quickstarts.datagrid.carmart.session.StatisticsProvider;


@Named("stats")
@RequestScoped
public class RemoteStatisticsProvider implements StatisticsProvider {

    @Inject
    private CacheContainerProvider provider;

    private Map<String, String> stats;

    @PostConstruct
    public void getStatsObject() {
        RemoteCache<String, Object> carCache = (RemoteCache) provider.getCacheContainer().getCache(CarManager.CACHE_NAME);
        stats = carCache.stats().getStatsMap();
    }

    public String getRetrievals() {
        return stats.get(ServerStatistics.RETRIEVALS);
    }

    public String getStores() {
        return stats.get(ServerStatistics.STORES);
    }

    public String getCurrentEntries() {
        return stats.get(ServerStatistics.CURRENT_NR_OF_ENTRIES);
    }

    public String getHits() {
        return stats.get(ServerStatistics.HITS);
    }

    public String getMisses() {
        return stats.get(ServerStatistics.MISSES);
    }

    public String getRemoveHits() {
        return stats.get(ServerStatistics.REMOVE_HITS);
    }
}
