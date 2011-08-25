package kr.android.hellodrinking.map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.nhn.android.maps.NMapView;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;

public class MapContainerView extends ViewGroup {
	private NMapOverlayManager mOverlayManager;
	
	public MapContainerView(Context context) {
		super(context);
	}

	public MapContainerView(Context context, AttributeSet att) {
		super(context, att);
	}
	
	public void setInit(NMapOverlayManager overlayManager){
		mOverlayManager = overlayManager;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		if(mOverlayManager == null)
			throw new RuntimeException("Not Init! : Surly setInit(NMapOverlayManager)");
		final int width = getWidth();
		final int height = getHeight();
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View view = getChildAt(i);
			final int childWidth = view.getMeasuredWidth();
			final int childHeight = view.getMeasuredHeight();
			final int childLeft = (width - childWidth) / 2;
			final int childTop = (height - childHeight) / 2;
			view.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
		}
		if (changed)
			mOverlayManager.onSizeChanged(width, height);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int w = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
		int h = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
		int sizeSpecWidth = widthMeasureSpec;
		int sizeSpecHeight = heightMeasureSpec;

		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View view = getChildAt(i);
			if (view instanceof NMapView) {
				if (((NMapView)view).isAutoRotateEnabled()) {
					int diag = (((int) (Math.sqrt(w * w + h * h)) + 1) / 2 * 2);
					sizeSpecWidth = MeasureSpec.makeMeasureSpec(diag, MeasureSpec.EXACTLY);
					sizeSpecHeight = sizeSpecWidth;
				}
			}
			view.measure(sizeSpecWidth, sizeSpecHeight);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
