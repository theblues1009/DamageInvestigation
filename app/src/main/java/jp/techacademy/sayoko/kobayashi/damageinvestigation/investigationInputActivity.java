package jp.techacademy.sayoko.kobayashi.damageinvestigation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;
import android.util.Log;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.view.Gravity;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.lang.Math;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class investigationInputActivity extends AppCompatActivity {

    private Realm mRealm;
    private RealmChangeListener mRealmListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
        }
    };


    private TextView ryoko_counter_text;
    private TextView kesson_counter_text;
    private TextView koson_counter_text;
    private TextView dogari_counter_text;
    private TextView nonezumi_kare_counter_text;
    private TextView nonezumi_seizon_counter_text;
    private TextView yukigusare_kare_counter_text;
    private TextView yukigusare_seizon_counter_text;
    private TextView total_counter_text;
    private TextView damege_rate_text;

    private Button ryoko_add_push_btn;
    private Button ryoko_sub_push_btn;
    private Button kesson_add_push_btn;
    private Button kesson_sub_push_btn;
    private Button koson_add_push_btn;
    private Button koson_sub_push_btn;
    private Button dogari_add_push_btn;
    private Button dogari_sub_push_btn;
    private Button nonezumi_kare_add_push_btn;
    private Button nonezumi_kare_sub_push_btn;
    private Button nonezumi_seizon_add_push_btn;
    private Button nonezumi_seizon_sub_push_btn;
    private Button yukigusare_kare_add_push_btn;
    private Button yukigusare_kare_sub_push_btn;
    private Button yukigusare_seizon_add_push_btn;
    private Button yukigusare_seizon_sub_push_btn;
    private Button submit_btn;
    private Button output_btn;

    private int mRyoko;
    private int mKesson;
    private int mKoson;
    private int mDogari;
    private int mNonezumiSeizon;
    private int mNonezumiKare;
    private int mYukigusareKare;
    private int mYukigusareSeizon;
    private int mTotal;
    private double mRateOfDamage;
    private double mDamage;

    private EditText mRateOfDamageEdit;
    private Investigation mInvestigation;

    private static final int PERMISSIONS_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigation_input);

        mRealm = Realm.getDefaultInstance();
        mRealm.addChangeListener(mRealmListener);

        //EXTRA_INVESTIGATIONからInvestigationのidを取得して、idからInvestigationのインスタンスを取得する
        Intent intent = getIntent();
        int investigationId = intent.getIntExtra(MainActivity.EXTRA_INVESTIGATION, -1);
        Realm realm = Realm.getDefaultInstance();
        mInvestigation = realm.where(Investigation.class).equalTo("id", investigationId).findFirst();
        realm.close();

        if (mInvestigation == null) {
            mInvestigation = new Investigation();
            RealmResults<Investigation> investigationRealmResults = realm.where(Investigation.class).findAll();
            int identifier;
            if (investigationRealmResults.max("id") != null) {
                identifier = investigationRealmResults.max("id").intValue() + 1;
            } else {
                identifier = 0;
            }
            mInvestigation.setId(identifier);


        } else {


            TextView textView1 = (TextView) findViewById(R.id.rinpan_edit_text);
            TextView textView2 = (TextView) findViewById(R.id.syohan_edit_text);
            TextView textView3 = (TextView) findViewById(R.id.area_edit_text);
            TextView textView4 = (TextView) findViewById(R.id.number_edit_text);

            textView1.setText(String.valueOf(mInvestigation.getRinpan()));
            textView2.setText(String.valueOf(mInvestigation.getSyohan()));
            textView3.setText(String.valueOf(mInvestigation.getArea()));

            int d = mInvestigation.getNumber();
            int e = mInvestigation.getRateOfInvestigation();

            textView4.setText(String.valueOf(d * e / 100));

            if (String.valueOf(mInvestigation.getRyoko()) == null || String.valueOf(mInvestigation.getRyoko()).length() == 0) {
                mRyoko = 0;
            } else {
                mRyoko = mInvestigation.getRyoko();
            }
            if (String.valueOf(mInvestigation.getKesson()) == null || String.valueOf(mInvestigation.getKesson()).length() == 0) {
                mKesson = 0;
            } else {
                mKesson = mInvestigation.getKesson();
            }
            if (String.valueOf(mInvestigation.getKoson()) == null || String.valueOf(mInvestigation.getKoson()).length() == 0) {
                mKoson = 0;
            } else {
                mKoson = mInvestigation.getKoson();
            }
            if (String.valueOf(mInvestigation.getDogari()) == null || String.valueOf(mInvestigation.getDogari()).length() == 0) {
                mDogari = 0;
            } else {
                mDogari = mInvestigation.getDogari();
            }
            if (String.valueOf(mInvestigation.getNonezumi_kare()) == null || String.valueOf(mInvestigation.getNonezumi_kare()).length() == 0) {
                mNonezumiKare = 0;
            } else {
                mNonezumiKare = mInvestigation.getNonezumi_kare();
            }
            if (String.valueOf(mInvestigation.getNonezumi_seizon()) == null || String.valueOf(mInvestigation.getNonezumi_seizon()).length() == 0) {
                mNonezumiSeizon = 0;
            } else {
                mNonezumiSeizon = mInvestigation.getNonezumi_seizon();
            }
            if (String.valueOf(mInvestigation.getYukigusare_kare()) == null || String.valueOf(mInvestigation.getYukigusare_kare()).length() == 0) {
                mYukigusareKare = 0;
            } else {
                mNonezumiKare = mInvestigation.getNonezumi_kare();
            }

            if (String.valueOf(mInvestigation.getYukigusare_seizon()) == null || String.valueOf(mInvestigation.getYukigusare_seizon()).length() == 0) {
                mYukigusareSeizon = 0;
            } else {
                mYukigusareSeizon = mInvestigation.getYukigusare_seizon();
            }

            ryoko_counter_text = (TextView) findViewById(R.id.ryoko_total);
            kesson_counter_text = (TextView) findViewById(R.id.kesson_total);
            koson_counter_text = (TextView) findViewById(R.id.koson_total);
            dogari_counter_text = (TextView) findViewById(R.id.dogari_total);
            nonezumi_kare_counter_text = (TextView) findViewById(R.id.nonezumi_kare_total);
            nonezumi_seizon_counter_text = (TextView) findViewById(R.id.nonezumi_seizon_total);
            yukigusare_kare_counter_text = (TextView) findViewById(R.id.yukigusare_kare_total);
            yukigusare_seizon_counter_text = (TextView) findViewById(R.id.yukigusare_seizon_total);

            ryoko_counter_text.setText(String.valueOf(mRyoko));
            kesson_counter_text.setText(String.valueOf(mKesson));
            koson_counter_text.setText(String.valueOf(mKoson));
            dogari_counter_text.setText(String.valueOf(mDogari));
            nonezumi_kare_counter_text.setText(String.valueOf(mNonezumiKare));
            nonezumi_seizon_counter_text.setText(String.valueOf(mNonezumiSeizon));
            yukigusare_kare_counter_text.setText(String.valueOf(mYukigusareKare));
            yukigusare_seizon_counter_text.setText(String.valueOf(mYukigusareSeizon));
        }

        // 画面のアイテムと紐付け_良好
        ryoko_counter_text = (TextView)

                findViewById(R.id.ryoko_total);

        ryoko_add_push_btn = (Button)

                findViewById(R.id.ryoko_add);

        ryoko_sub_push_btn = (Button)

                findViewById(R.id.ryoko_sub);
        //クリック_良好
        ryoko_add_push_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                mRyoko++;
                ryoko_counter_text.setText(String.valueOf(mRyoko));
                Total();
                RateOfDamage();
                alertMax();
            }
        });
        ryoko_sub_push_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (mRyoko == 0) {

                } else {
                    mRyoko--;
                    ryoko_counter_text.setText(String.valueOf(mRyoko));
                    Total();
                    RateOfDamage();
                }

            }
        });

        // 画面のアイテムと紐付け_欠損
        kesson_counter_text = (TextView)

                findViewById(R.id.kesson_total);

        kesson_add_push_btn = (Button)

                findViewById(R.id.kesson_add);

        kesson_sub_push_btn = (Button)

                findViewById(R.id.kesson_sub);
        //クリック_欠損
        kesson_add_push_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                mKesson++;
                kesson_counter_text.setText(String.valueOf(mKesson));
                Total();
                RateOfDamage();
                alertMax();
            }
        });
        kesson_sub_push_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (mKesson == 0) {
                    Toast.makeText(investigationInputActivity.this, "調査本数が負の値になることはありません", Toast.LENGTH_SHORT).show();
                } else {
                    mKesson--;
                    kesson_counter_text.setText(String.valueOf(mKesson));
                    Total();
                    RateOfDamage();
                }

            }
        });

        // 画面のアイテムと紐付け_枯損
        koson_counter_text = (TextView)

                findViewById(R.id.koson_total);

        koson_add_push_btn = (Button)

                findViewById(R.id.koson_add);

        koson_sub_push_btn = (Button)

                findViewById(R.id.koson_sub);
        //クリック_枯損
        koson_add_push_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                mKoson++;
                koson_counter_text.setText(String.valueOf(mKoson));
                Total();
                RateOfDamage();
                alertMax();
            }
        });
        koson_sub_push_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (mKoson == 0) {
                    Toast.makeText(investigationInputActivity.this, "調査本数が負の値になることはありません", Toast.LENGTH_SHORT).show();
                } else {
                    mKoson--;
                    koson_counter_text.setText(String.valueOf(mKoson));
                    Total();
                    RateOfDamage();
                }
            }
        });

        // 画面のアイテムと紐付け_胴刈
        dogari_counter_text = (TextView)

                findViewById(R.id.dogari_total);

        dogari_add_push_btn = (Button)

                findViewById(R.id.dogari_add);

        dogari_sub_push_btn = (Button)

                findViewById(R.id.dogari_sub);
        //クリック_胴刈
        dogari_add_push_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                mDogari++;
                dogari_counter_text.setText(String.valueOf(mDogari));
                Total();
                RateOfDamage();
                alertMax();
            }
        });

        dogari_sub_push_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (mDogari == 0) {
                    Toast.makeText(investigationInputActivity.this, "調査本数が負の値になることはありません", Toast.LENGTH_SHORT).show();
                } else {
                    mDogari--;
                    dogari_counter_text.setText(String.valueOf(mDogari));
                    Total();
                    RateOfDamage();
                }
            }
        });

        // 画面のアイテムと紐付け_野ネズミ枯れ
        nonezumi_kare_counter_text = (TextView)

                findViewById(R.id.nonezumi_kare_total);

        nonezumi_kare_add_push_btn = (Button)

                findViewById(R.id.nonezumi_kare_add);

        nonezumi_kare_sub_push_btn = (Button)

                findViewById(R.id.nonezumi_kare_sub);
        //クリック_野ネズミ枯れ
        nonezumi_kare_add_push_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                mNonezumiKare++;
                nonezumi_kare_counter_text.setText(String.valueOf(mNonezumiKare));
                Total();
                RateOfDamage();
                alertMax();
            }
        });

        nonezumi_kare_sub_push_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (mNonezumiKare == 0) {
                    Toast.makeText(investigationInputActivity.this, "調査本数が負の値になることはありません", Toast.LENGTH_SHORT).show();
                } else {
                    mNonezumiKare--;
                    nonezumi_kare_counter_text.setText(String.valueOf(mNonezumiKare));
                    Total();
                    RateOfDamage();
                }

            }
        });

        // 画面のアイテムと紐付け_野ネズミ生存
        nonezumi_seizon_counter_text = (TextView)

                findViewById(R.id.nonezumi_seizon_total);

        nonezumi_seizon_add_push_btn = (Button)

                findViewById(R.id.nonezumi_seizon_add);

        nonezumi_seizon_sub_push_btn = (Button)

                findViewById(R.id.nonezumi_seizon_sub);
        //クリック_野ネズミ生存
        nonezumi_seizon_add_push_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                mNonezumiSeizon++;
                nonezumi_seizon_counter_text.setText(String.valueOf(mNonezumiSeizon));
                Total();
                RateOfDamage();
                alertMax();
            }
        });

        nonezumi_seizon_sub_push_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (mNonezumiSeizon == 0) {
                    Toast.makeText(investigationInputActivity.this, "調査本数が負の値になることはありません", Toast.LENGTH_SHORT).show();
                } else {
                    mNonezumiSeizon--;
                    nonezumi_seizon_counter_text.setText(String.valueOf(mNonezumiSeizon));
                    Total();
                    RateOfDamage();
                }
            }
        });

        // 画面のアイテムと紐付け_雪腐れ枯れ
        yukigusare_kare_counter_text = (TextView)

                findViewById(R.id.yukigusare_kare_total);

        yukigusare_kare_add_push_btn = (Button)

                findViewById(R.id.yukigusare_kare_add);

        yukigusare_kare_sub_push_btn = (Button)

                findViewById(R.id.yukigusare_kare_sub);
        //クリック_雪腐れ枯れ
        yukigusare_kare_add_push_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                mYukigusareKare++;
                yukigusare_kare_counter_text.setText(String.valueOf(mYukigusareKare));
                Total();
                RateOfDamage();
                alertMax();
            }
        });

        yukigusare_kare_sub_push_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (mYukigusareKare == 0) {
                    Toast.makeText(investigationInputActivity.this, "調査本数が負の値になることはありません", Toast.LENGTH_SHORT).show();
                } else {
                    mYukigusareKare--;
                    yukigusare_kare_counter_text.setText(String.valueOf(mYukigusareKare));
                    Total();
                    RateOfDamage();
                }
            }
        });

        // 画面のアイテムと紐付け_雪腐れ生存
        yukigusare_seizon_counter_text = (TextView)

                findViewById(R.id.yukigusare_seizon_total);

        yukigusare_seizon_add_push_btn = (Button)

                findViewById(R.id.yukigusare_seizon_add);

        yukigusare_seizon_sub_push_btn = (Button)

                findViewById(R.id.yukigusare_seizon_sub);
        //クリック_雪腐れ生存
        yukigusare_seizon_add_push_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                mYukigusareSeizon++;
                yukigusare_seizon_counter_text.setText(String.valueOf(mYukigusareSeizon));
                Total();
                RateOfDamage();
                alertMax();
            }
        });

        yukigusare_seizon_sub_push_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (mYukigusareSeizon == 0) {
                    Toast.makeText(investigationInputActivity.this, "調査本数が負の値になることはありません", Toast.LENGTH_SHORT).show();
                } else {
                    mYukigusareSeizon--;
                    yukigusare_seizon_counter_text.setText(String.valueOf(mYukigusareSeizon));
                    Total();
                    RateOfDamage();
                }
            }
        });

        submit_btn = (Button)

                findViewById(R.id.submit);
        submit_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                if (mInvestigation == null) {
                    mInvestigation = new Investigation();

                    RealmResults<Investigation> investigationRealmResults = realm.where(Investigation.class).findAll();

                    int identifier;
                    if (investigationRealmResults.max("id") != null) {
                        identifier = investigationRealmResults.max("id").intValue() + 1;
                    } else {
                        identifier = 0;
                    }
                    mInvestigation.setId(identifier);
                }


                mInvestigation.setRyoko(mRyoko);
                mInvestigation.setKesson(mKesson);
                mInvestigation.setKoson(mKoson);
                mInvestigation.setDogari(mDogari);
                mInvestigation.setNonezumi_seizon(mNonezumiSeizon);
                mInvestigation.setNonezumi_kare(mNonezumiKare);
                mInvestigation.setYukigusare_seizon(mYukigusareSeizon);
                mInvestigation.setYukigusare_kare(mYukigusareKare);
                mInvestigation.setDamege_rate(mRateOfDamage);


                realm.copyToRealmOrUpdate(mInvestigation);
                realm.commitTransaction();

                realm.close();

                Intent intent = new Intent(investigationInputActivity.this, MainActivity.class);
                startActivity(intent);
            }


        });
        output_btn = (Button) findViewById(R.id.output);
        output_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Android 6.0以降の場合
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

        Total();
        RateOfDamage();

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
        String filename = sdf.format(date) + "-" + mInvestigation.getRinpan() + "林班" + mInvestigation.getSyohan() + "小班.csv";

        try {
            //SDカードフォルダのパス
            String sdPath = Environment.getExternalStorageDirectory().getPath();
            //作成するファイル名
            String fileName = "/" + filename;

            String fullPath = sdPath + fileName;
            //書き込み
            BufferedWriter bw = null;
            try {
                toast(fullPath + "test2");
                FileWriter fw = new FileWriter(fullPath);
                toast(fullPath + "test3");
                bw = new BufferedWriter(fw);
                bw.write("林班" + ", " + "小班" + ", " + "面積" + ", " + "植栽本数"+ ", "+"調査率"+ ", "+"調査者"+ ", "
                        +"良好"+ ", "+"枯損"+ ", "+"欠損"+ ", "+"胴刈"+ ", "+"野ネズミ枯れ"+ ", "+"野ネズミ生存"+ ", "+"雪腐れ枯れ"+ ", "+"雪腐れ生存" + "\r\n" +
                        String.valueOf(mInvestigation.getRinpan()) + ", " + String.valueOf(mInvestigation.getSyohan()) + ", "
                        + String.valueOf(mInvestigation.getArea()) + ", " + String.valueOf(mInvestigation.getNumber()) + ", "
                        + String.valueOf(mInvestigation.getRateOfInvestigation()) + ", " + String.valueOf(mInvestigation.getInvestigators()) + ", "
                        + mRyoko+ ", " + mKoson + ", " + mKesson + ", " + mDogari + ", " + mNonezumiKare + ", "
                        +mNonezumiSeizon + ", " + mYukigusareKare + ", " + mYukigusareSeizon + ", ");

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

    public void Total() {
        total_counter_text = (TextView) findViewById(R.id.total_text);
        mTotal = mRyoko + mKesson + mKoson + mDogari + mNonezumiSeizon + mNonezumiKare + mYukigusareKare + mYukigusareSeizon;
        total_counter_text.setText(String.valueOf(mTotal));
    }

    public void RateOfDamage() {
        damege_rate_text = (TextView) findViewById(R.id.damege_rate_text);
        if (mTotal == 0) {
            damege_rate_text.setText(String.valueOf(""));
        } else {
            mDamage = (double)mTotal - (double)mRyoko;
            mRateOfDamage = (double)Math.round((mDamage/ (double)mTotal)*100 );
            damege_rate_text.setText(String.valueOf(mRateOfDamage));
        }


    }

    public void alertMax() {

        //EXTRA_INVESTIGATIONからInvestigationのidを取得して、idからInvestigationのインスタンスを取得する
        Intent intent = getIntent();
        int investigationId = intent.getIntExtra(MainActivity.EXTRA_INVESTIGATION, -1);
        Realm realm = Realm.getDefaultInstance();
        mInvestigation = realm.where(Investigation.class).equalTo("id", investigationId).findFirst();
        realm.close();

        if (mInvestigation == null) {

        } else {

            if (mTotal == (mInvestigation.getNumber() * mInvestigation.getRateOfInvestigation()) / 100) {
                showAlertDialog();
            }
        }
    }


    private void showAlertDialog() {
        //AlertDialog.Builderクラスを使ってAlertDialogの準備をする
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("お知らせ");
        alertDialogBuilder.setMessage("調査予定本数に達しました！");

        //ボタンに表示される文字列、押したときのリスナーを設定
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


}




