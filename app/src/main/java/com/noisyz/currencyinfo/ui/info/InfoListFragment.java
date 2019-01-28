package com.noisyz.currencyinfo.ui.info;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bindlibrary.generated.pair.adapter.PairFilterableBindAdapter;
import com.noisyz.bindlibrary.base.impl.adapter.RecyclerFilterableBindAdapter;
import com.noisyz.currencyinfo.R;
import com.noisyz.currencyinfo.net.model.Pair;
import com.noisyz.mvvmbase.ui.list.abs.ListFilterableFragment;
import com.noisyz.mvvmbase.viewmodel.impl.single.abs.BaseItemViewModelImpl;

import java.util.List;

import androidx.navigation.fragment.NavHostFragment;

public class InfoListFragment extends ListFilterableFragment<Pair> {

    public static final String EXTRA_PAIRS = "extra_pairs";

    @Override
    protected RecyclerFilterableBindAdapter<Pair, String> initFilterableItemsAdapter(List<Pair> list) {
        return new PairFilterableBindAdapter<>(list, R.layout.currency_item, this);
    }

    @Override
    public BaseItemViewModelImpl<List<Pair>> initViewModel() {
        String pairs = null;
        if (getArguments() != null) {
            pairs = getArguments().getString(EXTRA_PAIRS);
        }
        return new InfoListViewModel(this, pairs);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(com.noisyz.mvvmbase.R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.info_title);
            toolbar.setNavigationIcon(R.drawable.ic_action_move_up);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavHostFragment.findNavController(InfoListFragment.this).navigateUp();
                }
            });
        }
    }

    @Override
    public void onItemClick(View convertView, int position, Pair pair) {

    }

    @Override
    public boolean filterBy(Pair pair, String query) {
        return pair.getName().toLowerCase().contains(query.toLowerCase());
    }
}
