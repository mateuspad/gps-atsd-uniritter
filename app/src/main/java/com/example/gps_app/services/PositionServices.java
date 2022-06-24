package com.example.gps_app.services;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import java.util.List;

public class PositionServices {

    private static PositionServices instance;
    private PositionDBServices dbLocal;
    private Context context;

    private PositionServices(Context context) {
        dbLocal = new PosicaoDBServiceSQLite(context);
        this.context = context;
    }

    public static PositionServices getInstance(Context context) {
        if(instance == null){
            instance = new PositionServices(context);
        }
        return instance;
    }

    public List<PositionDBServices.Localizacao> getUltimasLoc(){
        return dbLocal.getUltimaPosUsuarios();
    }

    public void gravar(Location localizacao) {
          if (dbLocal != null) {
            dbLocal.salvar(localizacao);
        }
    }

}
