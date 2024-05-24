package com.example.lab3_2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Contact> contactList;
    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;

    private void addControl() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        contactList = new ArrayList<>();
        DatabaseHandler dbHandler = new DatabaseHandler(this);
        contactAdapter = new ContactAdapter(this, contactList, dbHandler);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(contactAdapter);
    }

    private void addEvent() {
        /** Call method add data*/
        createData();
    }

    /**
     * Add data to bookList
     */
    public void createData() {
        Contact contact = new Contact(1, "Hoàng Mạnh Thắng", "0909988908");
        contactList.add(contact);
        Contact contact1 = new Contact(2, "Trường CNTT ", "09224444xxx");
        contactList.add(contact1);
        contactAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControl();
        addEvent();
    }
}
//}


