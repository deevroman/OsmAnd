package net.osmand.plus.plugins.development.widget;

import static net.osmand.plus.views.mapwidgets.WidgetType.DEV_CAMERA_DISTANCE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.osmand.core.android.MapRendererView;
import net.osmand.plus.activities.MapActivity;
import net.osmand.plus.settings.enums.MetricsConstants;
import net.osmand.plus.utils.OsmAndFormatter;
import net.osmand.plus.views.OsmandMapTileView;
import net.osmand.plus.views.layers.base.OsmandMapLayer.DrawSettings;
import net.osmand.plus.views.mapwidgets.WidgetType;
import net.osmand.plus.views.mapwidgets.WidgetsPanel;
import net.osmand.plus.views.mapwidgets.widgets.SimpleWidget;

public class CameraDistanceWidget extends SimpleWidget {

	private final OsmandMapTileView mapView;
	private float cachedCameraDistance = -1;

	public CameraDistanceWidget(@NonNull MapActivity mapActivity, @Nullable String customId, @Nullable WidgetsPanel widgetsPanel) {
		super(mapActivity, WidgetType.DEV_CAMERA_DISTANCE, customId, widgetsPanel);
		this.mapView = mapActivity.getMapView();
		updateInfo(null);
		setIcons(DEV_CAMERA_DISTANCE);
	}

	@Override
	public void updateInfo(@Nullable DrawSettings drawSettings) {
		float cameraDistance = getCameraHeightInMeters();
		if (isUpdateNeeded() || cameraDistance != cachedCameraDistance) {
			cachedCameraDistance = cameraDistance;
			if (cameraDistance > 0) {
				MetricsConstants metricsConstants = settings.METRIC_SYSTEM.get();
				OsmAndFormatter.FormattedValue formattedDistance = OsmAndFormatter.getFormattedDistanceValue(cachedCameraDistance,
						app, false, metricsConstants);
				setText(formattedDistance.value, formattedDistance.unit);
			} else {
				setText(NO_VALUE, null);
			}
		}
	}

	private float getCameraHeightInMeters() {
		MapRendererView mapRenderer = mapView.getMapRenderer();
		if (mapRenderer != null) {
			return mapRenderer.getCameraHeightInMeters();
		}
		return 0;
	}

	private String formatDistance(float distanceInMeters) {
		return OsmAndFormatter.getFormattedDistance(distanceInMeters, app, true,
				MetricsConstants.KILOMETERS_AND_METERS);
	}
}