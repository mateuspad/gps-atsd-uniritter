package com.example.gps_app.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;

import com.example.gps_app.sqlite.DBHelper;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class PosicaoDBServiceSQLite implements PositionDBServices {

    DBHelper dbHelper;

    public PosicaoDBServiceSQLite(Context context) {
        dbHelper = new DBHelper(context);
    }

    @Override
    public void salvar(Location location) {
        String dbName = "localizations";
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("lat", location.getLatitude());
        values.put("lng", location.getLongitude());
        values.put("time", 0);
        values.put("sent", 0);
        Log.d("SQLite DBService", "gravou");
        Log.d("Tempo", "" + location.getTime());
        writableDatabase.insert(dbName, null, values);
    }

    @Override
    public List<Localizacao> getAllLocalizacao() {
        return null;
    }

    @Override
    public List<Localizacao> getAllLocalizacaoData(long inicio, long fim) {
        return null;
    }

    @Override
    public List<Localizacao> getAllLocalizacaoNaoEnviadas() {
        return null;
    }
}

