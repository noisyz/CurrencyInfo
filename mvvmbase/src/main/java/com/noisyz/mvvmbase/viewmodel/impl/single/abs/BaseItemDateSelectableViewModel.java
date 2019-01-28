package com.noisyz.mvvmbase.viewmodel.impl.single.abs;

import com.noisyz.mvvmbase.view.ItemView;

public abstract class BaseItemDateSelectableViewModel<T> extends BaseItemViewModelImpl<T> {
    private long dateFrom;
    private long dateTill;

    public BaseItemDateSelectableViewModel(ItemView<T> tItemView) {
        super(tItemView);
    }

    public void setDates(long dateFrom, long dateTill) {
        this.dateFrom = dateFrom;
        this.dateTill = dateTill;
    }

    public long getDateFrom() {
        return this.dateFrom;
    }

    public long getDateTill() {
        return this.dateTill;
    }
}
