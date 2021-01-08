package com.paymentez.plazez.sdk.controllers;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.paymentez.plazez.sdk.adapters.SwiperAdapter;
import com.paymentez.plazez.sdk.controls.RecyclerItemTouchHelper;

public abstract class AbstractSwiperContainerActivity<T, Y extends RecyclerView.ViewHolder> extends PmzBaseActivity{

    class SwipeListener implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
            List<T> items = getItems();
            final SwiperAdapter<T, Y> adapter = getAdapter();
            CoordinatorLayout coordinator = getCoordinatorLayout();

            if(items != null && adapter != null) {
                // backup of removed item for undo purpose
                final T deletedItem = items.get(viewHolder.getAdapterPosition());
                final int deletedIndex = viewHolder.getAdapterPosition();

                // remove the item from recycler view
                adapter.removeItem(viewHolder.getAdapterPosition());

                // showing snack bar with Undo option
                /*Snackbar snackbar = Snackbar.make(coordinator, getDeletedLabelWarning(), Snackbar.LENGTH_LONG);
                snackbar.setAction(getString(R.string.swipe_to_delete_undo_button), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // undo is selected, restore the deleted item
                        adapter.restoreItem(deletedItem, deletedIndex);
                    }
                });
                snackbar.setActionTextColor(getResources().getColor(R.color.snack_red));
                snackbar.show();*/
            }
        }
    }

    protected void setRecyclerItemTouchHelper(RecyclerView recyclerView){
        ItemTouchHelper.SimpleCallback touchCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, new SwipeListener());
        new ItemTouchHelper(touchCallback).attachToRecyclerView(recyclerView);
    }

    protected abstract String getDeletedLabelWarning();

    protected abstract CoordinatorLayout getCoordinatorLayout();

    protected abstract SwiperAdapter<T,Y> getAdapter();

    protected abstract List<T> getItems();
}

