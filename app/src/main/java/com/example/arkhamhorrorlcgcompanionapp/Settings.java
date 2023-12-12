package com.example.arkhamhorrorlcgcompanionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Settings extends AppCompatActivity {
    TextView auto_fail_tv;
    TextView cultist_tv;
    TextView skull_tv;
    TextView elder_thing_tv;
    TextView grave_stone_tv;
    TextView elder_sign_tv;
    TextView plus_one_tv;
    TextView zero_tv;
    TextView minus_one_tv;
    TextView minus_two_tv;
    TextView minus_three_tv;
    TextView minus_four_tv;
    TextView minus_five_tv;
    TextView minus_six_tv;
    TextView minus_seven_tv;
    TextView minus_eight_tv;
    SoundPool soundPool;
    int errorSound;
    String defaultTokenListString;
    String testListString;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        auto_fail_tv = findViewById(R.id.qty_auto_fail);
        cultist_tv = findViewById(R.id.qty_cultist);
        skull_tv = findViewById(R.id.qty_skull);
        elder_thing_tv = findViewById(R.id.qty_elder_thing);
        grave_stone_tv = findViewById(R.id.qty_grave_stone);
        elder_sign_tv = findViewById(R.id.qty_elder_sign);
        plus_one_tv = findViewById(R.id.qty_plus_one);
        zero_tv = findViewById(R.id.qty_zero);
        minus_one_tv = findViewById(R.id.qty_minus_one);
        minus_two_tv = findViewById(R.id.qty_minus_two);
        minus_three_tv = findViewById(R.id.qty_minus_three);
        minus_four_tv = findViewById(R.id.qty_minus_four);
        minus_five_tv = findViewById(R.id.qty_minus_five);
        minus_six_tv = findViewById(R.id.qty_minus_six);
        minus_seven_tv = findViewById(R.id.qty_minus_seven);
        minus_eight_tv = findViewById(R.id.qty_minus_eight);

        AudioAttributes audioAttributes = new AudioAttributes.Builder().build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(6).setAudioAttributes(audioAttributes).build();
        errorSound = soundPool.load(this, R.raw.error, 1);

        //Shared Preferences Get from Settings, make a default string
        sp = getSharedPreferences("settingsPreferences", Context.MODE_PRIVATE);

        //Default token quantities set map
        Map<String,String> defaultTokensMap = new HashMap<String,String>(){{
            put("auto_fail_token.png","1");
            put("cultist_token.png","1");
            put("skull_token.png","1");
            put("elder_thing_token.png","1");
            put("grave_stone_token.png","1");
            put("elder_sign_token.png","1");
            put("plus_one_token.png","1");
            put("zero_token.png","1");
            put("minus_one_token.png","1");
            put("minus_two_token.png","1");
            put("minus_three_token.png","1");
            put("minus_four_token.png","0");
            put("minus_five_token.png","0");
            put("minus_six_token.png","0");
            put("minus_seven_token.png","0");
            put("minus_eight_token.png","0");
        }};
        String defaultMapString = defaultTokensMap.toString();

        //Get token bag "memory" hashmap->string from activity main shared preferences
        SharedPreferences gp = getApplicationContext().getSharedPreferences("settingsPreferences", Context.MODE_PRIVATE);
        String tokensStrMapFromActivityMain = gp.getString("savedTokenQtysStr", defaultMapString);
        try {
            setQuantitiesFromActivityMain(tokensStrMapFromActivityMain);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setQuantitiesFromActivityMain(String s) throws IOException {
        //Convert stringified hashmap from settings back into a hashmap

        // use properties to restore the map
        Properties props = new Properties();
        props.load(new StringReader(s.substring(1, s.length() - 1).replace(", ", "\n")));
        Map<String, String> qtysMap = new HashMap<String, String>();
        for (Map.Entry<Object, Object> e : props.entrySet()) {
            qtysMap.put((String)e.getKey(), (String) e.getValue());
        }

        //update token textviews with qtys from hashmap above
        for(Map.Entry<String, String> set : qtysMap.entrySet()){
            switch ( set.getKey() ){
                case "auto_fail_token.png":
                    auto_fail_tv.setText(set.getValue());
                    break;
                case "cultist_token.png":
                    cultist_tv.setText(set.getValue());
                    break;
                case "skull_token.png":
                    skull_tv.setText(set.getValue());
                    break;
                case "elder_sign_token.png":
                    elder_sign_tv.setText(set.getValue());
                    break;
                case "elder_thing_token.png":
                    elder_thing_tv.setText(set.getValue());
                    break;
                case "grave_stone_token.png":
                    grave_stone_tv.setText(set.getValue());
                    break;
                case "minus_eight_token.png":
                    minus_eight_tv.setText(set.getValue());
                    break;
                case "minus_seven_token.png":
                    minus_seven_tv.setText(set.getValue());
                    break;
                case "minus_six_token.png":
                    minus_six_tv.setText(set.getValue());
                    break;
                case "minus_five_token.png":
                    minus_five_tv.setText(set.getValue());
                    break;
                case "minus_four_token.png":
                    minus_four_tv.setText(set.getValue());
                    break;
                case "minus_three_token.png":
                    minus_three_tv.setText(set.getValue());
                    break;
                case "minus_two_token.png":
                    minus_two_tv.setText(set.getValue());
                    break;
                case "minus_one_token.png":
                    minus_one_tv.setText(set.getValue());
                    break;
                case "zero_token.png":
                    zero_tv.setText(set.getValue());
                    break;
                case "plus_one_token.png":
                    plus_one_tv.setText(set.getValue());
                    break;
                default:
                    Toast.makeText(Settings.this, "Invalid token in hashmap", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public String assembledTokenList (){
        String tokenList = "";
        // loop through each token textview, get text, set to string
        // nested loop add one token to loop for each quantity found ie 1,4,0
        //Adds auto fails to tokenList respective to user input
        int auto_fail_qty = Integer.parseInt(auto_fail_tv.getText().toString());
        for(int i = 0; i < auto_fail_qty; i++){
            //if the bag is empty, add token
            //if there is already 1+ token(s) in the bag, prepend a comma first
            if(tokenList != ""){
                tokenList += ",";
            }
            tokenList += "auto_fail_token.png";
        }

        //Adds cultists to tokenList respective to user input
        int cultist_qty = Integer.parseInt(cultist_tv.getText().toString());
        for(int i = 0; i < cultist_qty; i++){
            if(tokenList != ""){ tokenList += ","; }
            tokenList += "cultist_token.png";
        }

        //Adds skulls to tokenList respective to user input
        int skull_qty = Integer.parseInt(skull_tv.getText().toString());
        for(int i = 0; i < skull_qty; i++){
            if(tokenList != ""){ tokenList += ","; }
            tokenList += "skull_token.png";
        }

        //Adds elder things to tokenList respective to user input
        int elder_thing_qty = Integer.parseInt(elder_thing_tv.getText().toString());
        for(int i = 0; i < elder_thing_qty; i++){
            if(tokenList != ""){ tokenList += ","; }
            tokenList += "elder_thing_token.png";
        }

        //Adds grave stones to tokenList respective to user input
        int grave_stone_qty = Integer.parseInt(grave_stone_tv.getText().toString());
        for(int i = 0; i < grave_stone_qty; i++){
            if(tokenList != ""){ tokenList += ","; }
            tokenList += "grave_stone_token.png";
        }

        //Adds elder signs to tokenList respective to user input
        int elder_sign_qty = Integer.parseInt(elder_sign_tv.getText().toString());
        for(int i = 0; i < elder_sign_qty; i++){
            if(tokenList != ""){ tokenList += ","; }
            tokenList += "elder_sign_token.png";
        }

        //Adds plus one's to tokenList respective to user input
        int plus_one_qty = Integer.parseInt(plus_one_tv.getText().toString());
        for(int i = 0; i < plus_one_qty; i++){
            if(tokenList != ""){ tokenList += ","; }
            tokenList += "plus_one_token.png";
        }

        //Adds zeroes to tokenList respective to user input
        int zero_qty = Integer.parseInt(zero_tv.getText().toString());
        for(int i = 0; i < zero_qty; i++){
            if(tokenList != ""){ tokenList += ","; }
            tokenList += "zero_token.png";
        }

        //Adds minus one's to tokenList respective to user input
        int minus_one_qty = Integer.parseInt(minus_one_tv.getText().toString());
        for(int i = 0; i < minus_one_qty; i++){
            if(tokenList != ""){ tokenList += ","; }
            tokenList += "minus_one_token.png";
        }

        //Adds minus two's to tokenList respective to user input
        int minus_two_qty = Integer.parseInt(minus_two_tv.getText().toString());
        for(int i = 0; i < minus_two_qty; i++){
            if(tokenList != ""){ tokenList += ","; }
            tokenList += "minus_two_token.png";
        }

        //Adds minus three's to tokenList respective to user input
        int minus_three_qty = Integer.parseInt(minus_three_tv.getText().toString());
        for(int i = 0; i < minus_three_qty; i++){
            if(tokenList != ""){ tokenList += ","; }
            tokenList += "minus_three_token.png";
        }

        //Adds minus four's to tokenList respective to user input
        int minus_four_qty = Integer.parseInt(minus_four_tv.getText().toString());
        for(int i = 0; i < minus_four_qty; i++){
            if(tokenList != ""){ tokenList += ","; }
            tokenList += "minus_four_token.png";
        }

        //Adds minus five's to tokenList respective to user input
        int minus_five_qty = Integer.parseInt(minus_five_tv.getText().toString());
        for(int i = 0; i < minus_five_qty; i++){
            if(tokenList != ""){ tokenList += ","; }
            tokenList += "minus_five_token.png";
        }

        //Adds minus six's to tokenList respective to user input
        int minus_six_qty = Integer.parseInt(minus_six_tv.getText().toString());
        for(int i = 0; i < minus_six_qty; i++){
            if(tokenList != ""){ tokenList += ","; }
            tokenList += "minus_six_token.png";
        }

        //Adds minus seven's to tokenList respective to user input
        int minus_seven_qty = Integer.parseInt(minus_seven_tv.getText().toString());
        for(int i = 0; i < minus_seven_qty; i++){
            if(tokenList != ""){ tokenList += ","; }
            tokenList += "minus_seven_token.png";
        }

        //Adds minus eight's to tokenList respective to user input
        int minus_eight_qty = Integer.parseInt(minus_eight_tv.getText().toString());
        for(int i = 0; i < minus_eight_qty; i++){
            if(tokenList != ""){ tokenList += ","; }
            tokenList += "minus_eight_token.png";
        }
        return tokenList;
    }

    public void saved(View view) {
        testListString = assembledTokenList();
        defaultTokenListString = "auto_fail_token.png,grave_stone_token.png,cultist_token.png,elder_sign_token.png,elder_thing_token.png,plus_one_token.png,zero_token.png,minus_one_token.png,minus_two_token.png,minus_three_token.png";
        
        //Set up putting string of tokens in shared preferences to MainActivity.java
        Toast.makeText(Settings.this, "Changes Saved.", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("tokensInBagString", testListString);
        editor.apply();

        //Save settings quantites in hashMap, in onCreate build from preferences if exists or use default map string
        //Tokens user selected in settings when clicked SAVE
        Map<String,String> currentTokensMap = new HashMap<String,String>(){{
            put("auto_fail_token.png",auto_fail_tv.getText().toString());
            put("cultist_token.png",cultist_tv.getText().toString());
            put("skull_token.png",skull_tv.getText().toString());
            put("elder_thing_token.png",elder_thing_tv.getText().toString());
            put("grave_stone_token.png",grave_stone_tv.getText().toString());
            put("elder_sign_token.png",elder_sign_tv.getText().toString());
            put("plus_one_token.png",plus_one_tv.getText().toString());
            put("zero_token.png",zero_tv.getText().toString());
            put("minus_one_token.png",minus_one_tv.getText().toString());
            put("minus_two_token.png",minus_two_tv.getText().toString());
            put("minus_three_token.png",minus_three_tv.getText().toString());
            put("minus_four_token.png",minus_four_tv.getText().toString());
            put("minus_five_token.png",minus_five_tv.getText().toString());
            put("minus_six_token.png",minus_six_tv.getText().toString());
            put("minus_seven_token.png",minus_seven_tv.getText().toString());
            put("minus_eight_token.png",minus_eight_tv.getText().toString());
        }};
        //String form of above map
        String currentMapString = currentTokensMap.toString();
        editor.putString("savedTokenQtysStr", currentMapString);
        editor.apply();
    }

    public void playErrorNoise(){
        //Plays error Sound effect -----------------------------------------------------------
        soundPool.autoPause(); // pauses whatever was playing, so you can hear the next sound ..
        soundPool.play(errorSound, 1, 1, 0, 0, 1);
    }

    public void ReturnToMain(View view) {
        Intent i = new Intent(Settings.this, MainActivity.class);
        startActivity(i);
    }

    public void minusAutoFail(View view) {
        int qty = Integer.parseInt("" + auto_fail_tv.getText());
        if (qty > 0){
            qty--;
            String str = Integer.toString(qty);
            auto_fail_tv.setText(str);
        }
        else{
            playErrorNoise();
        }
    }

    public void minusCultist(View view) {
        int qty = Integer.parseInt("" + cultist_tv.getText());
        if (qty > 0){
            qty--;
            String str = Integer.toString(qty);
            cultist_tv.setText(str);
        }
        else{
            playErrorNoise();
        }
    }

    public void minusSkull(View view) {
        int qty = Integer.parseInt("" + skull_tv.getText());
        if (qty > 0){
            qty--;
            String str = Integer.toString(qty);
            skull_tv.setText(str);
        }
        else{
            playErrorNoise();
        }
    }

    public void minusElderThing(View view) {
        int qty = Integer.parseInt("" + elder_thing_tv.getText());
        if (qty > 0){
            qty--;
            String str = Integer.toString(qty);
            elder_thing_tv.setText(str);
        }
        else{
            playErrorNoise();
        }
    }

    public void minusGraveStone(View view) {
        int qty = Integer.parseInt("" + grave_stone_tv.getText());
        if (qty > 0){
            qty--;
            String str = Integer.toString(qty);
            grave_stone_tv.setText(str);
        }
        else{
            playErrorNoise();
        }
    }

    public void minusElderSign(View view) {
        int qty = Integer.parseInt("" + elder_sign_tv.getText());
        if (qty > 0){
            qty--;
            String str = Integer.toString(qty);
            elder_sign_tv.setText(str);
        }
        else{
            playErrorNoise();
        }
    }

    public void minusPlusOne(View view) {
        int qty = Integer.parseInt("" + plus_one_tv.getText());
        if (qty > 0){
            qty--;
            String str = Integer.toString(qty);
            plus_one_tv.setText(str);
        }
        else{
            playErrorNoise();
        }
    }

    public void minusZero(View view) {
        int qty = Integer.parseInt("" + zero_tv.getText());
        if (qty > 0){
            qty--;
            String str = Integer.toString(qty);
            zero_tv.setText(str);
        }
        else{
            playErrorNoise();
        }
    }

    public void minusMinusOne(View view) {
        int qty = Integer.parseInt("" + minus_one_tv.getText());
        if (qty > 0){
            qty--;
            String str = Integer.toString(qty);
            minus_one_tv.setText(str);
        }
        else{
            playErrorNoise();
        }
    }

    public void minusMinusTwo(View view) {
        int qty = Integer.parseInt("" + minus_two_tv.getText());
        if (qty > 0){
            qty--;
            String str = Integer.toString(qty);
            minus_two_tv.setText(str);
        }
        else{
            playErrorNoise();
        }
    }

    public void minusMinusThree(View view) {
        int qty = Integer.parseInt("" + minus_three_tv.getText());
        if (qty > 0){
            qty--;
            String str = Integer.toString(qty);
            minus_three_tv.setText(str);
        }
        else{
            playErrorNoise();
        }
    }

    public void minusMinusFour(View view) {
        int qty = Integer.parseInt("" + minus_four_tv.getText());
        if (qty > 0){
            qty--;
            String str = Integer.toString(qty);
            minus_four_tv.setText(str);
        }
        else{
            playErrorNoise();
        }
    }

    public void minusMinusFive(View view) {
        int qty = Integer.parseInt("" + minus_five_tv.getText());
        if (qty > 0){
            qty--;
            String str = Integer.toString(qty);
            minus_five_tv.setText(str);
        }
        else{
            playErrorNoise();
        }
    }

    public void minusMinusSix(View view) {
        int qty = Integer.parseInt("" + minus_six_tv.getText());
        if (qty > 0){
            qty--;
            String str = Integer.toString(qty);
            minus_six_tv.setText(str);
        }
        else{
            playErrorNoise();
        }
    }

    public void minusMinusSeven(View view) {
        int qty = Integer.parseInt("" + minus_seven_tv.getText());
        if (qty > 0){
            qty--;
            String str = Integer.toString(qty);
            minus_seven_tv.setText(str);
        }
        else{
            playErrorNoise();
        }
    }

    public void minusMinusEight(View view) {
        int qty = Integer.parseInt("" + minus_eight_tv.getText());
        if (qty > 0){
            qty--;
            String str = Integer.toString(qty);
            minus_eight_tv.setText(str);
        }
        else{
            playErrorNoise();
        }
    }

    public void addAutoFail(View view) {
        String str = Integer.toString(Integer.parseInt("" + auto_fail_tv.getText()) + 1);
        auto_fail_tv.setText(str);
    }

    public void addCultist(View view) {
        String str = Integer.toString(Integer.parseInt("" + cultist_tv.getText()) + 1);
        cultist_tv.setText(str);
    }

    public void addSkull(View view) {
        String str = Integer.toString(Integer.parseInt("" + skull_tv.getText()) + 1);
        skull_tv.setText(str);
    }

    public void addElderThing(View view) {
        String str = Integer.toString(Integer.parseInt("" + elder_thing_tv.getText()) + 1);
        elder_thing_tv.setText(str);
    }

    public void addGraveStone(View view) {
        String str = Integer.toString(Integer.parseInt("" + grave_stone_tv.getText()) + 1);
        grave_stone_tv.setText(str);
    }

    public void addElderSign(View view) {
        String str = Integer.toString(Integer.parseInt("" + elder_sign_tv.getText()) + 1);
        elder_sign_tv.setText(str);
    }

    public void addPlusOne(View view) {
        String str = Integer.toString(Integer.parseInt("" + plus_one_tv.getText()) + 1);
        plus_one_tv.setText(str);
    }

    public void addZero(View view) {
        String str = Integer.toString(Integer.parseInt("" + zero_tv.getText()) + 1);
        zero_tv.setText(str);
    }

    public void addMinusOne(View view) {
        String str = Integer.toString(Integer.parseInt("" + minus_one_tv.getText()) + 1);
        minus_one_tv.setText(str);
    }

    public void addMinusTwo(View view) {
        String str = Integer.toString(Integer.parseInt("" + minus_two_tv.getText()) + 1);
        minus_two_tv.setText(str);
    }

    public void addMinusThree(View view) {
        String str = Integer.toString(Integer.parseInt("" + minus_three_tv.getText()) + 1);
        minus_three_tv.setText(str);
    }

    public void addMinusFour(View view) {
        String str = Integer.toString(Integer.parseInt("" + minus_four_tv.getText()) + 1);
        minus_four_tv.setText(str);
    }

    public void addMinusFive(View view) {
        String str = Integer.toString(Integer.parseInt("" + minus_five_tv.getText()) + 1);
        minus_five_tv.setText(str);
    }

    public void addMinusSix(View view) {
        String str = Integer.toString(Integer.parseInt("" + minus_six_tv.getText()) + 1);
        minus_six_tv.setText(str);
    }

    public void addMinusOSeven(View view) {
        String str = Integer.toString(Integer.parseInt("" + minus_seven_tv.getText()) + 1);
        minus_seven_tv.setText(str);
    }

    public void addMinusEight(View view) {
        String str = Integer.toString(Integer.parseInt("" + minus_eight_tv.getText()) + 1);
        minus_eight_tv.setText(str);
    }
}