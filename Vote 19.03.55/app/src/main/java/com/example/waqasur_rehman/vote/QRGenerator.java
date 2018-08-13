package com.example.waqasur_rehman.vote;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import net.glxn.qrgen.core.scheme.VCard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ezvcard.Ezvcard;


/**
 * Created by waqas on 26/08/2017.
 */

public class QRGenerator extends BaseActivity {

    ImageView image;

String question,state;
    ArrayList<String> optionList;

    JSONArray array ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generator_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            question = bundle.getString("question");
            optionList = bundle.getStringArrayList("array");
            state = bundle.getString("state");

        }
        //Toast.makeText(QRGenerator.this,"question ;" + question +"\n" + "options " + optionList.toString() +"\n" + "state " + state,Toast.LENGTH_LONG ).show();
        image = (ImageView)findViewById(R.id.Qr_code);


//        VCard vCard = new VCard("values");
//
//        vCard.setName(question);
//        vCard.setNote(optionList.toString());
//        vCard.setTitle(state);
//
//        Log.d("Vcard", vCard.toString());

//        ezvcard.VCard  vCard = new ezvcard.VCard();
//
//
//        vCard.setNickname(question);
//        vCard.setCategories(optionList.toString());
//        vCard.setClassification(state);
//
//        String json = Ezvcard.writeJson(vCard).go();

//        ArrayList list =  new ArrayList();
//
//        list.add(question);
//        list.add(optionList);
//        list.add(state);




        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try{


            JSONArray array1 = new JSONArray(optionList);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("question", question);
            jsonObject.put("options", array1);
            jsonObject.put("state", state);

            JSONArray array = new JSONArray();
            array.put(jsonObject);

            Log.d(" array1", array1.toString());

            BitMatrix bitMatrix = multiFormatWriter.encode(array.toString(),BarcodeFormat.QR_CODE,200,200);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

            image = (ImageView)findViewById(R.id.Qr_code);

            image.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }





    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }






}
