package com.fimano.festa.festafimano.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SecurityPreferences {
    private final SharedPreferences mSharedPreferences;

    public SecurityPreferences(Context context) {
        //Não pode instanciar, pois SharedPreferences é uma interface
        this.mSharedPreferences = context.getSharedPreferences("FimDeAno", context.MODE_PRIVATE);
        //context.getSharedPreferences: obter o shared preferences
        //FimDeAno: arquivo criado ao acessar app
        //MODE_PRIVATE significa que só esse app pode acessar essas informações
    }


    //Salva dados
    public void storeString(String key, String value) {
        this.mSharedPreferences.edit().putString(key, value).apply(); //Passa uma chave e um valor para salvar
    }

    //Recuperar valor salvo

    public String getStoredString(String key) {
        return this.mSharedPreferences.getString(key, ""); //Caso não exista o valor, retorna nulo
    }
}
