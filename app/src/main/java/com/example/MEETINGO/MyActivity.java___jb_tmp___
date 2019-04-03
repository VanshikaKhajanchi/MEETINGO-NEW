package com.example.MEETINGO;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MyActivity  extends Activity {

    private VPaintView vpaintview;
    private PaintClient paintClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        this.vpaintview =findViewById(R.id.activity_my_view_whiteboard);
        this.testSocketIoClient();
        this.vpaintview.setPaintClient(paintClient);

        waitForSocketIoToConnect(30000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        paintClient.disconnectSocket();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void buttonClearClick(View view) {
        Log.i("INFO", "Clear button clicked");
        paintClient.emitClear();
        vpaintview.clearCanvas();
    }

    public void buttonSnapshotClick(View view) {
        Log.i("INFO", "Snapshot button clicked");

        vpaintview.setDrawingCacheEnabled(true);

        String saved = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                vpaintview.getDrawingCache(),
                UUID.randomUUID().toString() + ".png",
                "drawing"
        );

        Toast saveToast;

        if(saved != null) {
            saveToast = Toast.makeText(
                    getApplicationContext(),
                    "Whiteboard snapshot saved!",
                    Toast.LENGTH_SHORT
            );

        } else {
            saveToast = Toast.makeText(
                    getApplicationContext(),
                    "Whiteboard snapshot failed!",
                    Toast.LENGTH_SHORT
            );
        }

        saveToast.show();

        vpaintview.destroyDrawingCache();
    }

    protected void testSocketIoClient() {
        try {
            paintClient = new PaintClient();
            paintClient.setVpaintview(vpaintview);
            paintClient.setContext(this);
        } catch(Exception exception) {
            Log.e("EXCEPTION", exception.getMessage());
        }
    }

    protected void waitForSocketIoToConnect(Integer milliseconds) {
        final ProgressDialog dialog = new ProgressDialog(MyActivity.this);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Establishing connection to server...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        long delayInMillis = milliseconds;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, delayInMillis);
    }
}
