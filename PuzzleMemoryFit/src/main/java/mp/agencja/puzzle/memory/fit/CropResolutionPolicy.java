package mp.agencja.puzzle.memory.fit;

import android.view.View.MeasureSpec;

import org.andengine.engine.options.resolutionpolicy.BaseResolutionPolicy;


public class CropResolutionPolicy extends BaseResolutionPolicy {

	private final float desiredWidth;
	private final float desiredHeight;

	private float userWidth;
	private float userHeight; 

	private float left;
	private float right; 
	private float top;
	private float bottom;

	public CropResolutionPolicy(float pWidth, float pHeight) {
		desiredWidth = pWidth;
		desiredHeight = pHeight;
	}

	@Override
	public void onMeasure(Callback pResolutionPolicyCallback,
			int pWidthMeasureSpec, int pHeightMeasureSpec) {

		BaseResolutionPolicy.throwOnNotMeasureSpecEXACTLY(pWidthMeasureSpec,
				pHeightMeasureSpec);

		final int measuredWidth = MeasureSpec.getSize(pWidthMeasureSpec);
		final int measuredHeight = MeasureSpec.getSize(pHeightMeasureSpec);

		final float desiredRatio = desiredWidth / desiredHeight;
		float scaleRatio;

		float resultWidth;
		float resultHeight;

		if ((float) measuredWidth / (float) measuredHeight < desiredRatio) {
			resultWidth = measuredHeight * desiredRatio;
			resultHeight = measuredHeight;
			scaleRatio = desiredHeight / resultHeight;
		} else {
			resultHeight = measuredWidth / desiredRatio;
			resultWidth = measuredWidth;
			scaleRatio = desiredWidth / resultWidth;
		}

		userWidth = measuredWidth * scaleRatio;
		userHeight = measuredHeight * scaleRatio;

		left = (desiredWidth - userWidth) / 2f;
		right = userWidth + left;
		bottom = (desiredHeight - userHeight) / 2f;
		top = userHeight + bottom;

		pResolutionPolicyCallback.onResolutionChanged(Math.round(resultWidth), Math.round(resultHeight));

	}


	public float getUserWidth() {
		return userWidth;
	}

	public float getUserHeight() {
		return userHeight;
	}


	public float getLeft() {
		return left;
	}


	public float getRight() {
		return right;
	}


	public float getTop() {
		return top;
	}


	public float getBottom() {
		return bottom;
	}


}