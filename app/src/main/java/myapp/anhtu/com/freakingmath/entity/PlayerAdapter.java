package myapp.anhtu.com.freakingmath.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import myapp.anhtu.com.freakingmath.R;

/**
 * Created by anhtu on 2/15/2017.
 */

public class PlayerAdapter extends ArrayAdapter {
    public PlayerAdapter(Context context, int resource) {
        super(context, resource);
    }

    public PlayerAdapter(Context context, int resource, List<Player> item) {
        super(context, resource, item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater vi;
            vi =LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_score,null);
        }
        Player player = (Player) getItem(position);
        if(player!=null){
            TextView txtName = (TextView) v.findViewById(R.id.txtName);
            TextView txtScore2 = (TextView) v.findViewById(R.id.txtScore2);
            txtName.setText(player.getName());
            txtScore2.setText(String.valueOf(player.getScore()));
        }
        return v;
    }
}
