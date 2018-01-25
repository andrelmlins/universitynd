package br.ufrpe.universitynd.Interface;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by AndreLucas on 24/01/2018.
 */

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private int visibleItemCount, totalItemCount = 1;
    private int firstVisiblesItems = 0;
    private int totalPages = 1;
    private int current_page = 1;
    private boolean canLoadMoreData = true;
    private LinearLayoutManager llm;

    public EndlessRecyclerViewScrollListener(int totalPages, RecyclerView recyclerView){
        this.llm = (LinearLayoutManager) recyclerView.getLayoutManager();
        this.totalPages = totalPages;
    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) //check for scroll down
        {
            visibleItemCount = llm.getChildCount();
            totalItemCount = llm.getItemCount();
            firstVisiblesItems = llm.findFirstVisibleItemPosition();

            if (canLoadMoreData) {
                if ((visibleItemCount + firstVisiblesItems) >= totalItemCount) {
                    if (current_page < totalPages) {
                        current_page++;
                        onLoadMore(current_page, totalItemCount);
                    } else {
                        canLoadMoreData = false;
                    }
                }
            }
        }
    }

    public abstract boolean onLoadMore(int page, int totalItemsCount);
}
