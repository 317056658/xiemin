package com.kkkcut.www.myapplicationkukai.DataCollect;


import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.google.gson.Gson;
import com.kkkcut.www.myapplicationkukai.R;
import com.kkkcut.www.myapplicationkukai.adapter.MyDataAdapter;
import com.kkkcut.www.myapplicationkukai.dao.SQLiteCollectAndCutHistoryDao;
import com.kkkcut.www.myapplicationkukai.entity.CollectCutHistory;
import com.kkkcut.www.myapplicationkukai.entity.KeyInfo;
import com.kkkcut.www.myapplicationkukai.publicKeyCut.FrmKeyCutMainActivity;
import com.kkkcut.www.myapplicationkukai.serialDriverCommunication.ProlificSerialDriver;
import com.kkkcut.www.myapplicationkukai.utils.GsonUtils;
import com.kkkcut.www.myapplicationkukai.utils.Tools;

import java.util.HashMap;
import java.util.List;

/**
 * 钥匙信息储藏室Activity
 */
public class KeyInformationBoxroomActivity extends AppCompatActivity implements BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {
    //获得window装饰类
    private View mDecorView;
    private ProlificSerialDriver driver;
    private HashMap<String, String> languageMap;
    public static final int FLAG_FAVORITE = 1001;  //收藏夹
    public static final int FLAG_CUT_HISTORY = 1002;//切割记录\
    public static final int FAVORITE_DATA_MAX = 50;
    public static final int CUT_HISTORY_DATA_MAX = 50;
   private   Context mContext;
    /**
     * 已经获取到多少条数据了
     */
    private int mCurrentCounter = 0;
    private boolean isLoadData;
    private int mReceiveFlag;
    private List<CollectCutHistory> dataSet;
    private Button mBtnCloseActivity;
    private RecyclerView mRecyclerBoxroom;
    private MyDataAdapter mDataAdapter;
    int dataQuantity;
    private SQLiteCollectAndCutHistoryDao sqLiteCollectAndCutHistoryDao;
    private int offset = 0;
    private Gson gson;
    private String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_and_history);
        this.getIntentData();
        initViews();

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
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    private void initViews() {
        mContext=this;
        mDecorView = getWindow().getDecorView();
       Button  mBtnDeleteAll= (Button)findViewById(R.id.btn_delete_all);
        mBtnDeleteAll.setOnClickListener(this);
        mBtnCloseActivity = (Button) findViewById(R.id.btn_close_activity);
        mBtnCloseActivity.setOnClickListener(this);
        TextView mTitle = (TextView) findViewById(R.id.tv_title);
        TextView mListSize = (TextView) findViewById(R.id.tv_list_size);
        switch (mReceiveFlag) {
            case FLAG_FAVORITE:
                mTitle.setText("Favorite Key Blank");//设置标题
                sqLiteCollectAndCutHistoryDao = SQLiteCollectAndCutHistoryDao.getInstance(getApplication());
                tableName=SQLiteCollectAndCutHistoryDao.TABLE_FAVORITE;
                dataQuantity = sqLiteCollectAndCutHistoryDao.getTableDataNum(tableName);
                mListSize.setText("Favorite Size:" + dataQuantity + "/50");  //设置显示的收藏数量
                dataSet = sqLiteCollectAndCutHistoryDao.queryTableTenData(tableName,0);
                break;
            case FLAG_CUT_HISTORY:
                mTitle.setText("Cut History");
                sqLiteCollectAndCutHistoryDao = SQLiteCollectAndCutHistoryDao.getInstance(getApplication());
                tableName= SQLiteCollectAndCutHistoryDao.TABLE_CUT_HISTORY;
                dataQuantity = sqLiteCollectAndCutHistoryDao.getTableDataNum(tableName);
                mListSize.setText("Cut History Size:"+dataQuantity + "/50"); //设置显示的收藏数量
                dataSet= sqLiteCollectAndCutHistoryDao.queryTableTenData(tableName,0);
                break;
        }
        mRecyclerBoxroom = (RecyclerView) findViewById(R.id.recycler_boxroom); //获得Recycler实例
        mRecyclerBoxroom.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(false);
        mRecyclerBoxroom.setLayoutManager(layoutManager);

        mCurrentCounter += dataSet.size();
        //item拖拽事件
        OnItemDragListener onItemDragListener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {

            }
        };

        mDataAdapter = new MyDataAdapter(dataSet);
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mDataAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerBoxroom);
        //item侧滑事件
        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                //删除滑动中单个数据
                sqLiteCollectAndCutHistoryDao.deleteTableSingleData(tableName,mDataAdapter.getData().get(pos).getId());
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        };
        //开启拖拽
//        mDataAdapter.enableDragItem(itemTouchHelper);
//        mDataAdapter.setOnItemDragListener(onItemDragListener);
        // 开启滑动删除
        mDataAdapter.enableSwipeItem();
        mDataAdapter.setOnItemSwipeListener(onItemSwipeListener);
        mDataAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(mContext, "onItemClick" + dataSet.get(position).getStartFlag() ,Toast.LENGTH_SHORT).show();
                CollectCutHistory cch=dataSet.get(position);
                gson= GsonUtils.Holder.getInstance();
               KeyInfo ki =gson.fromJson(cch.getKeyInfo(),KeyInfo.class);
                FrmKeyCutMainActivity.startFrmKeyCutMainActivity(mContext,ki,cch.getStep(),languageMap,cch.getStartFlag());
                finish();
            }
        });
        mRecyclerBoxroom.setAdapter(mDataAdapter);
        mDataAdapter.setOnLoadMoreListener(this, mRecyclerBoxroom);
        mDataAdapter.disableLoadMoreIfNotFullPage();
        mDataAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);  //开启动画效果
        mDataAdapter.isFirstOnly(false);

    }


    /**
     * 启动当前Activity
     *
     * @param context
     * @param languageMap
     */
    public static void startFavoriteActivity(Context context, HashMap<String, String> languageMap, int flag) {
        Intent intent = new Intent(context, KeyInformationBoxroomActivity.class);
        intent.putExtra("language", languageMap);
        intent.putExtra("flag", flag);
        context.startActivity(intent);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        languageMap = (HashMap<String, String>) intent.getSerializableExtra("language");
        mReceiveFlag = intent.getIntExtra("flag", -1);
    }


    @Override
    public void onLoadMoreRequested() {
        mRecyclerBoxroom.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mCurrentCounter >= dataQuantity) {
                    mDataAdapter.loadMoreEnd();  //加载更多结束
                } else {
                    if (isLoadData) {
                        offset += 10;
                        dataSet = sqLiteCollectAndCutHistoryDao.queryTableTenData(tableName,offset);
                        mDataAdapter.addData(dataSet);
                        mCurrentCounter += dataSet.size();
                        Log.d("是好多？", "run: " + mCurrentCounter);
                        mDataAdapter.loadMoreComplete();
                    } else {
                        isLoadData = true;
                        mDataAdapter.loadMoreComplete();
                    }
                }

            }
        }, 700);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close_activity:
                finish();
                break;
            case R.id.btn_delete_all:
                if(mDataAdapter.getData().size()>=1){
                    sqLiteCollectAndCutHistoryDao.deleteTableAllData(tableName);  // 删除全部
                    mDataAdapter.getData().clear();
                    mRecyclerBoxroom.removeAllViews();
                    mDataAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}