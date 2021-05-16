package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lostsong.R;
import com.domslab.model.SongCard;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    ArrayList<SongCard> songCards;
    Context context;
    private OnSongListener mOnSongListener;
    public SongAdapter(Context context, ArrayList<SongCard> songCards, OnSongListener onSongListener) {
        this.songCards = songCards;
        this.context = context;
        mOnSongListener=onSongListener;
    }

    @NonNull
    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new ViewHolder(view,mOnSongListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.ViewHolder holder, int position) {
        //set logo to imageview
        holder.imageView.setImageResource(songCards.get(position).getSongLogo());
        //set name to textview
        holder.textView.setText(songCards.get(position).getSongName());
    }

    @Override
    public int getItemCount() {
        return songCards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;

        OnSongListener onSongListener;
        public ViewHolder(@NonNull View itemView, OnSongListener onSongListener) {
            super(itemView);
            //assing variable
            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.text_view);
            this.onSongListener = onSongListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onSongListener.onSongClick(getAdapterPosition());
        }
    }
    public interface OnSongListener{
        public void onSongClick(int position);
    }
}
