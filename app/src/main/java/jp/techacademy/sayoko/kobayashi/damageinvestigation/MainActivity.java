package jp.techacademy.sayoko.kobayashi.damageinvestigation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_INVESTIGATION = "jp.techacademy.sayoko.kobayashi.damegeinvestigationapp.INVESTIGATION";

    private Realm mRealm;
    private RealmChangeListener mRealmListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            reLoadListView();
        }
    };

    private ListView mListView;
    private InvestigationAdapter mInvestigationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BaseInputActivity.class);
                startActivity(intent);
            }
        });

        //Realmの設定
        mRealm = Realm.getDefaultInstance();
        mRealm.addChangeListener(mRealmListener);

        //ListViewの設定
        mInvestigationAdapter = new InvestigationAdapter(MainActivity.this);
        mListView = (ListView) findViewById(R.id.listView1);

        //ListViewをタップしたときの処理
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //入力・編集する画面に遷移する
                Investigation investigation = (Investigation) parent.getAdapter().getItem(position);

                Intent intent = new Intent(MainActivity.this, BaseInputActivity.class);
                intent.putExtra(EXTRA_INVESTIGATION,investigation.getId());

                startActivity(intent);
            }
        });
        //ListViewを長押ししたときの処理
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //調査データを削除する

                final Investigation investigation = (Investigation) parent.getAdapter().getItem(position);

                //ダイアログを表示する
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("削除");
                builder.setMessage(investigation.getRinpan() + "を削除しますか");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RealmResults<Investigation>results = mRealm.where(Investigation.class).equalTo("id", investigation.getId()).findAll();
                        mRealm.beginTransaction();
                        results.deleteAllFromRealm();
                        mRealm.commitTransaction();

                        reLoadListView();
                    }
                });

                builder.setNegativeButton("CANCEL", null);

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });


        reLoadListView();
    }

    private void reLoadListView() {
        //Realmデータベースから「全てのデータを取得して新しい日時順に並べた結果」を取得
        RealmResults<Investigation> investigationRealmResults = mRealm.where(Investigation.class).findAllSorted("date", Sort.DESCENDING);
        //上記の結果を、InvestigationListとしてセットする
        mInvestigationAdapter.setInvestigationList(mRealm.copyFromRealm(investigationRealmResults));
        //Investigation用のアダプタに渡す
        mListView.setAdapter(mInvestigationAdapter);
        //表示を更新するために、アダプターにデータが更新されたことを知らせる
        mInvestigationAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mRealm.close();
    }




}
