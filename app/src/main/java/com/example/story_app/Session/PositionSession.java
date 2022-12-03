package com.example.story_app.Session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.story_app.MainActivity;

import java.util.HashMap;

public class PositionSession {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String POSITION = "POSITON";

    // Constructor
    public PositionSession(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create positon session
     */
    public void createPositionSession(String position) {
        // Storing login value as TRUE
        editor.putString(POSITION, position);

        // commit changes
        editor.commit();
    }

    /**
     * Quick check for login
     **/
    // Get position State
    public String GetPositionComment() {
        return pref.getString(POSITION, null);
    }
}
