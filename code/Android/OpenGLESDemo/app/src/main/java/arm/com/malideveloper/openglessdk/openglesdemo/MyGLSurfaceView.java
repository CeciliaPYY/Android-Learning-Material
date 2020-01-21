package arm.com.malideveloper.openglessdk.openglesdemo;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * author: pengyuyan@baidu.com
 * created on: 2020-01-21
 * description:
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer myGLRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

        myGLRenderer = new MyGLRenderer();

        setRenderer(myGLRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
