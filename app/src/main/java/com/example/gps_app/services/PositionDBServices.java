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
        private String user;
        private GeoPoint localizacao;
        private Date data;
        private double speed;
        private boolean enviado;

        public Localizacao(GeoPoint localizacao) {
            this.localizacao = localizacao;
            this.data = Calendar.getInstance().getTime();
            this.id = this.data.getTime();
            this.enviado = false;
        }

        public Localizacao(long id, String user, double lat, double lng, long time, double speed, boolean enviado) {
            this.id = id;
            this.user = user;
            this.localizacao = new GeoPoint(lat, lng);
            this.data = new Date(time);
            this.speed = speed;
            this.enviado = enviado;
        }

        public boolean isEnviado() {
            return enviado;
        }

        @Override
        public String toString() {
            return "Localizacao:\n" +
                    "id -> " + id +
                    "\nuser -> " + user +
                    "\nlocalizacao -> " + localizacao.getLatitude() + ", " + localizacao.getLongitude() +
                    "\ndata -> " + data +
                    "\nspeed -> " + speed +
                    "\nenviado -> " + enviado;
        }
    }
}

