package com.example.gps_app.services;

import android.location.Location;

import com.google.firebase.firestore.GeoPoint;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public interface PositionDBServices {

    void salvar(Location location);
    List<Localizacao> getAllLocalizacao();
    List<Localizacao> getAllLocalizacaoData(long inicio, long fim);
    List<Localizacao> getAllLocalizacaoNaoEnviadas();
    List<Localizacao> getUltimaPosUsuarios();

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

        public GeoPoint getLocalizacao() {
            return localizacao;
        }

        public String getUser() {
            return user;
        }

        public Date getData() {
            return data;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Localizacao that = (Localizacao) o;
            return Objects.equals(user, that.user);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user);
        }
    }
}

