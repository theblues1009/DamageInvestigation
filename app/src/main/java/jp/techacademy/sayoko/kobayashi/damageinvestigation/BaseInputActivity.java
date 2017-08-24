package jp.techacademy.sayoko.kobayashi.damageinvestigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class BaseInputActivity extends AppCompatActivity {
    private EditText mRinpanEdit, mSyohanEdit, mAreaEdit, mNumberEdit, mRateOfInvestigationEdit, mInvestigatorsEdit;
    private Spinner sp;

    private Investigation mInvestigation;

    private View.OnClickListener mOnDoneClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addInvestigation();
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_input);

        //ActionBarの設置
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //UI部品の設定
        findViewById(R.id.done_button).setOnClickListener(mOnDoneClickListner);
        mRinpanEdit = (EditText) findViewById(R.id.rinpan_edit_text);
        mSyohanEdit = (EditText) findViewById(R.id.syohan_edit_text);
        sp = (Spinner) findViewById(R.id.spn_treeSpecies);
        mAreaEdit = (EditText) findViewById(R.id.area_edit_text);
        mNumberEdit = (EditText) findViewById(R.id.number_edit_text);
        mRateOfInvestigationEdit = (EditText) findViewById(R.id.rate_of_invesrtigation_edit_text);
        mInvestigatorsEdit = (EditText) findViewById(R.id.investigators_edit_text);

        //EXTRA_INVESTIGATIONからInvestigationのidを取得して、idからInvestigationのインスタンスを取得する
        Intent intent = getIntent();
        int investigationId = intent.getIntExtra(MainActivity.EXTRA_INVESTIGATION, -1);
        Realm realm = Realm.getDefaultInstance();
        mInvestigation = realm.where(Investigation.class).equalTo("id", investigationId).findFirst();
        realm.close();

        if (mInvestigation == null) {
            //新規作成の場合

        } else {
            //更新の場合
            mRinpanEdit.setText(mInvestigation.getRinpan());
            mSyohanEdit.setText(mInvestigation.getSyohan());
            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                             //項目が選択された場合の処理
                                             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                 Spinner sp = (Spinner) parent;
                                                 //選択項目を取得し、その値をトースト表示
                                                 Toast.makeText(BaseInputActivity.this, String.format("選択項目:%S", sp.getSelectedItem()),
                                                         Toast.LENGTH_SHORT).show();
                                             }

                                             //項目が選択されなかった場合の処理（今回は空）
                                             public void onNothingSelected(AdapterView<?> parent) {
                                             }
                                         });
            mAreaEdit.setText(String.valueOf(mInvestigation.getArea()));
            mNumberEdit.setText(mInvestigation.getNumber());
            mRateOfInvestigationEdit.setText(String.valueOf(mInvestigation.getRateOfDamage()));
            mInvestigatorsEdit.setText(mInvestigation.getInvestigators());

            TextView dateText = (TextView) findViewById(R.id.date_id);
            // 現在の時刻を取得
            Date date = mInvestigation.getDate();
            // 表示形式を設定
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy'年'MM'月'dd'日'　hh'時'mm'分'ss'秒'");
            dateText.setText(sdf.format(date));


        }


    }

    private void addInvestigation() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        if (mInvestigation == null) {
            //新規作成の場合
            mInvestigation = new Investigation();

            RealmResults<Investigation> investigationRealmResults = realm.where(Investigation.class).findAll();

            int identifier;
            if (investigationRealmResults.max("id") != null) {
                identifier = investigationRealmResults.max("id").intValue() + 1;
            } else {
                identifier = 0;

            }
            mInvestigation.setId(identifier);

            int rinpan = Integer.parseInt(mRinpanEdit.getText().toString());
            int syohan =Integer.parseInt(mSyohanEdit.getText().toString());
            String tree_species = (String) sp.getSelectedItem();
            double area = Double.parseDouble(mAreaEdit.getText().toString());
            int number = Integer.parseInt(mNumberEdit.getText().toString());
            int rateOfInvestigation = Integer.parseInt(mRateOfInvestigationEdit.getText().toString());
            String investigators = mInvestigatorsEdit.getText().toString();

            mInvestigation.setDate(new Date());
            mInvestigation.setRinpan(rinpan);
            mInvestigation.setSyohan(syohan);
            mInvestigation.setTree_species(tree_species);
            mInvestigation.setArea(area);
            mInvestigation.setNumber(number);
            mInvestigation.setRateOfInvestigation(rateOfInvestigation);
            mInvestigation.setInvestigators(investigators);

            realm.copyToRealmOrUpdate(mInvestigation);
            realm.commitTransaction();

            realm.close();
        }
    }

}
