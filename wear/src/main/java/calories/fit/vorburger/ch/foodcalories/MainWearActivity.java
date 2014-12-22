package calories.fit.vorburger.ch.foodcalories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainWearActivity extends Activity implements WearableListView.ClickListener {

    private static final String LOG_TAG = "ch.vorburger.wear.Calories";
    private static final int SPEECH_REQUEST_CODE = 0;

    private PersistentNamedValuePair<Integer> totalCaloriesEatenToday;

    private FoodRepository foodRepository = new FixedFoodRepositoryImpl();
    private final int idealMaxCaloriesToday = 1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        totalCaloriesEatenToday = new PersistentNamedValuePair<>(this, "totalCaloriesEatenToday", 0);
        startRecognizeSpeechAction();
        // NO voice, late night dev test, only (and comment out the last line above):
        //List<Food> apples = foodRepository.find("apple");
        //showPickerView(apples);
    }

    private void showMainView(Food food) {
        setContentView(R.layout.activity_main_wear);
        if (food != null) {
            TextView mTextView = (TextView) findViewById(R.id.text);
            totalCaloriesEatenToday.setValue(totalCaloriesEatenToday.getValue() + food.calories);
            String confirmationText = getString(R.string.msg_you_ate) + " " + food.articlePrefix + " " + food.name + " (+" + food.calories + " Cals; now " + totalCaloriesEatenToday.getValue() + "/" + idealMaxCaloriesToday + ")";
            mTextView.setText(confirmationText);
        }
    }

    private void showPickerView(List<Food> results) {
        setContentView(R.layout.list);
        WearableListView listView = (WearableListView) findViewById(R.id.wearable_list);
        listView.setAdapter(new WearableListViewAdapter(this, results));
        listView.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder v) {
        Food food = (Food) v.itemView.getTag();
        showMainView(food);
    }

    @Override
    public void onTopEmptyRegionClick() {
    }

    private void startRecognizeSpeechAction() {
        // https://developer.android.com/training/wearables/apps/voice.html
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // TODO Config in companion app to switch between these two modes - which works better for users?
        // intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10); // This seems to be ignored? TODO, make configurable from companion app?
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What did you eat?");
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            // https://developer.android.com/training/wearables/apps/voice.html
            List<String> intentResults = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            // https://code.google.com/p/android/issues/detail?id=23606
            float [] scores = data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);

            for (int i = 0; i < intentResults.size(); i++) {
                Log.i(LOG_TAG, "HEARD: " + intentResults.get(i) + " (confidence: " + scores[i] + ")");
            }

            // TODO improve accuracy via a phonetic matching algorithm
            // TODO consider all possible matches, not just first one, ordered by confidence level?
            String spokenText = intentResults.get(0);
            List<Food> results = foodRepository.find(spokenText);
            if (results.isEmpty()) {
                // TODO UI, how to do the "didn't catch that" UI ?
                startRecognizeSpeechAction();

            } else if (results.size() == 1) {
                Food r = results.get(0);
                showMainView(r);

            } else { // results.size() > 1
                Log.e(LOG_TAG, "Multiple choices: " + results.toString());
                showPickerView(results);

            }
        }
        // NO } else {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
