package calories.fit.vorburger.ch.foodcalories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainWearActivity extends Activity {

    private static final String LOG_TAG = "ch.vorburger.wear.Calories";
    private static final int SPEECH_REQUEST_CODE = 0;

    private FoodRepository foodRepository = new FixedFoodRepositoryImpl();
    private int totalCaloriesEatenToday = 0;
    private int idealMaxCaloriesToday = 1200;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_wear);
        mTextView = (TextView) findViewById(R.id.text);

        startRecognizeSpeechAction();
    }

    private void startRecognizeSpeechAction() {
        // https://developer.android.com/training/wearables/apps/voice.html
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            // https://developer.android.com/training/wearables/apps/voice.html
            List<String> intentResults = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = intentResults.get(0);
            Log.i(LOG_TAG, "HEARD: " + spokenText);

            List<Food> results = foodRepository.find(spokenText);
            if (results.isEmpty()) {
                // TODO UI, how to do the "didn't catch that" UI ?
                startRecognizeSpeechAction();

            } else if (results.size() == 1) {
                Food r = results.get(0);
                totalCaloriesEatenToday += r.calories;
                String confirmationText = getString(R.string.msg_you_ate) + " " + r.articlePrefix + r.name + " (+ " + r.calories + " calories; now " + totalCaloriesEatenToday + "/" + idealMaxCaloriesToday + ")";
                mTextView.setText(confirmationText);

            } else { // results.size() > 1
                Log.e(LOG_TAG, "Multiple choices: " + results.toString());
                // TODO multi choice UI...

            }
        }
        // NO } else {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
