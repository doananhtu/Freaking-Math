package myapp.anhtu.com.freakingmath.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.ArrayList;

import myapp.anhtu.com.freakingmath.R;
import myapp.anhtu.com.freakingmath.database.DatabaseFreakingMath;
import myapp.anhtu.com.freakingmath.entity.Player;
import myapp.anhtu.com.freakingmath.entity.PlayerAdapter;

public class ShowScoreActivity extends AppCompatActivity {

    DatabaseFreakingMath db = new DatabaseFreakingMath(ShowScoreActivity.this);
    ArrayList<Player> listPlayer = new ArrayList<>();
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_score);

        lv = (ListView)findViewById(R.id.listViewScore);
        PlayerAdapter adapter = new PlayerAdapter(ShowScoreActivity.this,R.layout.activity_score,db.getAllScore());
        lv.setAdapter(adapter);
    }
}
