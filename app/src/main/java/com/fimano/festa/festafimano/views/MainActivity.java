package com.fimano.festa.festafimano.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fimano.festa.festafimano.R;
import com.fimano.festa.festafimano.constants.FimDeAnoConstants;
import com.fimano.festa.festafimano.util.SecurityPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportActionBar(): pega a action bar
        getSupportActionBar().setDisplayShowTitleEnabled(false); //deixa de exibir o texto
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Define o que deve mostrar
        getSupportActionBar().setIcon(R.mipmap.ic_launcher); //Seta a logo na action bar

        this.mViewHolder.textDayLeft = findViewById(R.id.text_day_left);
        this.mViewHolder.textToday = findViewById(R.id.text_today);
        this.mViewHolder.buttonConfirm = findViewById(R.id.button_confirm);

        this.mViewHolder.buttonConfirm.setOnClickListener(this);

        this.mSecurityPreferences = new SecurityPreferences(this);

        //Faz a formatação do dia de hoje
        this.mViewHolder.textToday.setText(SIMPLE_DATE_FORMAT.format(Calendar.getInstance().getTime()));

        String dayLeft = String.format("%s %s", String.valueOf(this.getDaysLeftToEndOfYear()), getString(R.string.dias));

        this.mViewHolder.textDayLeft.setText(dayLeft);

    }

    //callback(): função executada quando outra função termina a sua execução
    //Ciclo de vida de uma activity

    /**
     * Esse método faz com que a activity seja visível para o usuário, enquanto o aplicativo
     * prepara para a atividade entrar em funcionamento e se tornar interativa.
     * O método onStart() é completado rapidamente. Assim que esse callback é terminado, a
     * activity entra no modo Resumido.
     */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Quando a activity entrar no estado Resumido, ela entra em funcionamento e está pronta
     * para interação com o usuário. Assim que o aplicativo entra nesse estado, permanecerá assim até
     * que algo aconteça e tire o foco do aplicativo.
     */
    @Override
    protected void onResume() {
        super.onResume();
        this.verifyPresence();
    }

    /**
     * Primeira notificação quando app sai do estado onResume().
     * Esse método é chamado como o primeiro indicativo de que o
     * usuário está saindo da activity
     * Não é recomendado fazer chamadas muito pesadas como por exemplo: salvar dados no banco de dados ou
     * fazer uma chamada a api.
     * Esse método deve ser usado para pausar animações e músicas que não devem continuar
     * quando sua tela está pausada.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Quando sua activity não está mais visível para o usuário, porém ainda permanece alocada
     * na memória, ela entra no modo Parado e o sistema invoca o callback onStop()
     * Caso se queira fazer processamentos mais pesados como salvar dados no banco de dados ou
     * fazer uma chamada a api, é recomendado fazer neste método.
     * É nesse método que todos os recursos alocados na criação devem ser descartados.
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Método chamado quando a activity não está mais alocada na memória, ela deixa de existir
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void verifyPresence() {
        String presence = this.mSecurityPreferences.getStoredString(FimDeAnoConstants.PRESENCE);

        if (presence.equals("")) {
            this.mViewHolder.buttonConfirm.setText(R.string.nao_confirmado);
        } else if (presence.equals(FimDeAnoConstants.CONFIRMED_WILL_GO)) {
            this.mViewHolder.buttonConfirm.setText(R.string.sim);
        } else {
            this.mViewHolder.buttonConfirm.setText(R.string.nao);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_confirm) {
            String presence = this.mSecurityPreferences.getStoredString(FimDeAnoConstants.PRESENCE);

            Intent intent = new Intent(this, DetailsActivity.class);

            intent.putExtra(FimDeAnoConstants.PRESENCE, presence);
            startActivity(intent);
        }
    }

    /**
     * Método para calcular quantos dias faltam para o fim do ano
     */
    private  int getDaysLeftToEndOfYear(){
        Calendar calendarToday = Calendar.getInstance();
        int today  = calendarToday.get(Calendar.DAY_OF_YEAR); //Dia de hoje

        Calendar calendarLastDay = Calendar.getInstance();
        int deceber31 = calendarLastDay.getActualMaximum(Calendar.DAY_OF_YEAR); //último dia do ano

        return deceber31 - today;
    }

    private static class ViewHolder {
        TextView textToday;
        TextView textDayLeft;
        Button buttonConfirm;
    }
}
