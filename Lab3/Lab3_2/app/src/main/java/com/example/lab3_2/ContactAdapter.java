package com.example.lab3_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Activity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.contactViewHolder> {
    private List<Contact> contactList;
    private Activity activity;
    private DatabaseHandler db;
    public ContactAdapter(Activity activity, List<Contact> contactList, DatabaseHandler dbHandler) {
        this.activity = activity;
        this.contactList = contactList;
        this.db = dbHandler;
    }

    @NonNull
    @Override
    public contactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item,parent,false);
        return new contactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull contactViewHolder holder, int position) {
        final Contact contact1 = contactList.get(position);
        holder.id.setText("STT: " + contact1.getId() + ", ");
        holder.name.setText("Tên: " + contact1.getName() + ", ");
        holder.phoneNumber.setText("SĐT: " + contact1.getPhoneNumber());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                removeData(contact1);
                return false;
            }
        });
    }
    private void removeData(Contact contact2) {
        int currentPosition = contactList.indexOf(contact2);
        contactList.remove(currentPosition);
        db.removeContact(contact2);
        notifyItemRemoved(currentPosition);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class contactViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView name;
        private TextView phoneNumber;

        public contactViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id);
            name = (TextView) itemView.findViewById(R.id.name);
            phoneNumber = (TextView) itemView.findViewById(R.id.phoneNumber);
        }
    }
}
