package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.chat.ChatBoxActivity;
import com.example.myapplication.paint.CustomWebSocket;
import com.example.myapplication.paint.DrawView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class PaintActivity extends AppCompatActivity {

    // Permission for read and write
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;

    // views
    private List<ImageView> colorButtons;
    private ConstraintLayout sizeButton;
    private boolean isSizeOpen;
    private boolean isColorOpen;



    // after pairing, jump to chat with firebase
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private CustomWebSocket client;
    private CustomWebSocket mClient;
    // uuid for image transfer to ccyy.xyz
    private String uuid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        // request read and write permission
        requestStoragePermission();

        // get instance for firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        client = CustomWebSocket.getInstance();


        // view related
        sizeButton = findViewById(R.id.size_buttons);

        colorButtons = new ArrayList<>();
        colorButtons.add(findViewById(R.id.blue_button));
        colorButtons.add(findViewById(R.id.green_button));
        colorButtons.add(findViewById(R.id.orange_button));
        colorButtons.add(findViewById(R.id.pink_button));

        isSizeOpen = true;
        isColorOpen = true;
    }

    /**
     * change paint color
     *
     * @param view to click
     */
    public void selectColors(View view) {
        DrawView drawView = findViewById(R.id.draw_view);
        if (view.getId() == R.id.blue_button) {
            drawView.changeColor(getResources().getColor(R.color.blue));
        } else if (view.getId() == R.id.green_button) {
            drawView.changeColor(getResources().getColor(R.color.green));
        } else if (view.getId() == R.id.orange_button) {
            drawView.changeColor(getResources().getColor(R.color.orange));
        } else if (view.getId() == R.id.pink_button) {
            drawView.changeColor(getResources().getColor(R.color.pink));
        }
        showColors(view);
    }

    /**
     * show or hide color panel
     *
     * @param view to show or hide
     */
    public void showColors(View view) {
        if (isColorOpen) {
            for (int i = 0; i < colorButtons.size(); i++) {
                ImageView colorButton = colorButtons.get(i);
                colorButton.animate().translationYBy(-170f * (i + 1))
                        .setDuration(300)
                        .setInterpolator(new DecelerateInterpolator())
                        .start();
            }
        } else {
            for (int i = 0; i < colorButtons.size(); i++) {
                ImageView colorButton = colorButtons.get(i);
                colorButton.animate().translationYBy(170f * (i + 1))
                        .setDuration(300)
                        .setInterpolator(new DecelerateInterpolator())
                        .start();
            }
        }
        isColorOpen = !isColorOpen;
    }

    /**
     * change paint size
     *
     * @param view size view
     */
    public void selectSizes(View view) {
        DrawView drawView = findViewById(R.id.draw_view);
        if (view.getId() == R.id.s_button) {
            drawView.changeSize(10f);
        } else if (view.getId() == R.id.m_button) {
            drawView.changeSize(15f);
        } else if (view.getId() == R.id.l_button) {
            drawView.changeSize(20f);
        } else if (view.getId() == R.id.x_button) {
            drawView.changeSize(25f);
        }
        if (sizeButton.getVisibility() == View.VISIBLE) {
            showSizes(view);
        }
    }

    /**
     * show or hide size panel
     *
     * @param view to show or hide
     */
    public void showSizes(View view) {
        if (isSizeOpen) {
            sizeButton.setVisibility(View.VISIBLE);
            TranslateAnimation anim = new TranslateAnimation(
                    Animation.ABSOLUTE, 0f,
                    Animation.ABSOLUTE, 0f,
                    Animation.RELATIVE_TO_SELF, 1f,
                    Animation.RELATIVE_TO_SELF, 0f
            );
            anim.setDuration(300);
            anim.setFillAfter(true);
            anim.setInterpolator(new DecelerateInterpolator());

            sizeButton.startAnimation(anim);
        } else {
            sizeButton.setVisibility(View.GONE);
            TranslateAnimation anim = new TranslateAnimation(
                    Animation.ABSOLUTE, 0f,
                    Animation.ABSOLUTE, 0f,
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 1f
            );
            anim.setDuration(300);
            anim.setFillAfter(true);
            anim.setInterpolator(new DecelerateInterpolator());

            sizeButton.startAnimation(anim);

        }
        isSizeOpen = !isSizeOpen;
    }

    /**
     * erase lines
     *
     * @param view the erase view
     */
    public void erase(View view) {
        DrawView drawView = findViewById(R.id.draw_view);
        drawView.erase();
    }

    /**
     * undo a line
     *
     * @param view the undo view
     */
    public void undo(View view) {
        DrawView drawView = findViewById(R.id.draw_view);
        drawView.undo();
    }

    /**
     * save image to media with new uuid
     *
     * @param view
     */
    public void saveImage(View view) {
        DrawView drawView = findViewById(R.id.draw_view);
        drawView.setDrawingCacheEnabled(true);
        uuid = UUID.randomUUID().toString();
        String imageSaved = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                drawView.createBitmap(),
                uuid + ".png",
                "myPainting");

        if (imageSaved != null) {
            Toast.makeText(getApplicationContext(), "ImageSaved", Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                // send image to ccyy.xyz
                public void run() {
                    sendImage(uuid, imageSaved);
                }
            }, 1000);

        } else {
            Toast.makeText(getApplicationContext(), "NoImageSaved", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * send image to ccyy.xyz
     *
     * @param uuid       the uuid of the image
     * @param imageSaved the real image
     */
    public void sendImage(String uuid, String imageSaved) {

        try {
            // debug prints
//            System.out.println("++++++++++++++++++++++++++++++++++++++++++");
//            System.out.println(uuid + ".png");
//            System.out.println("++++++++++++++++++++++++++++++++++++++++++");
//            System.out.println(imageSaved);

            // use https multipart to send png files
            new MultipartUploadRequest(this, uuid, "https://ccyy.xyz/api/v2/post/" + uuid)
                    // get file from temp
                    .addFileToUpload(Environment.getExternalStorageDirectory() + "/temp.png", "file")
                    // POST to server
                    .setMethod("POST")
                    // listen on progress
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {
                            Toast.makeText(context, "Transmission in progress", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, Exception exception) {
                            Toast.makeText(context, "Transmission failed", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            Toast.makeText(context, "Transmission success", Toast.LENGTH_SHORT).show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                // after image successfully send to ccyy.xyz using https
                                // tell server to predict my paint and pair
                                public void run() {
                                    tellServer();
                                }
                            }, 1000);
                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            Toast.makeText(context, "Transmission cancel", Toast.LENGTH_SHORT).show();
                        }
                    })
                    // start upload()
                    .startUpload();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * request permission for read and store
     */
    private void requestStoragePermission() {
        // only require this after android M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }

    }

    /**
     * debug print for child threads, need to print on ui threads
     *
     * @param message the message to print
     */
    private void print(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textResult = findViewById(R.id.text_result);
                textResult.setText(textResult.getText().toString() + "\n" + message);
            }
        });
    }

    /**
     * Websocket listener
     */
    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int CLOSE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            // send email and uid and image uuid in json string to ccyy.xyz, to further predict and pair
            webSocket.send(String.format("{\"Proto\":1,\"Proto1\":5,\"PlayerName\":\"%s\",\"PlayerId\":\"%s\",\"Img\":\"%s\"}",
                    user.getEmail(),
                    user.getUid(),
                    uuid + ".png"));
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onMessage(WebSocket webSocket, String message) {
            // if reply coming back from websocket, decode it and parse it
            // extract user and userid for chat
            Base64.Decoder decoder = Base64.getMimeDecoder();
            try {
                String jsonString = new String(decoder.decode(message), "UTF-8");
                JSONObject json = new JSONObject(jsonString);
                String playerName = json.getString("PlayerName");
                String playerId = json.getString("PlayerId");
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ChatBoxActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("user", playerName);
                intent.putExtra("userId", playerId);
                startActivity(intent);

            } catch (UnsupportedEncodingException | JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            print("Receive Bytes : " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(CLOSE_STATUS, null);
            print("Closing Socket : " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable throwable, Response response) {
            print("Error : " + throwable.toString());
        }
    }

    /**
     * after sending image to server, tell server to predict and pair
     */
    private void tellServer() {
        // build the request
        // inject a fake header to pass ccyy.xyz server's nginx checking, otherwise receive 403 forbidden :(
        // use wss (ws with ssl) for security!
        Request request = new Request.Builder().url("wss://ccyy.xyz/api/v3/")
                .removeHeader("User-Agent")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Connection", "Upgrade")
                .addHeader("Host", "ccyy.xyz")
                .addHeader("Origin", "https://ccyy.xyz")
                .addHeader("Pragma", "no-cache")
                .addHeader("Upgrade", "websocket")
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36")
                .build();

        // set up my websocket request listener
        EchoWebSocketListener listener = new EchoWebSocketListener();
        // init websocket
        WebSocket webSocket = client.getClient().newWebSocket(request, listener);
//        print("Headers: " + request.headers().toString());
        // execute my request
        client.getClient().dispatcher().executorService();//.shutdown();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if(requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
//
//        }
//    }
}