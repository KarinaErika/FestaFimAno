package com.fimano.festa.festafimano.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.fimano.festa.festafimano.R;
import com.fimano.festa.festafimano.constants.FimDeAnoConstants;
import com.fimano.festa.festafimano.util.SecurityPreferences;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //getSupportActionBar(): pega a action bar
        getSupportActionBar().setDisplayShowTitleEnabled(false); //deixa de exibir o texto
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Define o que deve mostrar
        getSupportActionBar().setIcon(R.mipmap.ic_launcher); //Seta a logo na action bar


        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.checkParticipate = (CheckBox) findViewById(R.id.check_participate);
        this.mViewHolder.checkParticipate.setOnClickListener(this);

        this.loadDataActivity();
    }

    private void loadDataActivity() {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            String presence = extra.getString(FimDeAnoConstants.PRESENCE);

            if (presence.equals(FimDeAnoConstants.CONFIRMED_WILL_GO)) {
                this.mViewHolder.checkParticipate.setChecked(true);
            } else {
                this.mViewHolder.checkParticipate.setChecked(false);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.check_participate) {
            if (this.mViewHolder.checkParticipate.isChecked()) {
                this.mSecurityPreferences.storeString(FimDeAnoConstants.PRESENCE, FimDeAnoConstants.CONFIRMED_WILL_GO);
            } else {
                this.mSecurityPreferences.storeString(FimDeAnoConstants.PRESENCE, FimDeAnoConstants.CONFIRMED_WONT_GO);

            }
        }
    }

    private static class ViewHolder {
        CheckBox checkParticipate;
    }
}
