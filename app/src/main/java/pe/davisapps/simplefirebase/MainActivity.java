package pe.davisapps.simplefirebase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private String FIREBASE_URL = "https://todolist-6d704.firebaseio.com/";
    private String FIREBASE_CHILD = "test";
    @BindView(R.id.editText) EditText editText;
    Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this,"inicio en onCreate", Toast.LENGTH_SHORT).show();

        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
        firebase = new Firebase(FIREBASE_URL).child(FIREBASE_CHILD);

        firebase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    Toast.makeText(MainActivity.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                    Log.e(getLocalClassName(), snapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    @OnClick(R.id.button)
    public void writeToFirebase() {
        try {
            Toast.makeText(MainActivity.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
            firebase.setValue(editText.getText().toString());
            editText.setText("");
        }
        catch(Exception ex){
            Toast.makeText(MainActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
