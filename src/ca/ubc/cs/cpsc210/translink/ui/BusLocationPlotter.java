package ca.ubc.cs.cpsc210.translink.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.util.Log;
import ca.ubc.cs.cpsc210.translink.R;
import ca.ubc.cs.cpsc210.translink.model.Bus;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.util.Geometry;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

// A plotter for bus locations
public class BusLocationPlotter extends MapViewOverlay {
    /** overlay used to display bus locations */
    private ItemizedIconOverlay<OverlayItem> busLocationsOverlay;

    /**
     * Constructor
     * @param context  the application context
     * @param mapView  the map view
     */
    public BusLocationPlotter(Context context, MapView mapView) {
        super(context, mapView);
        busLocationsOverlay = createBusLocnOverlay();
    }

    public ItemizedIconOverlay<OverlayItem> getBusLocationsOverlay() {
        return busLocationsOverlay;
    }

    /**
     * Plot buses serving selected stop
     */
    public void plotBuses() {
        // TODO: complete the implementation of this method (Task 10)
        busLocationsOverlay.removeAllItems();

        /*OverlayItem item = new OverlayItem("","",new GeoPoint(49.2606,-123.246));
        item.setMarker(Drawable.createFromPath("bus.png"));
        busLocationsOverlay.addItem(item);*/


        Stop selectedStop = StopManager.getInstance().getSelected();
        if (selectedStop!=null){
            // TODO: can plot a single point with geopoint (49.2606,-123.246), but cannot plot geopoints for buses!
            // TODO: the problem probably is, the size of buses is 0.

            for (Bus bus: selectedStop.getBuses()){

                GeoPoint pt = Geometry.gpFromLL(bus.getLatLon());
                String destination = bus.getDestination();
                String time = bus.getTime();
                OverlayItem item = new OverlayItem(destination,time,pt);

                //item.setMarker(Drawable.createFromPath("bus.png"));
                item.setMarker(context.getResources().getDrawable(R.drawable.bus));

                busLocationsOverlay.addItem(item);

            }
        }



    }

    /**
     * Create the overlay for bus markers.
     */
    private ItemizedIconOverlay<OverlayItem> createBusLocnOverlay() {
        ResourceProxy rp = new DefaultResourceProxyImpl(context);

        return new ItemizedIconOverlay<OverlayItem>(
                new ArrayList<OverlayItem>(),
                context.getResources().getDrawable(R.drawable.bus),
                null, rp);
    }
}
