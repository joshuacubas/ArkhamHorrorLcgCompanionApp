package com.example.arkhamhorrorlcgcompanionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    SoundPool soundPool;
    int pouchSound,errorSound,terrorSound,spritefulSound;
    ImageView token1, token2, token3, pouchBag;
    String k, source1, source2;
    String defaultTokensString;
    String tokensStringFromPreferences;
    TextView doomQty,cluesQty;
    SharedPreferences spS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token1 = findViewById(R.id.token1);
        token2 = findViewById(R.id.token2);
        token3 = findViewById(R.id.token3);
        source1 = "star.png";
        source2 = "star.png";
        doomQty = findViewById(R.id.doomQty);
        cluesQty = findViewById(R.id.cluesQty);
        pouchBag = findViewById(R.id.pouch);

        AudioAttributes audioAttributes = new AudioAttributes.Builder().build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(6).setAudioAttributes(audioAttributes).build();
        pouchSound = soundPool.load(this, R.raw.dice_noise, 1);
        errorSound = soundPool.load(this, R.raw.error, 1);
        terrorSound = soundPool.load(this, R.raw.terror, 1);
        spritefulSound = soundPool.load(this, R.raw.spriteful, 1);
        defaultTokensString = "auto_fail_token.png,grave_stone_token.png,cultist_token.png,elder_sign_token.png,elder_thing_token.png,plus_one_token.png,zero_token.png,minus_one_token.png,minus_two_token.png,minus_three_token.png";

        //Shared Preferences Get from Settings, make a default string
        spS = getSharedPreferences("settingsPreferences", Context.MODE_PRIVATE);
        // Main Concept: return shared prefs from settings, so it "remembers" the previously selected tokens list
        //              this doesnt manipulate, just sends it back to remember the token list
            //1.create map for default tokens
            //2.create str variable , set equal to above map converted to string
            //3.compare with stringified map from getprefs(settinssActivity)
                    //if not equal, that means there are user defined settings, set default mapstr equal to the one from settings
                    // if same do nothing
            //4. return default map str of whatever the current value is, back to settings with shared prefs

        Map<String,Integer> defaultTokensMap = new HashMap<String,Integer>(){{
            put("auto_fail_token.png",1);
            put("cultist_token.png",1);
            put("skull_token.png",1);
            put("elder_thing_token.png",1);
            put("grave_stone_token.png",1);
            put("elder_sign_token.png",1);
            put("plus_one_token.png",1);
            put("zero_token.png",1);
            put("minus_one_token.png",1);
            put("minus_two_token.png",1);
            put("minus_three_token.png",1);
            put("minus_four_token.png",0);
            put("minus_five_token.png",0);
            put("minus_six_token.png",0);
            put("minus_seven_token.png",0);
            put("minus_eight_token.png",0);
        }};
        String defaultMapString = defaultTokensMap.toString();
        SharedPreferences.Editor editorM = spS.edit();

        editorM.putString("tokensStrMapFromActivityMain", defaultMapString);
        editorM.apply();
    }

    public void GoToSettings(View view) {
        Intent i = new Intent(MainActivity.this, Settings.class);
        startActivity(i);
    }

    public void PouchClicked(View view) {
        tokensStringFromPreferences = spS.getString("tokensInBagString",defaultTokensString);

        //If user set all items to zero, do not proceed further, toast zero tokens selected in bag settings
        if(tokensStringFromPreferences == ""){
            Toast.makeText(MainActivity.this, "0 Tokens selected in settings", Toast.LENGTH_SHORT).show();
            return;
        }

        //Play Sound effect -----------------------------------------------------------
        soundPool.autoPause(); // pauses whatever was playing, so you can hear the next sound ..
        soundPool.play(pouchSound, 1, 1, 0, 0, 1);
        //        soundPool.play(Parameters explained ....) :
        //        soundID	int: a soundID returned by the load() function
        //        leftVolume	float: left volume value (range = 0.0 to 1.0)
        //        rightVolume	float: right volume value (range = 0.0 to 1.0)
        //        priority	int: stream priority (0 = lowest priority)
        //        loop	int: loop mode (0 = no loop, -1 = loop forever)
        //        rate	float: playback rate (1.0 = normal playback, range 0.5 to 2.0)

        //Shaking Animation
        Animation pouchBagAnimation = AnimationUtils.loadAnimation(this, R.anim.shaking_animation);
        pouchBag.startAnimation(pouchBagAnimation);

        //Shared Preferences Get from Settings, make a default string


        //build and shuffle tokenbag list, and select random token
        String[] tokenStringsArray = tokensStringFromPreferences.split("[,]", 0);
        List<String> tokensList = Arrays.asList(tokenStringsArray);
        Collections.shuffle(tokensList);
        Collections.shuffle(tokensList);
        Random rand = new Random();
        k = tokensList.get(rand.nextInt(tokensList.size()));

        CountDownTimer countDownTimer = new CountDownTimer(3500,3500) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                //change Token3 to token 2's current image before new token is drawn
                switch (source2){
                    case "auto_fail_token.png":
                        token3.setImageResource(R.drawable.auto_fail_token);
                        break;
                    case "cultist_token.png":
                        token3.setImageResource(R.drawable.cultist_token);
                        break;
                    case "skull_token.png":
                        token3.setImageResource(R.drawable.skull_token);
                        break;
                    case "elder_sign_token.png":
                        token3.setImageResource(R.drawable.elder_sign_token);
                        break;
                    case "elder_thing_token.png":
                        token3.setImageResource(R.drawable.elder_thing_token);
                        break;
                    case "grave_stone_token.png":
                        token3.setImageResource(R.drawable.grave_stone_token);
                        break;
                    case "minus_eight_token.png":
                        token3.setImageResource(R.drawable.minus_eight_token);
                        break;
                    case "minus_seven_token.png":
                        token3.setImageResource(R.drawable.minus_seven_token);
                        break;
                    case "minus_six_token.png":
                        token3.setImageResource(R.drawable.minus_six_token);
                        break;
                    case "minus_five_token.png":
                        token3.setImageResource(R.drawable.minus_five_token);
                        break;
                    case "minus_four_token.png":
                        token3.setImageResource(R.drawable.minus_four_token);
                        break;
                    case "minus_three_token.png":
                        token3.setImageResource(R.drawable.minus_three_token);
                        break;
                    case "minus_two_token.png":
                        token3.setImageResource(R.drawable.minus_two_token);
                        break;
                    case "minus_one_token.png":
                        token3.setImageResource(R.drawable.minus_one_token);
                        break;
                    case "zero_token.png":
                        token3.setImageResource(R.drawable.zero_token);
                        break;
                    case "plus_one_token.png":
                        token3.setImageResource(R.drawable.plus_one_token);
                        break;

                    default:
                        token3.setImageResource(R.drawable.star);
                }
                // change value of variable so the next roll knows what the  2->3
                source2 = source1;
                //change Token2 to token 1's current image before new token is drawn
                switch (source1){
                    case "auto_fail_token.png":
                        token2.setImageResource(R.drawable.auto_fail_token);
                        break;
                    case "cultist_token.png":
                        token2.setImageResource(R.drawable.cultist_token);
                        break;
                    case "skull_token.png":
                        token2.setImageResource(R.drawable.skull_token);
                        break;
                    case "elder_sign_token.png":
                        token2.setImageResource(R.drawable.elder_sign_token);
                        break;
                    case "elder_thing_token.png":
                        token2.setImageResource(R.drawable.elder_thing_token);
                        break;
                    case "grave_stone_token.png":
                        token2.setImageResource(R.drawable.grave_stone_token);
                        break;
                    case "minus_eight_token.png":
                        token2.setImageResource(R.drawable.minus_eight_token);
                        break;
                    case "minus_seven_token.png":
                        token2.setImageResource(R.drawable.minus_seven_token);
                        break;
                    case "minus_six_token.png":
                        token2.setImageResource(R.drawable.minus_six_token);
                        break;
                    case "minus_five_token.png":
                        token2.setImageResource(R.drawable.minus_five_token);
                        break;
                    case "minus_four_token.png":
                        token2.setImageResource(R.drawable.minus_four_token);
                        break;
                    case "minus_three_token.png":
                        token2.setImageResource(R.drawable.minus_three_token);
                        break;
                    case "minus_two_token.png":
                        token2.setImageResource(R.drawable.minus_two_token);
                        break;
                    case "minus_one_token.png":
                        token2.setImageResource(R.drawable.minus_one_token);
                        break;
                    case "zero_token.png":
                        token2.setImageResource(R.drawable.zero_token);
                        break;
                    case "plus_one_token.png":
                        token2.setImageResource(R.drawable.plus_one_token);
                        break;
                    default:
                        token2.setImageResource(R.drawable.star);
                }
                // Change Token 1 to the new random token , k
                switch (k){
                    case "auto_fail_token.png":
                        token1.setImageResource(R.drawable.auto_fail_token);
                        break;
                    case "cultist_token.png":
                        token1.setImageResource(R.drawable.cultist_token);
                        break;
                    case "skull_token.png":
                        token1.setImageResource(R.drawable.skull_token);
                        break;
                    case "elder_sign_token.png":
                        token1.setImageResource(R.drawable.elder_sign_token);
                        break;
                    case "elder_thing_token.png":
                        token1.setImageResource(R.drawable.elder_thing_token);
                        break;
                    case "grave_stone_token.png":
                        token1.setImageResource(R.drawable.grave_stone_token);
                        break;
                    case "minus_eight_token.png":
                        token1.setImageResource(R.drawable.minus_eight_token);
                        break;
                    case "minus_seven_token.png":
                        token1.setImageResource(R.drawable.minus_seven_token);
                        break;
                    case "minus_six_token.png":
                        token1.setImageResource(R.drawable.minus_six_token);
                        break;
                    case "minus_five_token.png":
                        token1.setImageResource(R.drawable.minus_five_token);
                        break;
                    case "minus_four_token.png":
                        token1.setImageResource(R.drawable.minus_four_token);
                        break;
                    case "minus_three_token.png":
                        token1.setImageResource(R.drawable.minus_three_token);
                        break;
                    case "minus_two_token.png":
                        token1.setImageResource(R.drawable.minus_two_token);
                        break;
                    case "minus_one_token.png":
                        token1.setImageResource(R.drawable.minus_one_token);
                        break;
                    case "zero_token.png":
                        token1.setImageResource(R.drawable.zero_token);
                        break;
                    case "plus_one_token.png":
                        token1.setImageResource(R.drawable.plus_one_token);
                        break;
                    default:
                        token1.setImageResource(R.drawable.minus);
                }
                // So the next draw knows to set token2 imageSrc to source1 value
                source1 = k;
            }
        }.start();
    }

    public void playErrorNoise(){
        //Plays error Sound effect -----------------------------------------------------------
        soundPool.autoPause(); // pauses whatever was playing, so you can hear the next sound ..
        soundPool.play(errorSound, 1, 1, 0, 0, 1);
    }

    public void minusClues(View view) {
        int qty = Integer.parseInt(""+cluesQty.getText());
        if (qty > 0){
            qty--;
            String str = Integer.toString(qty);
            cluesQty.setText(str);
        }
        else{
            playErrorNoise();
        }
    }

    public void addClues(View view) {
        String str = Integer.toString(Integer.parseInt("" + cluesQty.getText()) + 1);
        cluesQty.setText(str);

        //Plays Uplifting Spriteful Sound effect -----------------------------------------------------------
        soundPool.autoPause(); // pauses whatever was playing, so you can hear the next sound ..
        soundPool.play(spritefulSound, 1, 1, 0, 0, 1);
    }

    public void minusDoom(View view) {
        int qty = Integer.parseInt("" + doomQty.getText());
        if (qty > 0){
            qty--;
            String str = Integer.toString(qty);
            doomQty.setText(str);
        }
        else{
            playErrorNoise();
        }
    }

    public void addDoom(View view) {
        String str = Integer.toString(Integer.parseInt("" + doomQty.getText()) + 1);
        doomQty.setText(str);

        //Plays Terror Sound effect -----------------------------------------------------------
        soundPool.autoPause(); // pauses whatever was playing, so you can hear the next sound ..
        soundPool.play(terrorSound, 1, 1, 0, 0, 1);
    }

}