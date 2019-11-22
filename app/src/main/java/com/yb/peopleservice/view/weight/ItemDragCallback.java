package com.yb.peopleservice.view.weight;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.DraggableController;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDragCallback extends ItemDragAndSwipeCallback {

    public ItemDragCallback(DraggableController draggableController) {
        super(draggableController);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }
}
