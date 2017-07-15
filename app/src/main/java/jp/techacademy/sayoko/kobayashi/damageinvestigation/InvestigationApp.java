package jp.techacademy.sayoko.kobayashi.damageinvestigation;

/**
 * Created by kobayashisayoko on 2017/07/05.
 */
import android.app.Application;
import io.realm.Realm;

public class InvestigationApp extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
    }
}
