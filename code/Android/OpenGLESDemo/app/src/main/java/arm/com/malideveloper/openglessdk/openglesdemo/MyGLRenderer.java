package arm.com.malideveloper.openglessdk.openglesdemo;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * author: pengyuyan@baidu.com
 * created on: 2020-01-21
 * description:
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    // 为什么这些方法会有一个GL10的参数。这是因为这些方法的签名（Method Signature）在2.0接口中
    // 被简单地重用了，以此来保持Android框架的代码尽量简单。

    // 调用一次，用来配置View的OpenGL ES环境。
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }


    // 每次重新绘制View时被调用。
    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

        GLES20.glViewport(0,0, i, i1);
    }

    // 如果View的几何形态发生变化时会被调用，例如当设备的屏幕方向发生改变时。
    @Override
    public void onDrawFrame(GL10 gl10) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }
}
