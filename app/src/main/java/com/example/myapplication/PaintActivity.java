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
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.chat.ChatBoxActivity;
import com.example.myapplication.paint.DrawView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
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

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;


    private List<ImageView> colorButtons;

    private ConstraintLayout sizeButton;
    private boolean isSizeOpen;
    private boolean isColorOpen;

    private OkHttpClient mClient;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String uuid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        requestStoragePermission();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mClient = new OkHttpClient.Builder()
                .readTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .connectTimeout(600, TimeUnit.SECONDS)
                .build();

        sizeButton = findViewById(R.id.size_buttons);

        colorButtons = new ArrayList<>();
        colorButtons.add(findViewById(R.id.blue_button));
        colorButtons.add(findViewById(R.id.green_button));
        colorButtons.add(findViewById(R.id.orange_button));
        colorButtons.add(findViewById(R.id.pink_button));

        isSizeOpen = true;
        isColorOpen = true;
    }

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

    public void erase(View view) {
        DrawView drawView = findViewById(R.id.draw_view);
        drawView.erase();
    }

    public void undo(View view) {
        DrawView drawView = findViewById(R.id.draw_view);
        drawView.undo();
    }

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
                public void run() {
                    sendImage(uuid, imageSaved);
                }
            }, 1000);

        } else {
            Toast.makeText(getApplicationContext(), "NoImageSaved", Toast.LENGTH_SHORT).show();
        }
    }


    public void sendImage(String uuid, String imageSaved) {

        try {
            System.out.println("++++++++++++++++++++++++++++++++++++++++++");
            System.out.println(uuid + ".png");
            System.out.println("++++++++++++++++++++++++++++++++++++++++++");

            System.out.println(imageSaved);

            new MultipartUploadRequest(this, uuid, "https://ccyy.xyz/api/v2/post/" + uuid)
                    .addFileToUpload(Environment.getExternalStorageDirectory() + "/temp.png", "file")
//                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMethod("POST")
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {
                            Toast.makeText(context, "Transmission in progress", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, Exception exception) {
                            Toast.makeText(context, "Transmission failed", Toast.LENGTH_SHORT).show();
                            exception.printStackTrace();
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            Toast.makeText(context, "Transmission success", Toast.LENGTH_SHORT).show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    tellServer(uuid);
                                }
                            }, 1000);
                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            Toast.makeText(context, "Transmission cancel", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .startUpload();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void requestStoragePermission() {
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

    private void print(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textResult = findViewById(R.id.text_result);
                textResult.setText(textResult.getText().toString() + "\n" + message);
            }
        });
    }

    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int CLOSE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocket.send(String.format("{\"Proto\":1,\"Proto1\":5,\"PlayerName\":\"%s\",\"PlayerId\":\"%s\",\"Img\":\"%s\"}",
                    user.getEmail(),
                    user.getUid(),
                    uuid + ".png"));
//            webSocket.send(ByteString.decodeHex("abcd"));
//            webSocket.close(CLOSE_STATUS, "Socket Closed !!");
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onMessage(WebSocket webSocket, String message) {
            Base64.Decoder decoder = Base64.getMimeDecoder();
            try {
//                print("Receive Message: " + message);
                String jsonString = new String(decoder.decode(message), "UTF-8");
                JSONObject json = new JSONObject(jsonString);
                String playerName = json.getString("PlayerName");
                String playerId = json.getString("PlayerId");
//                print("Receive Message: " + playerName);
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

    private void tellServer(String uuid) {
        Request request = new Request.Builder().url("wss://ccyy.xyz/api/v3/")
                .removeHeader("User-Agent")
//                .addHeader("User-Agent", WebSettings.getDefaultUserAgent(getApplicationContext()))
//                .addHeader("Accept-Encoding", "gzip, deflate")
//                .addHeader("Accept-Language", "en-US,en;q=0.9")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Connection", "Upgrade")
                .addHeader("Host", "ccyy.xyz")
                .addHeader("Origin", "https://ccyy.xyz")
                .addHeader("Pragma", "no-cache")
//                .addHeader("Sec-WebSocket-Extensions", "permessage-deflate; client_max_window_bits")
//                .addHeader("Sec-WebSocket-Key", "YOVtGgshqpKQuHJ+Q5ilaQ==")
//                .addHeader("Sec-WebSocket-Version","13")
                .addHeader("Upgrade", "websocket")
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36")
                .build();

        EchoWebSocketListener listener = new EchoWebSocketListener();
        WebSocket webSocket = mClient.newWebSocket(request, listener);
//        print("Headers: " + request.headers().toString());
        mClient.dispatcher().executorService();//.shutdown();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if(requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
//
//        }
//    }
}