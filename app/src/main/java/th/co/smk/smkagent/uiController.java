package th.co.smk.smkagent;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by smk on 24/4/2560.
 */

public class uiController {
    public void hideActionBar(final View view) {
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                //| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                ;

        // This work only for android 4.4+
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT)
        {
            view.setSystemUiVisibility(flags);
            view
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                    {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility)
                        {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                            {
                                view.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }
    }

    public boolean checkEdittextIsNull(EditText editText,String errorStr) {
        if (editText.length()==0 || editText.toString()==" ") {
            editText.setError(errorStr);
            return true;
        }else {
            return false;
        }
    }

    public String LTrim(String string) {
        Pattern LTRIM = Pattern.compile("^\\s+");
        return LTRIM.matcher(string).replaceAll("");
    }

    public String RTrim(String string) {
        Pattern LTRIM = Pattern.compile("\\s+$");
        return LTRIM.matcher(string).replaceAll("");
    }
}
