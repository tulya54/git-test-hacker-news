package kz.tech.testhackernewsclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kz.tech.testhackernewsclient.R;
import kz.tech.testhackernewsclient.models.Model;


public class AdapterStories extends RecyclerView.Adapter<AdapterStories.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(String url);
        void onCommentClick(String id);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private Context c;
    private List<Model> list;
    public AdapterStories(Context c) {
        this.c = c;
        this.list = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_stories, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model m = list.get(position);
        if (m.getTitle() != null) {
            holder.tvText.setText(m.getTitle());
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvText;
        ImageView ivImg;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
            ivImg = (ImageView) itemView.findViewById(R.id.ivImg);
            tvText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        onItemClickListener.onItemClick(list.get(position).getUrl());
                    }
                }
            });
            ivImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        onItemClickListener.onCommentClick(list.get(position).getId());
                    }
                }
            });
        }
    }
    public void updateDate(Model m) {
        int num = list.size()-1;
        list.add(m);
        notifyItemChanged(num);
    }
    public void updateDates(List<Model> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
