package com.example.gps_app.services;

import android.location.Location;

import com.google.firebase.firestore.GeoPoint;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface PositionDBServices {

    void salvar(Location location);
    List<Localizacao> getAllLocalizacao();
    List<Localizacao> getAllLocalizacaoData(long inicio, long fim);
    List<Localizacao> getAllLocalizacaoNaoEnviadas();

    class Localizacao {
        private long id;
        private GeoPoint localizacao;
        private Date data;
        private boolean enviado;

        public Localizacao(GeoPoint localizacao) {
            this.localizacao = localizacao;
            this.data = Calendar.getInstance().getTime();
            this.id = this.data.getTime();
            this.enviado = false;
        }
        public Localizacao(double lat, double lng, long time, boolean enviado) {
            this.localizacao = new GeoPoint(lat, lng);
            this.data = new Date(time);
            this.id = time;
            this.enviado = enviado;
        }
        public long getId() {
            return id;
        }

        public GeoPoint getLocalizacao() {
            return localizacao;
        }

        public Date getData() {
            return data;
        }

        public boolean isEnviado() {
            return enviado;
        }

        public void setEnviado(boolean enviado) {
            this.enviado = enviado;
        }
    }
}

