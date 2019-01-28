package com.noisyz.mvvmbase.viewmodel.cache.collector;

import com.noisyz.mvvmbase.viewmodel.cache.parser.ItemParser;

import java.util.List;

public abstract class Collector<T> implements ItemParser<T> {
    private List<T> collectedData;

    public List<T> collect(String trackersXml, String markersXml) {
        this.collectedData = parseItems(trackersXml, markersXml);
        return this.collectedData;
    }

    public void release() {
        if (this.collectedData != null) {
            this.collectedData.clear();
            this.collectedData = null;
        }
    }

    public List<T> getCollectedData() {
        return this.collectedData;
    }
}
