package com.example.gps_app.repositories;

import android.content.Context;
import android.location.Location;

import androidx.lifecycle.MutableLiveData;

import com.example.gps_app.services.PositionDBServices;
import com.example.gps_app.services.PositionServices;

import java.util.ArrayList;
import java.util.List;

public class PosicaoRepository {

    private static PosicaoRepository instance;
    private MutableLiveData<List<Location>> dados;
    private Context context;

    private PosicaoRepository(Context context){
        this.context = context;
        dados = new MutableLiveData<>();
        dados.setValue(new ArrayList<>());
    }

    public static PosicaoRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PosicaoRepository(context);
        }
        return instance;
    }

    public static PosicaoRepository getInstance() {
        return instance;
    }

    public MutableLiveData<List<Location>> getPosicoes() {
        return dados;
    }

    public void incluir(Location location) {
        PositionServices.getInstance(context).gravar(location);
        dados.getValue().add(location);
        dados.setValue(dados.getValue());
    }

    public List<PositionDBServices.Localizacao> getUltimasPosicoesDosUsuarios(){
        return PositionServices.getInstance(context).getUltimasLoc();
    }

    public Context getContext() {
        return this.context;
    }
}
