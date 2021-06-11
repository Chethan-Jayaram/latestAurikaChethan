package com.mobisprint.aurika.coorg.fragments.dining;

import android.content.Context;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.InRoomDiningAdapter;
import com.mobisprint.aurika.coorg.controller.ird.CoorgInRoomDiningController;
import com.mobisprint.aurika.coorg.pojo.dining.Data;
import com.mobisprint.aurika.coorg.pojo.dining.Dining;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.List;

import retrofit2.Response;


public class CoorgInRoomDiningFragment extends Fragment implements ApiListner {

    private RecyclerView recyclerView;
    private CoorgInRoomDiningController coorgInRoomDiningController;
    private TextView toolbar_title;
    private Context mContext;
    private ImageView img_back;
    private CoordinatorLayout lyt;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_coorg_in_room_dining, container, false);

       toolbar_title = getActivity().findViewById(R.id.toolbar_title);
       recyclerView = view.findViewById(R.id.coorg_ird_recycler);
       mContext = getContext();
       coorgInRoomDiningController = new CoorgInRoomDiningController(this);
       Bundle bundle = getArguments();
       toolbar_title.setText(bundle.getString("title"));
       img_back = getActivity().findViewById(R.id.naviagation_hamberger);
       img_back.setVisibility(View.VISIBLE);
       lyt = view.findViewById(R.id.lyt);
       lyt.setVisibility(View.GONE);
       progressBar = view.findViewById(R.id.progress_bar);
       progressBar.setVisibility(View.GONE);

       coorgInRoomDiningController.getDiningMenu();

        return view;
    }

    @Override
    public void onFetchProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        progressBar.setVisibility(View.GONE);
        lyt.setVisibility(View.VISIBLE);

        if (response!= null){
            Dining dining = (Dining) response.body();
            List<Data> dataList = dining.getData();
            InRoomDiningAdapter adapter = new InRoomDiningAdapter(mContext,dataList,Position -> {

                Fragment fragment = new InRoomDiningMenuFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title",dataList.get(Position).getTitle());
                bundle.putString("desc",dataList.get(Position).getDescription());
                bundle.putInt("category_id",dataList.get(Position).getId());
                bundle.putString("from_time",dataList.get(Position).getFromTime());
                bundle.putString("to_time",dataList.get(Position).getToTime());
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container,fragment).addToBackStack(null).commit();



            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }


    }

    @Override
    public void onFetchError(String error) {
        progressBar.setVisibility(View.GONE);
        GlobalClass.ShowAlert(mContext,"Alert",error);

    }
}