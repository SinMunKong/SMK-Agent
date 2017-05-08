package th.co.smk.smkagent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class ActivityLogin extends AppCompatActivity {
    uiController uiControl = new uiController();
    Context context = this;

    private void uiSetting() {
        //uiControl.hideActionBar(getWindow().getDecorView());
        if (Build.VERSION.SDK_INT <= 11){
            getActionBar().hide();
        }else {
            getSupportActionBar().hide();
        }

        Button button_Login = (Button) findViewById(R.id.bt_login);
        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SOAPAction();
            }
        });
    }

    private void testLogin() {
        EditText id_edittext = (EditText) findViewById(R.id.et_id);
        id_edittext.setText("AG1-0100000");
        EditText pass_edittext = (EditText) findViewById(R.id.et_pass);
        pass_edittext.setText("123");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uiSetting();
        testLogin();
    }

    private void requestPermission() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    private void SOAPAction() {
        requestPermission();
        String NAMESPACE = getResources().getString(R.string.NAMESPACE);
        String METHOD_NAME = getResources().getString(R.string.METHOD_GET_LOGIN_PASSWORD);
        String SOAP_ACTION = getResources().getString(R.string.SOAP_ACTION_GET_LOGIN_PASSWORD);
        String URLWSDL = getResources().getString(R.string.URLWSDL);

        SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

        EditText id_edittext = (EditText) findViewById(R.id.et_id);
        EditText pass_edittext = (EditText) findViewById(R.id.et_pass);
        id_edittext.setText(uiControl.RTrim(id_edittext.getText().toString()));

        boolean alertLogin = false;
        if (uiControl.checkEdittextIsNull(id_edittext,getResources().getString(R.string.et_loginId_error))){
            alertLogin = true;
        }
        if (uiControl.checkEdittextIsNull(pass_edittext,getResources().getString(R.string.et_loginPass_error))) {
            alertLogin = true;
        }
        if (alertLogin == true) {
            return;
        }

        Request.addProperty("pv_login", id_edittext.getText().toString());
        Request.addProperty("pv_passwd", pass_edittext.getText().toString());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(Request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URLWSDL);
        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (envelope.getResponse().toString().equals("false")){
                new AlertDialog.Builder(context)
                        .setTitle(R.string.alert_loginError_title)
                        .setMessage(R.string.alert_loginError_message)
                        .setNegativeButton(R.string.alert_buttonConfirm, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }else {
                Intent intent = new Intent(this, ActivityMain.class);
                intent.putExtra(getResources().getString(R.string.intent_parameter_id_user),id_edittext.getText());
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
