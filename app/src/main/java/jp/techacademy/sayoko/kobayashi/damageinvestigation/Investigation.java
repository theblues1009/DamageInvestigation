package jp.techacademy.sayoko.kobayashi.damageinvestigation;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Investigation extends RealmObject implements Serializable{
    private int rinpan;
    private int syohan;
    private String tree_species;
    private double area;
    private int number;
    private int rateOfInvestigation;
    private double rateOfDamage;
    private Date date;
    private String investigators;

    private int ryoko;
    private int kesson;
    private int koson;
    private int dogari;
    private int nonezumi_kare;
    private int nonezumi_seizon;
    private int yukigusare_kare;
    private int yukigusare_seizon;
    private int total;
    private double damege_rate;

    //idをプライマリーキーとして設定
    @PrimaryKey
    private int id;

    public int getRinpan(){
        return rinpan;
    }

    public void setRinpan(int rinpan){
        this.rinpan = rinpan;
    }

    public int getSyohan(){
        return syohan;
    }

    public void setSyohan(int syohan){
        this.syohan = syohan;
    }

    public String getTree_species(){return tree_species;}

    public void setTree_species(String tree_species){this.tree_species = tree_species;}

    public double getArea(){
        return area;
    }

    public void setArea(double area){
        this.area = area;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public int getRateOfInvestigation(){
        return rateOfInvestigation;
    }

    public void setRateOfInvestigation(int rateOfInvestigation){
        this.rateOfInvestigation = rateOfInvestigation;}

    public double getRateOfDamage(){
        return rateOfDamage;
    }

    public void setRateOfDamage(double rateOfDamage){
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



    public int getRyoko(){
        return ryoko;
    }
    public void setRyoko(int ryoko){
        this.ryoko = ryoko;
    }
    public int getKesson(){
        return kesson;
    }
    public void setKesson(int kesson){
        this.kesson = kesson;
    }

    public int getKoson(){
        return koson;
    }
    public void setKoson(int koson){
        this.koson = koson;
    }

    public int getDogari(){
        return dogari;
    }
    public void setDogari(int dogari) {
        this.dogari = dogari;

    }

    public int getNonezumi_kare(){
        return nonezumi_kare;
    }
    public void setNonezumi_kare(int nonezumi_kare) {
        this.nonezumi_kare = nonezumi_kare;

    }

    public int getNonezumi_seizon(){
        return nonezumi_seizon;
    }
    public void setNonezumi_seizon(int nonezumi_seizon) {
        this.nonezumi_seizon = nonezumi_seizon;

    }

    public int getYukigusare_kare(){
        return yukigusare_kare;
    }
    public void setYukigusare_kare(int yukigusare_kare) {
        this.yukigusare_kare = yukigusare_kare;

    }

    public int getYukigusare_seizon(){
        return yukigusare_seizon;
    }
    public void setYukigusare_seizon(int yukigusare_seizon) {
        this.yukigusare_seizon = yukigusare_seizon;

    }

    public int getTotal(){
        return total;
    }
    public void setTotal(int total) {
        this.total = total;

    }

    public double getDamege_rate(){
        return damege_rate;
    }
    public void setDamege_rate(double damege_rate) {
        this.damege_rate = damege_rate;

    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}

