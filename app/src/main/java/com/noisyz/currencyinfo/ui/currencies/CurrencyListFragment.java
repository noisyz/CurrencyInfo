package com.noisyz.currencyinfo.ui.currencies;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bindlibrary.generated.pair.adapter.PairFilterableBindAdapter;
import com.noisyz.bindlibrary.base.impl.adapter.RecyclerFilterableBindAdapter;
import com.noisyz.currencyinfo.R;
import com.noisyz.currencyinfo.net.model.Pair;
import com.noisyz.currencyinfo.ui.info.InfoListFragment;
import com.noisyz.mvvmbase.ui.list.abs.ListFilterableFragment;
import com.noisyz.mvvmbase.viewmodel.impl.single.abs.BaseItemViewModelImpl;

import java.util.List;

import androidx.navigation.fragment.NavHostFragment;

public class CurrencyListFragment extends ListFilterableFragment<Pair> {

    private List<Pair> pairs;

    @Override
    protected RecyclerFilterableBindAdapter<Pair, String> initFilterableItemsAdapter(List<Pair> list) {
        Log.d("myLogs", "Init adapter");
        this.pairs = list;
        return new PairFilterableBindAdapter<>(list, R.layout.currency_item_checkable, this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(com.noisyz.mvvmbase.R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.pairs_title);
            toolbar.getMenu().clear();
            toolbar.inflateMenu(R.menu.menu_main);
            toolbar.inflateMenu(com.noisyz.mvvmbase.R.menu.menu_search);
            ((SearchView) MenuItemCompat.getActionView(toolbar.getMenu().findItem(com.noisyz.mvvmbase.R.id.action_search))).setOnQueryTextListener(this);

            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (menuItem.getItemId() == R.id.action_get_info) {
                        showInfo();
                        return true;
                    } else
                        return false;
                }
            });
        }
    }


    private void showInfo() {
        StringBuilder checkedPairs = new StringBuilder();
        if (pairs != null) {
            for (Pair pair : pairs) {
                if (pair.isEnabled()) {
                    checkedPairs.append(pair.getNameInApiFormat()).append(",");
                }
            }

            Bundle args = new Bundle();
            args.putString(InfoListFragment.EXTRA_PAIRS, checkedPairs.toString());

            if (getView() != null) {
                NavHostFragment.findNavController(this).navigate(R.id.currency_list_to_info, args);
            }
        } else {
            showError(0, "There is no selected pair" );
        }
    }

    @Override
    public BaseItemViewModelImpl<List<Pair>> initViewModel() {
        return new CurrencyListViewModel(this);
    }

    @Override
    public void onItemClick(View convertView, int position, Pair pair) {

    }

    @Override
    public boolean filterBy(Pair pair, String query) {
        return pair.getName().toLowerCase().contains(query.toLowerCase());
    }
}
