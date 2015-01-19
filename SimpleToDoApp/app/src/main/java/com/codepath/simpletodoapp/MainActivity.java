package com.codepath.simpletodoapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public final static String EXTRA_ITEM = "com.codepath.SimpleToDoApp.ITEM";
    public final static String EXTRA_ITEM_POS = "com.codepath.SimpleToDoApp.ITEM_POS";
    private final int EDIT_REQUEST_CODE = 10;
    ArrayList<String> itemsArray;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemsArray);
        lvItems.setAdapter(itemsAdapter);
        setUpListViewListener();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View v) {
        EditText newItem = (EditText) findViewById(R.id.eNewItem);
        String newItemText = newItem.getText().toString();
        itemsAdapter.add(newItemText);
        newItem.setText("");
        writeItems();
    }

    public void setUpListViewListener() {
        lvItems.setOnItemLongClickListener(
          new AdapterView.OnItemLongClickListener(){
              @Override
          public boolean onItemLongClick (AdapterView<?> adapter, View view, int pos, long id) {
                itemsArray.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
          }
        });

        lvItems.setOnItemClickListener(
           new AdapterView.OnItemClickListener() {
               @Override
           public void onItemClick (AdapterView<?> adapter, View view, int pos, long id) {
                   Intent editIntent = new Intent(MainActivity.this, EditItemActivity.class);
                   editIntent.putExtra(EXTRA_ITEM, itemsArray.get(pos).toString());
                   editIntent.putExtra(EXTRA_ITEM_POS, pos);
                   startActivityForResult(editIntent, EDIT_REQUEST_CODE);
               }
           }
        );
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            int pos = data.getIntExtra(EXTRA_ITEM_POS, 0);
            String itemText = data.getStringExtra(EXTRA_ITEM);
            itemsArray.set(pos,itemText);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    public void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, getString(R.string.file_name));
        try {
            itemsArray = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            itemsArray = new ArrayList<String>();
        }
    }

    public void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, getString(R.string.file_name));
        try {
            FileUtils.writeLines(todoFile, itemsArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
