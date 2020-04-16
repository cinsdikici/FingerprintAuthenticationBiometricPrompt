package com.smality.fingerprintauthenticationbiometricprompt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import android.os.Bundle;
import java.util.concurrent.*;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Executor newExecutor = Executors.newSingleThreadExecutor();
        //Parmağı sensöre dokundurduğunuz anda, telefonda kayıtlı iz ile
        //karsılastırma için BiometricPrompt sınıfının içindeki işlem durumu metodları oluşturulur
        final BiometricPrompt myBiometricPrompt = new BiometricPrompt(this, newExecutor, new BiometricPrompt.AuthenticationCallback() {

            //Bu metodda sensöre dokundurduğunuzda, bir hatayla karsılaşıp karşılaşmadığınızın kontrolunu yaptık
            //Örnek hatalar; cihazın dokunmatik sensörü kir kaplıdır, kullanıcı bu cihaza herhangi bir parmak izi kaydetmemiştir veya tam biyometrik tarama yapmak için yeterli bellek yoktur
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                } else {
                    Log.d("MainActivity", "bir hata oluştu");
                }
            }
            //Bu metod, parmak izi cihazda kayıtlı parmak izlerinden biriyle başarılı bir şekilde eşleştiğinde çağrılır.
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Log.d("MainActivity", "Parmak izi başarıyla tanındı");
            }
            //Bu metodda parmak izi tanıma işlemi başarılı olmadığında, yapılacak işlemler yazılmalı
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.d("MainActivity", "Parmak izi tanınmadı");
            }

        });
        //kimlik doğrulama iletişim kutusunda görünmesi gereken metni tanımlamanız ve kimlik doğrulamasını iptal etmesini sağlayan metoda isim atama
        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Başlık yazılacak alan")
                .setSubtitle("Altbaşlık yazılacak alan")
                .setDescription("Açıklama yazılacak alan")
                .setNegativeButtonText("İptal")
                .build();

        findViewById(R.id.launchAuthentication).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBiometricPrompt.authenticate(promptInfo);
            }
        });

    }
}
