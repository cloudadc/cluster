package org.infinispan.demo.carmart.session;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.infinispan.demo.carmart.session.StatisticsProvider;

@Named("stats")
@RequestScoped
public class LocalStatisticsProvider implements StatisticsProvider {

    @PostConstruct
    public void getStatsObject() {
        
    }

    public String getRetrievals() {
        return "";
    }

    public String getStores() {
        return "";
    }

    public String getCurrentEntries() {
        return "";
    }

    public String getHits() {
        return "";
    }

    public String getMisses() {
        return "";
    }

    public String getRemoveHits() {
        return "";
    }
}
