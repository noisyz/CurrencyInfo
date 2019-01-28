package com.noisyz.currencyinfo.ui.currencies;

import com.noisyz.currencyinfo.net.ApiService;
import com.noisyz.currencyinfo.net.model.Pair;
import com.noisyz.mvvmbase.network.Response;
import com.noisyz.mvvmbase.view.ItemView;
import com.noisyz.mvvmbase.viewmodel.impl.single.abs.BaseItemViewModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class CurrencyListViewModel extends BaseItemViewModelImpl<List<Pair>> {
    public CurrencyListViewModel(ItemView<List<Pair>> listItemView) {
        super(listItemView);
    }

    @Override
    protected Single<List<Pair>> initRequest() {
        return ApiService.getInstance().getApiMethods().getPairs()
                .map(new Function<Response<List<String>>, List<Pair>>() {
                    @Override
                    public List<Pair> apply(Response<List<String>> listResponse) throws Exception {
                        List<String> availablePairs = listResponse.getResult();
                        Collections.reverse(availablePairs);
                        List<Pair> pairs = new ArrayList<>();
                        for (String name : availablePairs) {
                            pairs.add(new Pair(name));
                        }
                        return pairs;
                    }
                });
    }
}
