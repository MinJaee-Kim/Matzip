package kr.ac.uc.matzip.view;

import static kr.ac.uc.matzip.view.FileUtils.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

    @Override
    public int getItemViewType(int position) {
        return position;
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
        holder.iis_locationTv2.setText(Kakao_Arraylist.get(position).getRoad_address_name());
        holder.latitude = Kakao_Arraylist.get(position).getY();
        holder.longitude = Kakao_Arraylist.get(position).getX();
        holder.mConstrantLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                Log.d(TAG, "onClick: " + holder.latitude + holder.iis_locationTv.getText());

                intent.putExtra("??????", holder.latitude);
                intent.putExtra("??????", holder.longitude);
                intent.putExtra("??????", holder.iis_locationTv.getText());
                ((Activity)context).setResult(RESULT_SEARCH, intent);
                ((Activity)context).finish();

                //????????? ?????????
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != Kakao_Arraylist ? Kakao_Arraylist.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iis_locationIv;
        protected TextView iis_locationTv, iis_locationTv2;
        private double latitude;
        private double longitude;
        private ConstraintLayout mConstrantLayout;

        public CustomViewHolder(View itemView){
            super(itemView);
            this.iis_locationIv = (ImageView) itemView.findViewById(R.id.iis_locationIv);
            this.iis_locationTv = (TextView) itemView.findViewById(R.id.iis_locationTv);
            this.iis_locationTv2 = (TextView) itemView.findViewById(R.id.iis_locationTv2);
            this.mConstrantLayout = itemView.findViewById(R.id.iis_Cl);
        }
    }
}
