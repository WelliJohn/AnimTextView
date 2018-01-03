package wellijohn.org.animtextview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import wellijohn.org.animtv.AnimTextView;

public class MainActivity extends AppCompatActivity {

    private AnimTextView mAtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mAtv.setText(222.09);
    }

    private void initView() {
        mAtv = findViewById(R.id.atv);
    }
}
