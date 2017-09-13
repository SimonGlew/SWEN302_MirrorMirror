package mirrormirror.swen302.mirrormirrorandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import mirrormirror.swen302.mirrormirrorandroid.R;
import mirrormirror.swen302.mirrormirrorandroid.utilities.ImageStorageManager;

/**
 * Created by hayandr1 on 17/08/17.
 */

public class HorizontalAdapter extends RecyclerView.Adapter {

    public List<String> filePaths;
    Activity context;

    public HorizontalAdapter(List<String> filePaths, Activity context){
        this.filePaths = filePaths;
        this.context = context;
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public ImageViewHolder(View view) {
            super(view);
            imageView=(ImageView) view.findViewById(R.id.recycler_imageview);
        }
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_image_layout, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(itemLayoutView);
        return  imageViewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ImageViewHolder imageHolder = (ImageViewHolder)holder;
        //imageHolder.imageView.setImageBitmap(ImageStorageManager.loadImageByName(filePaths.get(position), context));
        Glide.with(context).load(ImageStorageManager.loadImageByName(filePaths.get(position), context)).into(imageHolder.imageView);
        imageHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView mainImage = (ImageView)HorizontalAdapter.this.context.findViewById(R.id.main_image);
                Glide.with(context).load(ImageStorageManager.loadImageByName(filePaths.get(position),context)).into(mainImage);
                //mainImage.setImageBitmap(ImageStorageManager.loadImageByName(filePaths.get(position), context));
            }
        });
    }



    @Override
    public int getItemCount() {
        if(filePaths != null){
            return filePaths.size();

        }
        else{
            return 0;
        }
    }
}
