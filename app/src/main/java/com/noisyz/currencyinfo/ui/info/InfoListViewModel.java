package com.noisyz.currencyinfo.ui.info;

import com.noisyz.currencyinfo.net.ApiService;
import com.noisyz.currencyinfo.net.model.Pair;
import com.noisyz.mvvmbase.network.Response;
import com.noisyz.mvvmbase.view.ItemView;
import com.noisyz.mvvmbase.viewmodel.impl.single.abs.BaseItemViewModelImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class InfoListViewModel extends BaseItemViewModelImpl<List<Pair>> {

    private final String pairs ;

    public InfoListViewModel(ItemView<List<Pair>> listItemView, String pairs) {
        super(listItemView);
        this.pairs = pairs;
    }

    @Override
    protected Single<List<Pair>> initRequest() {
        return ApiService.getInstance().getApiMethods().getPrices(pairs)
                .map(new Function<Response<Map<String, Double>>, List<Pair>>() {
                    @Override
                    public List<Pair> apply(Response<Map<String, Double>> mapResponse) throws Exception {
                        List<Pair> pairs = new ArrayList<>();
                        Map<String, Double> priceMap = mapResponse.getResult();
                        for (String key : priceMap.keySet()) {
                            pairs.add(new Pair(key, priceMap.get(key)));
                        }
                        return pairs;
                    }
                });
    }
}
