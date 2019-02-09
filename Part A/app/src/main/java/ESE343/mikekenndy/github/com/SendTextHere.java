package ESE343.mikekenndy.github.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SendTextHere extends AppCompatActivity {

    private TextView mTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text_here);

        mTextview = (TextView) findViewById(R.id.sent_text);

        // Get text from previous activity
        Intent intent = getIntent();
        String fromActivity = intent.getStringExtra("userInput");

        // Display text in activity
        mTextview.setText(fromActivity);
    }
}
