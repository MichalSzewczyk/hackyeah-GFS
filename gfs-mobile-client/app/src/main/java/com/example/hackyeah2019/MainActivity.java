package com.example.hackyeah2019;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hackyeah2019.model.RespondJson;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    //Not proud from this line, but Cloud Dev... 2019.09.14 did first time Android from 7 years... they changed everything xD
    public static List<String> stringList = new ArrayList<>();

    //Point it to running server with backend part
    private String resolutionHost = "10.250.166.86:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //NO GPS SIGNAL...
//        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Log.e("MainActivity", "no permissions");
//            return;
//        }
        //No GPS SIGNAL...
        // Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (result != null) {
            try {
                if (result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    String valueScanned = result.getContents().trim();

                    //Hardcoded location as GPS signal not working here ;(
                    RespondJson resultCost = doHttpRequest(52.110559, 20.829819, Long.valueOf(valueScanned));
                    String messageForUser;
                    messageForUser = resultCost.getProductName().length() >= 27
                            ? "Koszt środowiskowy " + resultCost.getValue() + " dla produktu " + resultCost.getProductName().substring(0, 26) + "..." :
                            "Koszt środowiskowy " + resultCost.getValue() + " dla produktu " + resultCost.getProductName();
                    stringList.add(messageForUser);

                    Toast toast = Toast.makeText(this, messageForUser, Toast.LENGTH_LONG);
                    View view = toast.getView();

                    //To change the Background of Toast
                    view.setBackgroundColor(Color.TRANSPARENT);
                    TextView text = view.findViewById(android.R.id.message);

                    if (resultCost.getValue() <= 34) {
                        view.setBackgroundColor(Color.GREEN);
                    } else if (resultCost.getValue() <= 67) {
                        view.setBackgroundColor(Color.YELLOW);
                    } else {
                        view.setBackgroundColor(Color.RED);
                    }
                    text.setTextColor(Color.BLACK);
                    toast.show();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
                Toast.makeText(this, "Fail to resolve cost", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public RespondJson doHttpRequest(double lat, double lon, long barcode) {
        String url = new StringBuilder("http://").append(resolutionHost).append("/footprint/calculate?lat=").append(lat).append("&lon=").append(lon).append("&barcode=").append(barcode).toString();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String result = restTemplate.getForObject(url, String.class);
        Gson gson = new Gson();

        return gson.fromJson(result, RespondJson.class);

    }
}
