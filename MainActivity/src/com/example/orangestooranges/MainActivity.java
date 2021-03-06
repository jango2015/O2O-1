package com.example.orangestooranges;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.orangestooranges.library.UserFunctions;

public class MainActivity extends Activity implements OnClickListener {
	UserFunctions userFunctions;
	Button btnLogout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		/**
         * Dashboard Screen for the application
         * */        
        // Check login status in database
        userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())){
        	setContentView(R.layout.activity_main);
        	btnLogout = (Button) findViewById(R.id.logoutButton);
        	
        	btnLogout.setOnClickListener(new View.OnClickListener() {
    			
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				userFunctions.logoutUser(getApplicationContext());
    				Intent login = new Intent(getApplicationContext(), LoginActivity.class);
    	        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	        	startActivity(login);
    	        	// Closing dashboard screen
    	        	finish();
    	        	
    	        	Button mainPlay = (Button)findViewById(R.id.mainPlayButton);
    	    		mainPlay.setOnClickListener(this);
    	    		Button mainSettings = (Button)findViewById(R.id.mainSettingsButton);
    	    		mainSettings.setOnClickListener(this);
    	    		Button mainFriends = (Button)findViewById(R.id.mainFriendsButton);
    	    		mainFriends.setOnClickListener(this);
    	    		Button mainCreate = (Button)findViewById(R.id.mainCreateButton);
    	    		mainCreate.setOnClickListener(this);
    	    		Button mainStats = (Button)findViewById(R.id.mainStatsButton);
    	    		mainStats.setOnClickListener(this);
    			}
    		});
        	
        }
        else{
        	// user is not logged in show login screen
        	Intent login = new Intent(getApplicationContext(), LoginActivity.class);
        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(login);
        	// Closing dashboard screen
        	finish();
        }
        
		/* Carson: added this to filter the flow into my new screen
		 * (play game menu).
		 *
		 * This code can be used as standard template for switching views
		 */
	}
	
	public void onClick(View v) {
		// Steve:
	    // to change screen with specific button remove Toast.makeText() and add startActivity()
		switch (v.getId()) {
	        case R.id.mainPlayButton: 
	        	//startActivity(new Intent(MainActivity.this, PlayGameMenu.class));
	        	break;
	        case R.id.mainSettingsButton:
	        	//startActivity(new Intent(MainActivity.this, SettingActivity.class));
	        	break;
	        case R.id.mainFriendsButton:
	        	Toast.makeText(MainActivity.this, "You Click Friends List", Toast.LENGTH_SHORT).show();
	            break;
	        case R.id.mainCreateButton:
	        	Toast.makeText(MainActivity.this, "You Click Create Game", Toast.LENGTH_SHORT).show();
	            break;
	        case R.id.mainStatsButton:
	        	Toast.makeText(MainActivity.this, "You Click Stats", Toast.LENGTH_SHORT).show();
	            break;
		}
	}
}
