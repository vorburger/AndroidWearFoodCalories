package calories.fit.vorburger.ch.foodcalories;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainWearActivity extends Activity {

    private static final String LOG_TAG = "ch.vorburger.wear.Calories";
    private static final int SPEECH_REQUEST_CODE = 0;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_wear);
        mTextView = (TextView) findViewById(R.id.text);
/*
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                mTextView.setText("Ho ho ho.. say something! ;)");
            }
        });
*/
        // https://developer.android.com/training/wearables/apps/voice.html
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            // https://developer.android.com/training/wearables/apps/voice.html
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            Log.i(LOG_TAG, "HEARD: " + spokenText);

            // TODO look up spoken item in our food database, and turn e.g. "apple" into "an apple" or "a bread" (with prefix)

            String confirmationText = getString(R.string.msg_you_ate) + " an " + spokenText + " (+123 calories; now 456/789)";
            // showSuccessConfirmation(this, confirmationText);
            mTextView.setText(confirmationText);
        }
        // NO } else {
        super.onActivityResult(requestCode, resultCode, data);
    }

/*
    // This Confirmation always disappears immediately :-(
    protected void showSuccessConfirmation(Context context, String message) {
        Intent intent = new Intent(context, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, message);
        context.startActivity(intent);
    }
 */
}
