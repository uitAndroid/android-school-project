package com.school.health;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import android.widget.Toast;
import java.io.FileOutputStream;
import java.util.Vector;

public class user extends Fragment {
    public user() {
    }
    public static final  String FILE_USER = "UserData.txt";
    private static EditText userWeight;
    private static EditText userFlong;
    private Button save;

    public static void save( Context fileContext,String File_Name1,String data) {
        String text = data;
        FileOutputStream fos = null;
        try {
            fos = fileContext.openFileOutput(File_Name1, fileContext.MODE_PRIVATE);
            fos .write(text.getBytes());
            Toast.makeText(fileContext,"Saved to " + fileContext.getFilesDir() + "/" + File_Name1,Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String load(Context fileContext,String File_Name1) {
        FileInputStream fis =  null;
        String output = "";
        try {
            fis = fileContext.openFileInput(File_Name1);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader  br = new BufferedReader(isr);
            StringBuilder   sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null)
            {
                sb.append(text).append("\n");
            }
            output = sb.toString();
            Toast.makeText(fileContext,"loaded !",Toast.LENGTH_LONG);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return output;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    public  static void GetData(String weight,String flong)
    {
        weight = userWeight.getText().toString().trim();
        flong = userFlong.getText().toString().trim();
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        userWeight = (EditText)getView().findViewById(R.id.userWeight);
        userFlong = (EditText)getView().findViewById(R.id.userFlong);
        save = (Button)getView().findViewById(R.id.save_btn);
        String data = "";
        data = load(getActivity(),FILE_USER);
        if(!data.equals(""))
        {
            String[] splitedData = data.split(",");
            userWeight.setText(splitedData[0]);
            userFlong.setText(splitedData[1]);
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userWeight.getText().toString().trim().equals("") && userFlong.getText().toString().trim().equals(""))
                {
                    userWeight.setText("60");
                    userFlong.setText("60");
                }
                String input = userWeight.getText().toString()+ "," + userFlong.getText().toString()+"," + java.time.LocalDate.now().toString();
                save(getActivity(),FILE_USER,input);
            }
        });
    }
}
