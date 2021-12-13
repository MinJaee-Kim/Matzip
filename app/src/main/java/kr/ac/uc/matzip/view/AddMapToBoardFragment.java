package kr.ac.uc.matzip.view;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static net.daum.mf.map.api.MapPoint.mapPointWithGeoCoord;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import kr.ac.uc.matzip.R;

public class AddMapToBoardFragment extends Fragment implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener, MapView.POIItemEventListener {
    private View view;
    private static final String LOG_TAG = "MapFragment";
    public static final String ADDRESS_VALUE = "AddressValue";
    public static final String ADDRESS_LATITUDE = "LATITUDE";
    public static final String ADDRESS_LONGITUDE = "LONGITUDE";
    private MapView mapView;
    private ViewGroup mapViewContainer;
    private Button btnFragment, locationBtn;
    private double latitude;
    private double longitude;
    private EditText mb_locationEt;
    private BottomSheetFragment bottomSheetFragment;
    Bundle bundle = new Bundle(3); // 파라미터의 숫자는 전달하려는 값의 갯수

    private MapPoint makerPoint;
    private FusedLocationProviderClient fusedLocationClient;    //위치 정보 가져오기

    public static AddMapToBoardFragment newInstance() {
        AddMapToBoardFragment addMapToBoardFragment = new AddMapToBoardFragment();
        return addMapToBoardFragment;
    }


    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.map_to_board, container, false);


        btnFragment = view.findViewById(R.id.mb_checkBtn);
        mb_locationEt = view.findViewById(R.id.mb_locationEt);

        locationBtn = view.findViewById(R.id.mb_locationBtn);

        bottomSheetFragment = new BottomSheetFragment(getActivity());


        btnFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            }
        });


//        mBehavior.getHalfExpandedRatio();


        //위치값 가져오기
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return view;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            //맵포인트값
                            MapPoint mapPoint = mapPointWithGeoCoord(latitude, longitude);


                            //맵 이동
                            mapView.setMapCenterPoint(mapPoint, true);
                            Log.d(TAG, "onCreate: 위치" + latitude + longitude);
                        }
                    }
                });

        Log.d(TAG, "onCreate: " + latitude + longitude);

        Log.d(TAG, "onCreateView: "+mapView);

        //나침반 off
//        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mapView.getCurrentLocationTrackingMode().equals(MapView.CurrentLocationTrackingMode.TrackingModeOff)){
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                } else if (mapView.getCurrentLocationTrackingMode().equals(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading)) {
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
                } else if (mapView.getCurrentLocationTrackingMode().equals(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading)){
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                    mapView.setShowCurrentLocationMarker(false);
                }
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        //지도를 띄우자
        // java code
        mapView = new MapView(getActivity());
        mapViewContainer = (ViewGroup) view.findViewById(R.id.mb_map_view);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        Log.d(TAG, "onResume: " + mapView.getCurrentLocationTrackingMode());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapViewContainer.removeAllViews();
    }


    //현 위치 좌표값
    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        //맵 중심 좌표
        makerPoint = mapView.getMapCenterPoint();
        Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
    }
    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {
    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {
    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {
    }


    private void onFinishReverseGeoCoding(String result) {
//        Toast.makeText(LocationDemoActivity.this, "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
    }



    //MapView가 사용가능 한 상태가 되었음을 알려준다.
    //onMapViewInitialized()가 호출된 이후에 MapView 객체가 제공하는 지도 조작 API들을 사용할 수 있다.
    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    //지도 중심 좌표가 이동한 경우 호출된다.
    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    //지도 확대/축소 레벨이 변경된 경우 호출된다.
    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }



    //사용자가 지도 위를 터치한 경우 호출된다.
    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    //사용자가 지도 위 한 지점을 더블 터치한 경우 호출된다.
    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    //사용자가 지도 위 한 지점을 길게 누른 경우(long press) 호출된다.
    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    //사용자가 지도 드래그를 시작한 경우 호출된다.
    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
    }

    //사용자가 지도 드래그를 끝낸 경우 호출된다.
    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    //지도의 이동이 완료된 경우 호출된다.
    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        makerPoint = mapView.getMapCenterPoint();

        MapPOIItem marker = new MapPOIItem();
        mapView.removeAllPOIItems();
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(makerPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker);

        //맵 어드레스 가져오기(주소 뿌리기)
        MapReverseGeoCoder reverseGeoCoder = new MapReverseGeoCoder("97f0f22108c0de55be514b904e4ec2e8", makerPoint, new MapReverseGeoCoder.ReverseGeoCodingResultListener() {
            @Override
            public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
                //주소를 찾은경우
                Log.d(TAG, "onReverseGeoCoderFoundAddress: 주소 성공" + s);
                mb_locationEt.setText(s);
                bundle.putString(ADDRESS_VALUE, String.valueOf(mb_locationEt.getText()));
                bundle.putDouble(ADDRESS_LATITUDE, makerPoint.getMapPointGeoCoord().latitude);
                bundle.putDouble(ADDRESS_LONGITUDE, makerPoint.getMapPointGeoCoord().longitude);

            }

            @Override
            public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
                //호출 실패한 경우
                Log.d(TAG, "onReverseGeoCoderFailedToFindAddress: 주소 실패");
            }
        }, getActivity());

        bottomSheetFragment.setArguments(bundle);

        reverseGeoCoder.startFindingAddress();
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }



}
