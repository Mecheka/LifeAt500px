package com.example.suriya.life500px.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.suriya.life500px.R;
import com.example.suriya.life500px.adpater.PhotoListAdpater;
import com.example.suriya.life500px.dao.PhotoItemCollectDao;
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

    private ListView listView;
    PhotoListAdpater listAdpater;
    SwipeRefreshLayout swipeRefresh;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        listView = (ListView) rootView.findViewById(R.id.listView);
        listAdpater = new PhotoListAdpater();
        listView.setAdapter(listAdpater);

        swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadData();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                swipeRefresh.setEnabled(i == 0);
            }
        });

        reloadData();
    }

    private void reloadData() {
        Call<PhotoItemCollectDao> call = HttpManage.getInstance().getSevice().loadPhotoList();
        call.enqueue(new Callback<PhotoItemCollectDao>() {
            @Override
            public void onResponse(Call<PhotoItemCollectDao> call, Response<PhotoItemCollectDao> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    PhotoItemCollectDao dao = response.body();
                    listAdpater.setDao(dao);
                    listAdpater.notifyDataSetChanged();
                    Toast.makeText(Contextor.getInstance().getContext(), dao.getData().get(0).getCaption(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Toast.makeText(Contextor.getInstance().getContext(), response.errorBody().string(),
                                Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PhotoItemCollectDao> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(Contextor.getInstance().getContext(), t.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
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
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }
}