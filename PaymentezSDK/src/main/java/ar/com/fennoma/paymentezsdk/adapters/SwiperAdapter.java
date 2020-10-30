package ar.com.fennoma.paymentezsdk.adapters;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class SwiperAdapter<Y, T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T>{

    public abstract void removeItem(int position);

    public abstract void restoreItem(Y item, int position);

    public abstract static class SwiperHolder extends RecyclerView.ViewHolder {

        public View container;

        public SwiperHolder(View itemView) {
            super(itemView);
            container = getContainer(itemView);
        }

        protected abstract View getContainer(View itemView);
    }
}
