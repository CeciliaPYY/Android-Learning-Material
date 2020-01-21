package arm.com.malideveloper.openglessdk.openglesdemo;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * author: pengyuyan@baidu.com
 * created on: 2020-01-21
 * description:
 */
public class OpenGLES20Activity extends Activity {
    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
    }
}
