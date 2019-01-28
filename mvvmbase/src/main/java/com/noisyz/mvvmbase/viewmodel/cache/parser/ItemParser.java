package com.noisyz.mvvmbase.viewmodel.cache.parser;

import java.util.List;

public interface ItemParser<T> {
    List<T> parseItems(String... strArr);
}
