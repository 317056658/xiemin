package com.kkkcut.www.myapplicationkukai.dialogActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kkkcut.www.myapplicationkukai.DataCollect.KeyInformationBoxroomActivity;
import com.kkkcut.www.myapplicationkukai.R;
import com.kkkcut.www.myapplicationkukai.dao.SQLiteCollectAndCutHistoryDao;
import com.kkkcut.www.myapplicationkukai.entity.KeyInfo;
import com.kkkcut.www.myapplicationkukai.search.FrmKeySearchActivity;
import com.kkkcut.www.myapplicationkukai.utils.Tools;

/**
 * Created by Administrator on 2017/5/11.
 */

public class MessageTipsActivity extends AppCompatActivity implements View.OnClickListener {
    private View mDecorView;
    private LinearLayout mYesNo;
    private TextView mContent;
    private String name;
    private Intent mBackDataIntent;
    private Button mOk;
    private int msgTips, intentFlag;
    public static final int COLLECT_TIPS = 10;  //收藏温馨提示
    public static final int LENGTH_UNIT_TIPS = 1; //长度单位温馨提示
    public static final int KEY_INFO_UNFOUND_TIPS = 2;   //钥匙信息没有找到
    public static final int KEY_UNAVAILABLE_TIPS = 3;   //钥匙不可用
    public static final int NONSUPPORT_THIS_KEY_TIPS = 4;  //不支持此钥匙
    public static final int KEY_SPECIAL_DESCRIPTION_TIPS = 5; //钥匙特殊描述
    public static final int UNFOUND_KEY_TIPS = 6;//  没有找到钥匙
    public  static  final int COLLECT_SUCCEED=7; //收藏成功
    public  static  final  int  COLLECT_FAILURE=8;// 收藏失败
    private boolean isCollect;
    private Intent intent;
    private KeyInfo ki=null;
    private String stepText;
    private int startFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_hint);
        getIntentData();
        initViews();
    }

    /**
     * 启动当前Activity
     * @param ki
     * @param context
     * @param msgTips
     */
    public static void startMessageTipsActivity(Context context, int msgTips, KeyInfo ki,String step,int startFlag) {
        Intent intent = new Intent(context, MessageTipsActivity.class);
        intent.putExtra("msgTips", msgTips);
        intent.putExtra("keyInfo",ki);
        intent.putExtra("step",step);
        intent.putExtra("flag",startFlag);
        context.startActivity(intent);
    }
    public  static  void  startMessageTipsActivity(Context context,int msgTips){
        Intent intent=new Intent(context,MessageTipsActivity.class);
        intent.putExtra("msgTips", msgTips);
        context.startActivity(intent);
    }

    /**
     * 获得意图数据
     */
    private void getIntentData() {
        intent = getIntent();
        msgTips = intent.getIntExtra("msgTips", -1);
        name = intent.getStringExtra("name");
        intentFlag = intent.getIntExtra("IntentFlag", -1);
        ki=intent.getParcelableExtra("keyInfo");
        stepText  =intent.getStringExtra("step");
        startFlag=intent.getIntExtra("flag",-10);
    }

    /**
     * 倒计时类
     */
    private CountDownTimer timer = new CountDownTimer(3000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            finish();
        }
    };


    public void initViews() {
        mOk = (Button) findViewById(R.id.btn_close_window);
        mOk.setOnClickListener(this);
        mContent = (TextView) findViewById(R.id.tv_content);
        mYesNo = (LinearLayout) findViewById(R.id.ll_yes_no);
        Button mYes = (Button) findViewById(R.id.btn_yes);
        mYes.setOnClickListener(this);
        Button mNo = (Button) findViewById(R.id.btn_no);
        mNo.setOnClickListener(this);
        mDecorView = getWindow().getDecorView();
        switch (msgTips) {
            case LENGTH_UNIT_TIPS:   //1 代表长度单位提示
                mContent.setText("Do you want to save\nchanges?  [mm->Inch]");
                //显示yes no 按钮
                mYesNo.setVisibility(View.VISIBLE);
                mBackDataIntent = new Intent();
                break;
            case KEY_INFO_UNFOUND_TIPS:    //2 表示没有找到这个钥匙的id
                mContent.setText("ERROR:001 -> Key blank ID\n '-1' is not found.");
                mOk.setVisibility(View.VISIBLE);
                break;
            case KEY_UNAVAILABLE_TIPS:   // 3 表示 深度小于3.5mm；
                mContent.setText("Too small key has been\nselected.Cutting of this key\nis not available");
                mOk.setVisibility(View.VISIBLE);
                break;
            case NONSUPPORT_THIS_KEY_TIPS:
                mContent.setText("The data of this key is not supported by this machine");
                mOk.setVisibility(View.VISIBLE);
                break;
            case KEY_SPECIAL_DESCRIPTION_TIPS:
                String desc = ki.getDesc();
                int index = desc.indexOf("(");
                StringBuilder stringBuilder = new StringBuilder(desc);
                mContent.setText(stringBuilder.insert(index, "\n").toString().trim());
                mOk.setVisibility(View.VISIBLE);
                break;
            case UNFOUND_KEY_TIPS:   //6代表钥匙胚搜索数据 没有找到
                mContent.setText("No matching key was found.");
                timer.start();  //启动倒计时
                break;
            case COLLECT_TIPS:   //收藏提示
                mContent.setText("Do you want to add the\ncurrent key to favorite list?");
                mYesNo.setVisibility(View.VISIBLE);
                isCollect = true;
                break;
            case COLLECT_SUCCEED:
                mContent.setText("The current key was added\nto favorite list.");
                mOk.setVisibility(View.VISIBLE);
                break;
            case  COLLECT_FAILURE:  //收藏失败
                mContent.setText("The favorite list Most collections fifty data");
                mOk.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Tools.hideBottomUIMenu(mDecorView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                if (isCollect) {  //是收藏
                    SQLiteCollectAndCutHistoryDao sqLiteCollectAndCutHistoryDao=
                            SQLiteCollectAndCutHistoryDao.getInstance(getApplication());
                   int  dataQuantity=sqLiteCollectAndCutHistoryDao.getTableDataNum(SQLiteCollectAndCutHistoryDao.TABLE_FAVORITE);
                    if(dataQuantity< KeyInformationBoxroomActivity.FAVORITE_DATA_MAX){
                        sqLiteCollectAndCutHistoryDao.insertDataToFavorite(stepText,ki,startFlag);
                        startMessageTipsActivity(this,MessageTipsActivity.COLLECT_SUCCEED);
                        this.finish();
                    }else {
                        startMessageTipsActivity(this,MessageTipsActivity.COLLECT_FAILURE);
                        this.finish();
                    }
                } else {
                    mBackDataIntent.putExtra("data", name);
                    setResult(1, mBackDataIntent);
                    finish();
                }
                break;
            case R.id.btn_no:
                if(isCollect){
                    finish();
                }else {
                    mBackDataIntent.putExtra("data", name);
                    setResult(2, mBackDataIntent);
                    finish();
                }
                break;
            case R.id.btn_close_window:
                if (msgTips == 3) {
                    if (intentFlag == 1) {  //等于1 代表是数据库搜索ID界面过来的
                        Intent intent = new Intent(this, FrmKeySearchActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);  //加上这句话 避免界面重新创建
                        startActivity(intent);
                    }
                } else if (msgTips == 4) {
                    if (intentFlag == 1) {  //等于1 代表是数据库搜索ID界面过来的
                        Intent intent = new Intent(this, FrmKeySearchActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);  //加上这句话 避免界面重新创建
                        startActivity(intent);
                    }
                } else {
                    finish();
                }
                break;
        }
    }
}
