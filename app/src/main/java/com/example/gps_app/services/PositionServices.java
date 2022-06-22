package com.example.gps_app.services;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import java.util.List;

public class PositionServices {

    private static PositionServices instance;
    private PositionDBServices dbLocal;
    //private PositionDBServices dbRemoto;
    private Context context;

    private PositionServices(Context context) {
        //dbRemoto = new PosicaoDBServiceFirebase();
        dbLocal = new PosicaoDBServiceSQLite(context);
        this.context = context;
    }

    public static PositionServices getInstance(Context context) {
        if(instance == null){
            instance = new PositionServices(context);
        }
        return instance;
    }

    public void gravar(Location localizacao) {
        //if (dbRemoto != null) {
        //    dbRemoto.salvar(localizacao);
        //}

        if (dbLocal != null) {
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //    dbLocal.getAllLocalizacao().forEach(System.out::println);
            //}
            dbLocal.salvar(localizacao);
        }
    }

}
