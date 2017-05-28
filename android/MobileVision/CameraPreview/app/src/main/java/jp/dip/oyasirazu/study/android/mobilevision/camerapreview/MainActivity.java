package jp.dip.oyasirazu.study.android.mobilevision.camerapreview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;


public class MainActivity extends AppCompatActivity {

    private CameraSource mCameraSource;
    private SurfaceView mCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCameraView = new SurfaceView(getApplicationContext());

        // 何もしない Detector を作成
        Detector<Void> detector = new Detector<Void>() {
            @Override
            public SparseArray<Void> detect(Frame frame) {
                return null;
            }
        };

        // 何もしない Processor を作って detector にセット
        detector.setProcessor(new Detector.Processor<Void>() {
            @Override
            public void release() {}

            @Override
            public void receiveDetections(Detector.Detections<Void> detections) {}
        });

        // カメラソース作成
        mCameraSource = new CameraSource
                .Builder(this, detector)
                .setRequestedPreviewSize(640, 480)
                .setRequestedFps(20.0f)
                .setAutoFocusEnabled(true)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .build();

        // SurfaceView に SurfaceHolderCallback を設定。
        mCameraView.getHolder().addCallback(new SurfaceHolder.Callback(){
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mCameraSource.start(mCameraView.getHolder());
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
            {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder)
            {
                mCameraSource.stop();
            }
        });

        setContentView(mCameraView);
    }
}
