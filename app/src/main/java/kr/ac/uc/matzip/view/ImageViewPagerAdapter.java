package kr.ac.uc.matzip.view;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.PhotoModel;

public class ImageViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<Uri> imageList;

    public ImageViewPagerAdapter(Context context, ArrayList<Uri> imageList)
    {
        this.mContext = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.board_photo_item, null);

        ImageView imageView = view.findViewById(R.id.bpi_photo_item);

        Picasso.get().load(imageList.get(position)).into(imageView);

        container.addView(view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageView.getScaleType().equals(ImageView.ScaleType.CENTER_CROP)){
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }else if(imageView.getScaleType().equals(ImageView.ScaleType.FIT_CENTER)){
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }
        });

        return view;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (View)o);
    }
}