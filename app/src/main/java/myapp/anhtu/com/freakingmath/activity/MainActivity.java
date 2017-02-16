package myapp.anhtu.com.freakingmath.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import myapp.anhtu.com.freakingmath.R;
import myapp.anhtu.com.freakingmath.database.DatabaseFreakingMath;
import myapp.anhtu.com.freakingmath.entity.Player;

import static myapp.anhtu.com.freakingmath.ulti.lib.buttonEffect;

public class MainActivity extends AppCompatActivity {

    TextView txtOperator, txtResult, txtScore;
    EditText edtName;
    ImageButton btnTrue, btnFalse, btnCup;
    Button btnOk;
    ProgressBar prbTime;
    CountDownTimer timer;
    RelativeLayout layoutColor;
    MediaPlayer soundBtn, buzz;

    DatabaseFreakingMath db = new DatabaseFreakingMath(MainActivity.this);
//    ArrayList<Player> players = new ArrayList<>();
    int maxScore;
    boolean trueOrFalse;
    int score = 0;
    float countDown = 0;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //
        btnTrue = (ImageButton) findViewById(R.id.btnTrue);
        btnFalse = (ImageButton) findViewById(R.id.btnFalse);
        btnCup = (ImageButton) findViewById(R.id.btnCup);
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtScore.setText(String.valueOf(score));

