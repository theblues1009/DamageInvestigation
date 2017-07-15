package jp.techacademy.sayoko.kobayashi.damageinvestigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class BaseInputActivity extends AppCompatActivity {
    private EditText mRinpanEdit, mSyohanEdit, mAreaEdit, mNumberEdit, mRateOfInvestigationEdit, mInvestigatorsEdit;
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
            mAreaEdit.setText(mInvestigation.getArea());
            mNumberEdit.setText(mInvestigation.getNumber());
            mRateOfInvestigationEdit.setText(mInvestigation.getRateOfDamage());
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

            String rinpan = mRinpanEdit.getText().toString();
            String syohan = mSyohanEdit.getText().toString();
            String area = mAreaEdit.getText().toString();
            String number = mNumberEdit.getText().toString();
            String rateOfInvestigation = mRateOfInvestigationEdit.getText().toString();
            String investigators = mInvestigatorsEdit.getText().toString();

            mInvestigation.setDate(new Date());
            mInvestigation.setRinpan(rinpan);
            mInvestigation.setSyohan(syohan);
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
