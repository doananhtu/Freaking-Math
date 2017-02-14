package myapp.anhtu.com.freakingmath.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import myapp.anhtu.com.freakingmath.R;

import static myapp.anhtu.com.freakingmath.ulti.lib.buttonEffect;

public class MainActivity extends AppCompatActivity {
    TextView txtOperator, txtResult, txtScore;
    ImageButton btnTrue, btnFalse;
    ProgressBar prbTime;
    CountDownTimer timer;
    RelativeLayout layoutColor;
    boolean trueOrFalse;
    int score = 0;
    float countDown = 0;
    MediaPlayer soundBtn,buzz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //
        btnTrue = (ImageButton)findViewById(R.id.btnTrue);
        btnFalse = (ImageButton)findViewById(R.id.btnFalse);
        txtScore = (TextView)findViewById(R.id.txtScore);
        txtScore.setText(String.valueOf(score));
        prbTime = (ProgressBar)findViewById(R.id.prbTime);
        soundBtn = MediaPlayer.create(MainActivity.this,R.raw.btnsound);
        buzz = MediaPlayer.create(MainActivity.this,R.raw.buzz);
        prbTime.getProgressDrawable().setColorFilter(Color.RED,android.graphics.PorterDuff.Mode.SRC_IN);

        //Dialog init
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Game Over");
        builder.setCancelable(false);
        //Kết quả đúng hay sai
        trueOrFalse = createOperator(1);
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundBtn.start();
                buttonEffect(btnTrue);
                countDown = 0;
                if(trueOrFalse){
                    prbTime.setProgress((int)countDown);
                    timer = new CountDownTimer(1000,10) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            countDown+=1.7;
                            prbTime.setProgress((int)countDown);
                        }

                        @Override
                        public void onFinish() {
                            if(countDown>=100){
                                buzz.start();
                                builder.setMessage("Your Score: "+ String.valueOf(score));
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
                        }
                    };
                    timer.start();
                    score += 1;
                    txtScore.setText(String.valueOf(score));
                    if(score>=0 && score<=10) //Tăng level
                        trueOrFalse = createOperator(1); //Tạo một phép tính mới.
                    else if(score>10 && score<=20)
                        trueOrFalse = createOperator(2);
                    else if(score>20)
                        trueOrFalse = createOperator(3);
                }else{
                    timer.cancel(); // fix bug độ trễ của countDownTimer
                    buzz.start();
                    builder.setMessage("Your Score: "+ String.valueOf(score));
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            changeColor();
                            score = 0; //refesh score
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    trueOrFalse = createOperator(1);
                }
            }
        });

        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundBtn.start();
                buttonEffect(btnFalse);
                countDown = 0;
                if(!trueOrFalse){
                    prbTime.setProgress((int)countDown);
                    timer = new CountDownTimer(1000,10) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            countDown+=1.7;
                            prbTime.setProgress((int)countDown);
                        }

                        @Override
                        public void onFinish() {
                            if(countDown>=100){
                                buzz.start();
                                builder.setMessage("Your Score: "+ String.valueOf(score));
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
                        }
                    };
                    timer.start();
                    score += 1;
                    txtScore.setText(String.valueOf(score));
                    if(score>=0 && score<=10) //Tăng level
                        trueOrFalse = createOperator(1); //Tạo một phép tính mới.
                    else if(score>10 && score<=20)
                        trueOrFalse = createOperator(2);
                    else if(score>20)
                        trueOrFalse = createOperator(3);
                }else{
                    timer.cancel();
                    buzz.start();
                    builder.setMessage("Your Score: "+ String.valueOf(score));
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            changeColor();
                            score = 0; //refesh score
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    trueOrFalse = createOperator(1);
                }
            }
        });
    }

    public boolean createOperator(int level){
        txtOperator = (TextView)findViewById(R.id.txtOperator);
        txtResult = (TextView)findViewById(R.id.txtResult);
        int op1 = 0,op2 = 0;
        Random r = new Random();
        int re = 0;
        switch (level){
            case 1:
                op1 = (r.nextInt(5) + 1);
                op2 = (r.nextInt(5) + 1);
                break;
            case 2:
                op1 = (r.nextInt(20) + 1);
                op2 = (r.nextInt(20) + 1);
                break;
            case 3:
                op1 = (r.nextInt(49) + 1);
                op2 = (r.nextInt(49) + 1);
                break;
        }
        int trueOrFalse = r.nextInt(6)+1;
        String oper = String.valueOf(op1) + " + " + String.valueOf(op2);

        if(trueOrFalse%2 == 0){
            re = op1 + op2;
            txtOperator.setText(oper);
            txtResult.setText("= " + String.valueOf(re));
            return true;
        }else{
            if((op1+op2-trueOrFalse>0))
                re = op1+op2-trueOrFalse;
            else re = op1+op2+trueOrFalse;
            txtOperator.setText(oper);
            txtResult.setText("= " + String.valueOf(re));
            return false;
        }
    }

    public void changeColor(){
        layoutColor = (RelativeLayout)findViewById(R.id.layoutMain);
        String[] colors = {"#37D258","#DF35FA","#FAC235","#1be1e4"};
        Random r = new Random();
        int colorId = r.nextInt(colors.length)+0;
        layoutColor.setBackgroundColor(Color.parseColor(colors[colorId]));
    }
}
