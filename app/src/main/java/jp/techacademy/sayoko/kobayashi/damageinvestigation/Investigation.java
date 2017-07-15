package jp.techacademy.sayoko.kobayashi.damageinvestigation;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Investigation extends RealmObject implements Serializable{
    private String rinpan;
    private String syohan;
    private String area;
    private String number;
    private String rateOfInvestigation;
    private String rateOfDamage;
    private Date date;
    private String investigators;

    //idをプライマリーキーとして設定
    @PrimaryKey
    private int id;

    public String getRinpan(){
        return rinpan;
    }

    public void setRinpan(String rinpan){
        this.rinpan = rinpan;
    }

    public String getSyohan(){
        return syohan;
    }

    public void setSyohan(String syohan){
        this.syohan = syohan;
    }

    public String getArea(){
        return area;
    }

    public void setArea(String area){
        this.area = area;
    }

    public String getNumber(){
        return number;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public String getRateOfInvestigation(){
        return rateOfInvestigation;
    }

    public void setRateOfInvestigation(String rateOfInvestigation){
        this.rateOfInvestigation = rateOfInvestigation;}

    public String getRateOfDamage(){
        return rateOfDamage;
    }

    public void setRateOfDamage(String rateOfDamage){
        this.rateOfDamage = rateOfDamage;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public String getInvestigators(){
        return investigators;
    }

    public void setInvestigators(String investigators){
        this.investigators = investigators;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}

