package com.huburt.app.ijkplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.MediaController;

/**
 * Created by hubert
 * <p>
 * Created on 2017/12/5.
 */

public class IjkMediaController extends MediaController implements IMediaController {

    public IjkMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IjkMediaController(Context context, boolean useFastForward) {
        super(context, useFastForward);
    }

    public IjkMediaController(Context context) {
        super(context);
    }

}
