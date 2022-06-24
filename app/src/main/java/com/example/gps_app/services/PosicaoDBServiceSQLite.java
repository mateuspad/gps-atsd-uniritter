package com.example.gps_app.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.gps_app.sqlite.DBHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.GeoPoint;
import com.google.type.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PosicaoDBServiceSQLite implements PositionDBServices {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DBHelper dbHelper;

    public PosicaoDBServiceSQLite(Context context) {
        dbHelper = new DBHelper(context);
    }

    @Override
    public void salvar(Location location) {
        String dbName = "localizations";
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user", mAuth.getUid());
        values.put("lat", location.getLatitude());
        values.put("lng", location.getLongitude());
        values.put("time", location.getTime());
        values.put("speed", location.getSpeed());
        values.put("sent", 0);
        writableDatabase.insert(dbName, null, values);
    }

    @Override
    public List<Localizacao> getAllLocalizacao() {
        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM localizations";
        Cursor c = readableDatabase.rawQuery(selectQuery, null);

        List<Localizacao> listLoc = new ArrayList<>();

        boolean sent = false;
        if(c.getColumnIndex("sent") == 1){
            sent = true;
        }

        while(c.moveToNext()){
            listLoc.add(new Localizacao(c.getLong(0), c.getString(1), c.getDouble(2), c.getDouble(3),
                    c.getLong(4), c.getDouble(5), sent));
        }
        return listLoc;
    }

    @Override
    public List<Localizacao> getAllLocalizacaoData(long inicio, long fim) {
        return null;
    }

    @Override
    public List<Localizacao> getAllLocalizacaoNaoEnviadas() {
        List<Localizacao> listLoc = getAllLocalizacao();
        List<Localizacao> list = new ArrayList<>();
        for(Localizacao l: listLoc){
            if(!l.isEnviado()){
                list.add(l);
            }
        }
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Localizacao> getUltimaPosUsuarios(){
        List<Localizacao> list = getAllLocalizacao();
        List<Localizacao> users = new ArrayList<>();
        list.sort(Comparator.comparing(Localizacao::getData));
        Collections.reverse(list);

        if(list.size() > 0){
            for(Localizacao loc: list){
                if(!users.contains(loc)){
                    users.add(loc);
                }
            }
        }

        return users;
    }
}

