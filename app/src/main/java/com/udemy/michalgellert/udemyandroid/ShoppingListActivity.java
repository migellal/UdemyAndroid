package com.udemy.michalgellert.udemyandroid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingListActivity extends AppCompatActivity {

    @BindView(R.id.itemName_ET)
    EditText itemName;
    @BindView(R.id.itemList)
    ListView itemList;
    @BindView(R.id.itemSpinner)
    Spinner itemSpinner;
    private List<String> listItems;
    private List<String> spinnerItems;
    private Set<String> listSetItems;
    private Set<String> spinnerSetItems;
    private static final String LIST_ITEMS_KEY = "LIST_ITEMS_KEY";
    private static final String SPINNER_ITEMS_KEY = "SPINNER_ITEMS_KEY";
    private static final String SHOPPING_LIST_KEY = "SHOPPING_LIST_KEY";
    private ShoppingListAdapter listAdapter;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemName.getText() != null && !itemName.getText().toString().trim().isEmpty() && listSetItems.add(itemName.getText().toString())) {
                    listItems.add(itemName.getText().toString());
                    itemName.setText("");
                    listAdapter.notifyDataSetChanged();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sp = getSharedPreferences(SHOPPING_LIST_KEY, MODE_PRIVATE);
        listSetItems = sp.getStringSet(LIST_ITEMS_KEY, new ArraySet<String>());
        spinnerSetItems = sp.getStringSet(SPINNER_ITEMS_KEY, new ArraySet<String>());
        listItems = new ArrayList<>(listSetItems);
        spinnerItems = new ArrayList<>(spinnerSetItems);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, spinnerItems);
        itemSpinner.setAdapter(spinnerAdapter);
        itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerItems.get(position).equals("")) {
                    itemName.setText("" + spinnerItems.get(position));
                    spinnerItems.remove(position);
                    spinnerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        listAdapter = new ShoppingListAdapter(this, R.layout.row_shooping_list, listItems, spinnerAdapter, spinnerItems, itemSpinner);
        itemList.setAdapter(listAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = getSharedPreferences(SHOPPING_LIST_KEY, MODE_PRIVATE).edit();
        listSetItems = new ArraySet<>(listItems);
        spinnerSetItems = new ArraySet<>(spinnerItems);
        editor.putStringSet(LIST_ITEMS_KEY, listSetItems);
        editor.putStringSet(SPINNER_ITEMS_KEY, spinnerSetItems);
        editor.commit();
    }

}
