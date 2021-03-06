package com.kkkcut.www.myapplicationkukai.entity;

import com.kkkcut.www.myapplicationkukai.R;

/**
 * 品牌类
 */

public class Mfg {
    private int id;
    private  int _Type;
    private  String name;
    private boolean _is_visible;
    private  String _desc;
    private boolean _is_automobile;
    private boolean _is_motorcycle;
    private boolean _is_dimple;
    private boolean _is_tubular;
    private boolean _is_standard;
    private boolean _is_kor;
    private boolean _is_chs;
    private boolean _is_sec;
    private String sortLetters;  //显示数据拼音的首字母
    private int img= R.drawable.vehicle1;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Mfg(){

    }
    public Mfg(int _id, int _Type, String _Name, boolean _is_visible, String _desc, boolean _is_automobile
    , boolean _is_motorcycle, boolean _is_dimple, boolean _is_tubular, boolean _is_standard, boolean _is_kor
    , boolean _is_chs, boolean _is_sec){
        this.id =_id;
        this._Type=_Type;
        this.name =_Name;
        this._is_visible=_is_visible;
        this._desc=_desc;
        this._is_automobile=_is_automobile;
        this._is_motorcycle=_is_motorcycle;
        this._is_dimple=_is_dimple;
        this._is_tubular=_is_tubular;
        this._is_standard=_is_standard;
        this._is_standard=_is_kor;
        this._is_standard=_is_chs;
        this._is_standard=_is_sec;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean is_is_visible() {
        return _is_visible;
    }

    public boolean is_is_automobile() {
        return _is_automobile;
    }

    public boolean is_is_motorcycle() {
        return _is_motorcycle;
    }

    public boolean is_is_dimple() {
        return _is_dimple;
    }

    public boolean is_is_tubular() {
        return _is_tubular;
    }

    public boolean is_is_standard() {
        return _is_standard;
    }

    public boolean is_is_kor() {
        return _is_kor;
    }

    public boolean is_is_chs() {
        return _is_chs;
    }

    public boolean is_is_sec() {
        return _is_sec;
    }

    public int get_Type() {
        return id;
    }

    public void set_Type(int Type) {
        this.id = Type;
    }



    public boolean get_is_visible() {
        return _is_visible;
    }
    public void set_is_visible(boolean is_visible) {
        this._is_visible = is_visible;
    }

    public String get_desc() {
        return _desc;
    }
    public void set_desc(String desc) {
        this._desc = desc;
    }

    public boolean get_is_automobile() {
        return _is_automobile;
    }
    public void set_is_automobile(boolean is_automobile) {
        this._is_automobile = is_automobile;
    }

    public boolean get_is_motorcycle() {
        return _is_motorcycle;
    }
    public void set_is_motorcycle(boolean is_motorcycle) {
        this._is_motorcycle = is_motorcycle;
    }

    public boolean get_is_dimple() {
        return _is_dimple;
    }
    public void set_is_dimple(boolean is_dimple) {
        this._is_dimple = is_dimple;
    }

    public boolean get_is_tubular() {
        return _is_tubular;
    }
    public void set_is_tubular(boolean is_tubular) {
        this._is_tubular = is_tubular;
    }

    public boolean get_is_standard() {
        return _is_standard;
    }
    public void set_is_standard(boolean is_standard) {
        this._is_standard = is_standard;
    }

    public boolean get_is_kor() {
        return _is_automobile;
    }
    public void set_is_kor(boolean is_kor) {
        this._is_kor = is_kor;
    }

    public boolean get_is_chs() {
        return _is_chs;
    }
    public void set_is_chs(boolean is_chs) {
        this._is_chs = is_chs;
    }

    public boolean get_is_sec() {
        return _is_sec;
    }
    public void set_is_sec(boolean is_sec) {
        this._is_sec= is_sec;
    }
    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }


}
