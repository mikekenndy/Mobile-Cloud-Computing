package ESE343.mikekenndy.github.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.main_txt_box);
        findViewById(R.id.main_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        // Get user input and clear text from textbox
        String userInput = mEditText.getText().toString();
        mEditText.getText().clear();

        // Send text to 'SendTextHere' activity
        Intent intent = new Intent(this, SendTextHere.class);
        intent.putExtra("userInput", userInput);

        startActivity(intent);

    }
}
