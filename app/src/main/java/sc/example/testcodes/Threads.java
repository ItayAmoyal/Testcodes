package sc.example.testcodes;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Threads extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        registerForContextMenu(findViewById(R.id.main));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("AsyncTask");
        menu.add("Threads");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        String st=item.getTitle().toString();
        if(st.equals("AsyncTask")){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}