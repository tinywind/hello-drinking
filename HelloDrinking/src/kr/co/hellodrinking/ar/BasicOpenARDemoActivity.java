package kr.co.hellodrinking.ar;

import java.util.ArrayList;
import java.util.List;

import kr.co.hellodrinking.R;

import org.openintents.intents.AbstractWikitudeARIntent;
import org.openintents.intents.WikitudeARIntent;
import org.openintents.intents.WikitudePOI;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BasicOpenARDemoActivity extends Activity {
    private static final String CALLBACK_INTENT = "wikitudeapi.mycallbackactivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basicopenardemo);
        
        ((Button) findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BasicOpenARDemoActivity.this.startARViewWithIcons();
            }
        });
    }

    void startARViewWithIcons() {
        WikitudeARIntent intent = prepareIntent();
        intent.addTitleText("AR app with custom icons");
        intent.setPrintMarkerSubText(false);
        addIcons(intent);
        try {
            intent.startIntent(this);
        } catch (ActivityNotFoundException e) {
            AbstractWikitudeARIntent.handleWikitudeNotFound(this);
        }
    }

    private WikitudeARIntent prepareIntent() {
        WikitudeARIntent intent = new WikitudeARIntent(this.getApplication(), "b82915e9-2710-4c86-bbc3-62a6be59f026", "Jeon");
        this.addPois(intent);
        intent.setMenuItem1("My menu item", BasicOpenARDemoActivity.CALLBACK_INTENT);
        intent.setPrintMarkerSubText(true);
        return intent;
    }

    private void addPois(WikitudeARIntent intent) {
        WikitudePOI poi1 = new WikitudePOI(35.683333, 139.766667, 36, "Tokyo", "the capital of Japan.");
        poi1.setLink("http://www.tourism.metro.tokyo.jp/");
        poi1.setDetailAction(BasicOpenARDemoActivity.CALLBACK_INTENT);
        
        WikitudePOI poi2 = new WikitudePOI(41.9, 12.5, 14, "Rome", "the capital of Italy");
        poi2.setDetailAction(BasicOpenARDemoActivity.CALLBACK_INTENT);
        
        List<WikitudePOI> pois = new ArrayList<WikitudePOI>();
        pois.add(poi1);
        pois.add(poi2);
        intent.addPOIs(pois);

        ((BasicOpenARDemoApplication) this.getApplication()).setPois(pois);
    }
    
    private void addIcons(WikitudeARIntent intent) {
        ArrayList<WikitudePOI> pois = intent.getPOIs();

        Resources res = getResources();
        
        pois.get(0).setIconresource(res.getResourceName(R.drawable.flag_japan));
        pois.get(1).setIconresource(res.getResourceName(R.drawable.flag_italy));
    }
}