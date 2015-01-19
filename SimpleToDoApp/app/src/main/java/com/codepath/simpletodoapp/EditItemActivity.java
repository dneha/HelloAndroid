package com.codepath.simpletodoapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class EditItemActivity extends ActionBarActivity {

    private int editItemPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Intent intent = getIntent();
        String item = intent.getStringExtra(MainActivity.EXTRA_ITEM);
        editItemPos = intent.getIntExtra(MainActivity.EXTRA_ITEM_POS, editItemPos);
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(item);
        int cursorPos = item.length();
        editText.setSelection(cursorPos);
        editText.requestFocus();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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

    public void onItemSaveClicked (View v) {
        EditText editText = (EditText) findViewById(R.id.editText);
        Intent saveIntent = new Intent();
        saveIntent.putExtra(MainActivity.EXTRA_ITEM, editText.getText().toString());
        saveIntent.putExtra(MainActivity.EXTRA_ITEM_POS, editItemPos);
        setResult(RESULT_OK, saveIntent);
        this.finish();
    }
}
