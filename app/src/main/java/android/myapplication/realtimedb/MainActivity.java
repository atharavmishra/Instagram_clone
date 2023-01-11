package android.myapplication.realtimedb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private EditText txt;
    private Button Add;
    private ListView lst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button logout = findViewById(R.id.logout);
        lst = findViewById(R.id.list);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));

            }
        });

        txt = findViewById(R.id.edt);
        Add = findViewById(R.id.add);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txt.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter text", Toast.LENGTH_SHORT).show();

                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("DB1").child("Name").setValue(name);
                }
            }
        });
       final ArrayList<String> list= new ArrayList<String>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listview, list);
        lst.setAdapter(adapter);
       DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("DB1");
       db.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot sp : snapshot.getChildren()){
                   list.add(snapshot.getValue().toString());
               }
               adapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });




    }
}