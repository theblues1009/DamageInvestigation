package jp.techacademy.sayoko.kobayashi.damageinvestigation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Button;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import android.Manifest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

import static jp.techacademy.sayoko.kobayashi.damageinvestigation.R.id.parent;


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
    private Button output_btn;
    private Investigation mInvestigation;

    private static final int PERMISSIONS_REQUEST_CODE = 100;


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
        //メニュー定義ファイルを元にオプションメニューを生成


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
                Intent intent = new Intent(MainActivity.this, investigationInputActivity.class);
                intent.putExtra(EXTRA_INVESTIGATION, investigation.getId());

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
                        RealmResults<Investigation> results = mRealm.where(Investigation.class).equalTo("id", investigation.getId()).findAll();
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


        output_btn = (Button) findViewById(R.id.output2);
        output_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Android 6.0以降の場合
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // パーミッションの許可状態を確認する
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        // 許可されている
//                saveFile();

                        save2();
                    } else {
                        // 許可されていないので許可ダイアログを表示する
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
                    }
                    // Android 5系以下の場合
                } else {
//            saveFile();

                    save2();
                }
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    //メニュー選択時にトースト表示
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Toast toast = Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG);
        toast.show();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    saveFile();
                    save2();
                }
                break;
            default:
                break;
        }
    }

    public void save2() {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.JAPANESE);
        String filename = sdf.format(date) + ".csv";

        try {
            //SDカードフォルダのパス
            String sdPath = Environment.getExternalStorageDirectory().getPath();
            //作成するファイル名
            String fileName = "/" + filename;

            String fullPath = sdPath + fileName;
            //書き込み
            BufferedWriter bw = null;
            try {
                FileWriter fw = new FileWriter(fullPath);
                bw = new BufferedWriter(fw);
                RealmResults<Investigation> results = mRealm.where(Investigation.class).findAll();
                bw.write("林班" + ", " + "小班" + ", " + "樹種" + ", " + "面積" + ", " + "植栽本数" + ", " + "調査率" + ", " + "調査者" + ", "
                        + "良好" + ", " + "枯損" + ", " + "欠損" + ", " + "胴刈" + ", " + "野ネズミ枯れ" + ", " + "野ネズミ生存" + ", "
                        + "雪腐れ枯れ" + ", " + "雪腐れ生存" + "\r\n");

                mRealm.beginTransaction();
                for (int i = 0; i < results.size(); i++) {

                    bw.write(String.valueOf(results.get(i).getRinpan()) + ", " + String.valueOf(results.get(i).getSyohan()) + ", "
                            + String.valueOf(results.get(i).getTree_species()) + ", " + String.valueOf(results.get(i).getArea()) + ", " + String.valueOf(results.get(i).getNumber()) + ", "
                            + String.valueOf(results.get(i).getRateOfInvestigation()) + ", " + String.valueOf(results.get(i).getInvestigators()) + ", "
                            + String.valueOf(results.get(i).getRyoko()) + ", " + String.valueOf(results.get(i).getKoson()) + ", " + String.valueOf(results.get(i).getKesson()) + ", "
                            + String.valueOf(results.get(i).getDogari()) + ", " + String.valueOf(results.get(i).getNonezumi_kare()) + ", "
                            + String.valueOf(results.get(i).getNonezumi_seizon()) + ", " + String.valueOf(results.get(i).getYukigusare_kare()) + ", " + String.valueOf(results.get(i).getYukigusare_seizon()) + "\r\n");
                }
            } finally {
                toast(fullPath + "に保存しました。");
                bw.flush();
                bw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void toast(String mes) {
        Toast ts = Toast.makeText(this, mes, Toast.LENGTH_LONG);
        ts.setGravity(Gravity.CENTER, 0, 0);
        ts.show();
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
