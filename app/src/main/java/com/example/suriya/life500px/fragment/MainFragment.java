package com.example.suriya.life500px.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.suriya.life500px.R;
import com.example.suriya.life500px.activity.MoreInfoActivity;
import com.example.suriya.life500px.adpater.PhotoListAdpater;
import com.example.suriya.life500px.dao.PhotoItemCollectDao;
import com.example.suriya.life500px.dao.PhotoItemDao;
import com.example.suriya.life500px.manager.PhotoListManager;
import com.example.suriya.life500px.manager.http.HttpManage;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {

    private Button btnNewPhoto;
    private ListView listView;
    PhotoListAdpater listAdpater;
    SwipeRefreshLayout swipeRefresh;
    PhotoListManager photoListManager;

    /****************
     * Function Zone
     ***************/

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Init Fragment Level
        init();

        if (savedInstanceState != null){
            onRestoreInstanceState(savedInstanceState);
        }
    }

    private void init() {

        photoListManager = new PhotoListManager();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        btnNewPhoto = (Button) rootView.findViewById(R.id.btnNewPhoto);
        btnNewPhoto.setOnClickListener(buttonClickListener);
        listView = (ListView) rootView.findViewById(R.id.listView);
        listAdpater = new PhotoListAdpater();
        listAdpater.setDao(photoListManager.getDao());
        listView.setAdapter(listAdpater);
        listView.setOnItemClickListener(listViewItemClickListener);

        swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(pulltorefreshListener);

        this.listView.setOnScrollListener(scorolListViewListener);

        if (savedInstanceState == null) {
            refresshData();
        }
    }

    private void refresshData() {
        if (photoListManager.getCount() == 0) {
            reloadData();
        } else {
            reloadDataNewer();
        }
    }

    private void reloadDataNewer() {

        int maxId = photoListManager.getMaximumId();
        Call<PhotoItemCollectDao> call = HttpManage.getInstance().getSevice()
                .loadPhotoListAfterId(maxId);
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD_NEWER));

    }

    boolean isLoadingMore = false;

    private void loadMoreData() {
        if (isLoadingMore){
            return;
        }
        isLoadingMore = true;
        int minId = photoListManager.getMinimumId();
        Call<PhotoItemCollectDao> call = HttpManage.getInstance().getSevice()
                .loadPhotoListBeforeId(minId);
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD_MORE));

    }

    private void reloadData() {
        Call<PhotoItemCollectDao> call = HttpManage.getInstance().getSevice().loadPhotoList();
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
        outState.putBundle("photoListManager", photoListManager.onSaveInstanceState());
    }

    /*
     * Restore Instance State Here
     */
    private void onRestoreInstanceState(Bundle saveInstanceState){

        photoListManager.onRestoreInstanceState(saveInstanceState
                .getBundle("photoListManager"));

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

    private void showButtonNewPhoto() {
        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_fade_in);
        btnNewPhoto.startAnimation(anim);
        btnNewPhoto.setVisibility(View.VISIBLE);

    }

    private void hideButtonNewPhoto() {
        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_fade_out);
        btnNewPhoto.startAnimation(anim);
        btnNewPhoto.setVisibility(View.GONE);

    }

    private void showToast(String text){
        Toast.makeText(getActivity(),
                text, Toast.LENGTH_SHORT).show();
    }

    /*****************
     *  Listener Zone
     ****************/

    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listView.smoothScrollToPosition(0);
            hideButtonNewPhoto();
        }
    };

    SwipeRefreshLayout.OnRefreshListener pulltorefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refresshData();
        }
    };

    AbsListView.OnScrollListener scorolListViewListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {

        }

        @Override
        public void onScroll(AbsListView absListView, int firstVisbleItem,
                             int visibleItemCount, int totalItemCount) {
            swipeRefresh.setEnabled(firstVisbleItem == 0);
            if (firstVisbleItem + visibleItemCount >= totalItemCount) {
                if (photoListManager.getCount() > 0) {
                    Log.d("ListView", "Load More Trigged");
                    loadMoreData();
                }
            }
        }
    };

    AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int posotion, long l) {
            PhotoItemDao dao = photoListManager.getDao().getData().get(posotion);
            Toast.makeText(getActivity(), "Position " + posotion, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), MoreInfoActivity.class);
            intent.putExtra("dao", dao);
            startActivity(intent);
        }
    };

    /**************
     * Inner Class
     *************/

    class PhotoListLoadCallback implements Callback<PhotoItemCollectDao> {

        public static final int MODE_RELOAD = 1;
        public static final int MODE_RELOAD_NEWER = 2;
        public static final int MODE_RELOAD_MORE = 3;

        int mode;

        public PhotoListLoadCallback(int mode) {
            this.mode = mode;
        }

        @Override
        public void onResponse(Call<PhotoItemCollectDao> call, Response<PhotoItemCollectDao> response) {
            swipeRefresh.setRefreshing(false);
            if (response.isSuccessful()) {
                PhotoItemCollectDao dao = response.body();

                int firstVisiblePosition = listView.getFirstVisiblePosition();
                View c = listView.getChildAt(0);
                int top = c == null ? 0 : c.getTop();

                if (mode == MODE_RELOAD_NEWER) {
                    photoListManager.insertDaoAtTopPosition(dao);
                } else if (mode == MODE_RELOAD){
                    photoListManager.setDao(dao);
                }else {
                    photoListManager.appendDaoAtBottomPosition(dao);
                    clerFlag(mode);
                }
                listAdpater.setDao(photoListManager.getDao());
                listAdpater.notifyDataSetChanged();

                if (mode == MODE_RELOAD_NEWER) {

                    int additionalSize =
                            (dao != null && dao.getData() != null) ? dao.getData().size() : 0;
                    listAdpater.increaseLastPosition(additionalSize);
                    listView.setSelectionFromTop(firstVisiblePosition + additionalSize, top);
                    if (additionalSize > 0) {
                        showButtonNewPhoto();
                    }
                } else {


                }

                showToast("Load Completed");
            } else {
                clerFlag(mode);
                try {
                    showToast(response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<PhotoItemCollectDao> call, Throwable t) {
            clerFlag(mode);
            swipeRefresh.setRefreshing(false);
            showToast(t.toString());
        }

        private void clerFlag(int mode) {
            if (mode == MODE_RELOAD_MORE){
                isLoadingMore = false;
            }
        }
    }

}
