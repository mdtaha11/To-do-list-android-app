package com.example.to_dolist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final List<String> list= new ArrayList<>();

    int j=1;
     ArrayList<String> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = findViewById(R.id.items_list);
        final TextAdapter adapter = new TextAdapter();

        readInfo();
        adapter.setData(list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete this task?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        list.remove(i);
                        adapter.setData(list);
                    }
                }).setNegativeButton("No",null)
                        .create();
                dialog.show();
            }
        });





    final Button myButton=findViewById(R.id.myButton);
    myButton.setOnClickListener( new  View.OnClickListener() {

        public void onClick(View view) {

            final EditText taskInput = new EditText(MainActivity.this);
            taskInput.setSingleLine();
            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Add a Task")
                    .setMessage("What is your Task")
                    .setView(taskInput)
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                   list.add((j++)+"." + " " +taskInput.getText().toString());


                                    adapter.setData(list);

                                }
                            }
                    )
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
        }

    });

}
    @Override
    protected void onPause()
    {
        super.onPause();
        saveInfo();
    }

    private void saveInfo() {
        try{
            File file=new File(this.getFilesDir(),"saved");

            FileOutputStream fOut=new FileOutputStream(file);
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(fOut));
            for(int i=0;i<list.size();i++)
            {
                bw.write(list.get(i));
                bw.newLine();

            }
            bw.close();
            fOut.close();


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void readInfo()
    {
        File file=new File(this.getFilesDir(),"saved");
        if (!file.exists()) {
            return;

        }

        try {
            FileInputStream is=new FileInputStream(file);
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            String line=br.readLine();
            while(line!=null){
                list.add(line);
                line=br.readLine();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }



class TextAdapter extends BaseAdapter {

        List<String> list= new ArrayList<>();
        void setData(List<String> mList){
            list.clear();
            list.addAll(mList);
        }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
        @Override
        public long getItemId(int position)
        {
            return 0;
        }
    @Override
    public View getView(int position,View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.items, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.task);

        textView.setText(list.get(position));
        return rowView;

    }
    }
}