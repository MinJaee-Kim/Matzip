package kr.ac.uc.matzip.view;

import static kr.ac.uc.matzip.view.FileUtils.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.KakaoModel;

public class MapSearchAdapter extends RecyclerView.Adapter<MapSearchAdapter.CustomViewHolder>{
    private Context context;
    private ArrayList<KakaoModel.Document> Kakao_Arraylist;
    public static final int RESULT_SEARCH = 5;

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
        holder.latitude = Kakao_Arraylist.get(position).getY();
        holder.longitude = Kakao_Arraylist.get(position).getX();
        holder.mConstrantLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                Log.d(TAG, "onClick: " + holder.latitude + holder.iis_locationTv.getText());

                intent.putExtra("위도", holder.latitude);
                intent.putExtra("경도", holder.longitude);
                intent.putExtra("위치", holder.iis_locationTv.getText());
                ((Activity)context).setResult(RESULT_SEARCH, intent);
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != Kakao_Arraylist ? Kakao_Arraylist.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iis_locationIv;
        protected TextView iis_locationTv;
        private double latitude;
        private double longitude;
        private ConstraintLayout mConstrantLayout;

        public CustomViewHolder(View itemView){
            super(itemView);
            this.iis_locationIv = (ImageView) itemView.findViewById(R.id.iis_locationIv);
            this.iis_locationTv = (TextView) itemView.findViewById(R.id.iis_locationTv);
            this.mConstrantLayout = itemView.findViewById(R.id.iis_Cl);
        }
    }
}
