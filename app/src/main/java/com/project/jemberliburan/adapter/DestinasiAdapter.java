package com.project.jemberliburan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.project.jemberliburan.R;
import java.util.List;

public class DestinasiAdapter extends RecyclerView.Adapter<DestinasiAdapter.ViewHolder> {

    private Context context;
    private List<Integer> destinationImages;

    public DestinasiAdapter(Context context, List<Integer> destinationImages) {
        this.context = context;
        this.destinationImages = destinationImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_destinasi_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(destinationImages.get(position));
    }

    @Override
    public int getItemCount() {
        return destinationImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.destinationImage);
        }
    }
}