        soundBtn = MediaPlayer.create(MainActivity.this, R.raw.btnsound);
        buzz = MediaPlayer.create(MainActivity.this, R.raw.buzz);
        prbTime = (ProgressBar) findViewById(R.id.prbTime);
        prbTime.getProgressDrawable()
                .setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);

        //Dialog init
        final Dialog cusDialog = new Dialog(MainActivity.this);
        cusDialog.setContentView(R.layout.custom_dialog);
        cusDialog.setCancelable(false);
        edtName = (EditText) cusDialog.findViewById(R.id.edtName);
        btnOk = (Button) cusDialog.findViewById(R.id.btnOk);

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Game Over");
        builder.setCancelable(false);
        trueOrFalse = createOperator(1); //trả về boolean kết quả đúng hay sai.
        db.delAllScore();
        //Khi chọn đáp án đúng
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundBtn.start();
                buttonEffect(btnTrue);
                countDown = 0;
                if (trueOrFalse) {
                    prbTime.setProgress((int) countDown);
                    timer = new CountDownTimer(1000, 10) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            countDown += 1.7; //loop 59 time
                            prbTime.setProgress((int) countDown);
                        }

                        @Override
                        public void onFinish() {
                            if (countDown >= 100) {
                                maxScore = db.getMaxScore();
                                boolean isHighScore = false;
                                if (maxScore==0 && score != 0) {
                                    dialogCustom(cusDialog);
                                }
                                if(maxScore==0 && score == 0){
                                    dialogNormal(builder);
                                }
                                if(maxScore!=0 && score == 0){
                                    dialogNormal(builder);
                                }
                                if(maxScore!=0 && score != 0){
                                    if(score > maxScore)
                                        isHighScore = true;
                                    if(isHighScore){
                                        dialogCustom(cusDialog);
                                    }else{
                                        dialogNormal(builder);
                                    }
                                }
                                trueOrFalse = createOperator(1);
                            }
                        }
                    };
                    timer.start();
                    score += 1;
                    txtScore.setText(String.valueOf(score));
                    if (score >= 0 && score <= 10) //Tăng level
                        trueOrFalse = createOperator(1); //Tạo một phép tính mới.
                    else if (score > 10 && score <= 20)
                        trueOrFalse = createOperator(2);
                    else if (score > 20)
                        trueOrFalse = createOperator(3);
                } else {
                    //Xem có điểm cao hơn hay ko?
                    maxScore = db.getMaxScore();
                    boolean isHighScore = false;
                    if (maxScore==0 && score != 0) {
                        dialogCustom(cusDialog);
                    }
                    if(maxScore==0 && score == 0){
                        dialogNormal(builder);
                    }
                    if(maxScore!=0 && score == 0){
                        dialogNormal(builder);
                    }
                    if(maxScore!=0 && score != 0){
                        if(score > maxScore)
                            isHighScore = true;
                        if(isHighScore){
                            dialogCustom(cusDialog);
                        }else{
                            dialogNormal(builder);
                        }
                    }
                    trueOrFalse = createOperator(1);
                }
            }
        });

        //Khi chọn đáp án sai
        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundBtn.start();
                buttonEffect(btnFalse);
                countDown = 0;
                if (!trueOrFalse) {
                    prbTime.setProgress((int) countDown);
                    timer = new CountDownTimer(1000, 10) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            countDown += 1.7;
                            prbTime.setProgress((int) countDown);
                        }

                        @Override
                        public void onFinish() {
                            if (countDown >= 100) {
                                if (countDown >= 100) {
                                    maxScore = db.getMaxScore();
                                    boolean isHighScore = false;
                                    if (maxScore==0 && score != 0) {
                                        dialogCustom(cusDialog);
                                    }
                                    if(maxScore==0 && score == 0){
                                        dialogNormal(builder);
                                    }
                                    if(maxScore!=0 && score == 0){
                                        dialogNormal(builder);
                                    }
                                    if(maxScore!=0 && score != 0){
                                        if(score > maxScore)
                                            isHighScore = true;
                                        if(isHighScore){
                                            dialogCustom(cusDialog);
                                        }else{
                                            dialogNormal(builder);
                                        }
                                    }
                                    trueOrFalse = createOperator(1);
                                }
                            }
                        }
                    };
                    timer.start();
                    score += 1;
                    txtScore.setText(String.valueOf(score));
                    if (score >= 0 && score <= 10) //Tăng level
                        trueOrFalse = createOperator(1); //Tạo một phép tính mới.
                    else if (score > 10 && score <= 20)
                        trueOrFalse = createOperator(2);
                    else if (score > 20)
                        trueOrFalse = createOperator(3);
                } else {
                    //Xem có điểm cao hơn hay ko?
                    maxScore = db.getMaxScore();
                    boolean isHighScore = false;
                    if (maxScore==0 && score != 0) {
                        dialogCustom(cusDialog);
                    }
                    if(maxScore==0 && score == 0){
                        dialogNormal(builder);
                    }
                    if(maxScore!=0 && score == 0){
                        dialogNormal(builder);
                    }
                    if(maxScore!=0 && score != 0){
                        if(score > maxScore)
                            isHighScore = true;
                        if(isHighScore){
                            dialogCustom(cusDialog);
                        }else{
                            dialogNormal(builder);
                        }
                    }
                    trueOrFalse = createOperator(1);
                }
            }
        });

        btnCup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreScreen = new Intent(MainActivity.this, ShowScoreActivity.class);
                startActivity(scoreScreen);
            }
        });
    }

    public void dialogNormal(AlertDialog.Builder builder){
        buzz.start();
        builder.setMessage("Your Score: " + String.valueOf(score));
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeColor();
                score = 0; //refesh score
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void dialogCustom(final Dialog cusDialog){
        cusDialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edtName.getText().toString();
                db.addScore(new Player(name, score));
                changeColor();
                score = 0;
                cusDialog.cancel();
            }
        });
    }
    public boolean createOperator(int level) {
        txtOperator = (TextView) findViewById(R.id.txtOperator);
        txtResult = (TextView) findViewById(R.id.txtResult);
        int op1 = 0, op2 = 0;
        Random r = new Random();
        int re = 0;
        switch (level) {
            case 1:
                op1 = (r.nextInt(5) + 1);
                op2 = (r.nextInt(5) + 1);
                break;
            case 2:
                op1 = (r.nextInt(10) + 1);
                op2 = (r.nextInt(10) + 1);
                break;
            case 3:
                op1 = (r.nextInt(19) + 1);
                op2 = (r.nextInt(19) + 1);
                break;
        }
        int trueOrFalse = r.nextInt(6) + 1;
        String oper = String.valueOf(op1) + " + " + String.valueOf(op2);

        if (trueOrFalse % 2 == 0) {
            re = op1 + op2;
            txtOperator.setText(oper);
            txtResult.setText("= " + String.valueOf(re));
            return true;
        } else {
            if ((op1 + op2 - trueOrFalse > 0))
                re = op1 + op2 - trueOrFalse;
            else re = op1 + op2 + trueOrFalse;
            txtOperator.setText(oper);
            txtResult.setText("= " + String.valueOf(re));
            return false;
        }
    }

    public void changeColor() {
        layoutColor = (RelativeLayout) findViewById(R.id.layoutMain);
        String[] colors = {"#37D258", "#DF35FA", "#FAC235", "#1be1e4"};
        Random r = new Random();
        int colorId = r.nextInt(colors.length) + 0;
        layoutColor.setBackgroundColor(Color.parseColor(colors[colorId]));
    }
}
