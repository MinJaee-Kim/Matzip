package kr.ac.uc.matzip.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.KakaoModel;

public class MapSearchAdapter extends RecyclerView.Adapter<MapSearchAdapter.CustomViewHolder>{

    private Context context;
    private ArrayList<KakaoModel.Document> Kakao_Arraylist;

    public MapSearchAdapter(Context context, ArrayList<KakaoModel.Document> arraylist) {
        this.context = context;
        this.Kakao_Arraylist = arraylist;
    }

    @NonNull
    @Override
    public MapSearchAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_search,parent,false);
        MapSearchAdapter.CustomViewHolder holder = new MapSearchAdapter.CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MapSearchAdapter.CustomViewHolder holder, int position) {
        holder.iis_locationTv.setText(Kakao_Arraylist.get(position).getPlace_name());
    }

    @Override
    public int getItemCount() {
        return (null != Kakao_Arraylist ? Kakao_Arraylist.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iis_locationIv;
        protected TextView iis_locationTv;

        public CustomViewHolder(View itemView){
            super(itemView);
            this.iis_locationIv = (ImageView) itemView.findViewById(R.id.iis_locationIv);
            this.iis_locationTv = (TextView) itemView.findViewById(R.id.iis_locationTv);
        }
    }
}
