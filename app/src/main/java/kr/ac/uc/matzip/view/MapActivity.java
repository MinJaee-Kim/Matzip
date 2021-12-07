package kr.ac.uc.matzip.view;

import static net.daum.mf.map.api.MapPoint.mapPointWithGeoCoord;

import android.content.ContentValues;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.LocationModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.LocationAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends Fragment implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener, MapView.POIItemEventListener {
    private View view;
    private static final String LOG_TAG = "MapActivity";
    public static final int SEARCH_REQUEST_CODE = "5";
    private MapView mapView;
    private ViewGroup mapViewContainer;
    private Button btnFragment, locationBtn;
    private EditText searchEt;
    private double latitude;
    private double longitude;
    private static final String TAG = "뷰페이저";
    private BottomBoardFragment bottomBoardFragment;

    private FusedLocationProviderClient fusedLocationClient;

    private ArrayList<LocationModel> arrayList;
    private ArrayList<MapPoint> mapArrayList;

    public static MapActivity newInstance() {
        MapActivity mapActivity = new MapActivity();
        return mapActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        view = inflater.inflate(R.layout.map_gallery, container, false);

        locationBtn = view.findViewById(R.id.mg_locationBtn);
        searchEt = view.findViewById(R.id.mg_locationEt);

        searchEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapSearchActivity.class);
                startActivityForResult(intent, SEARCH_REQUEST_CODE);
            }
        });

        //위치값 가져오기
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

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

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mapView.getCurrentLocationTrackingMode().equals(MapView.CurrentLocationTrackingMode.TrackingModeOff)){
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                } else if (mapView.getCurrentLocationTrackingMode().equals(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading)) {
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
                } else if (mapView.getCurrentLocationTrackingMode().equals(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading)){
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                }
            }
        });

//        searchEt.addTextChangedListener();




        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SEARCH_REQUEST_CODE) {
            if(data == null){   // 검색 요소를 누르지 않은경우
                Toast.makeText(getContext(), "위치를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
            } else {
                latitude = data.getDoubleExtra("위도", 0);
                longitude = data.getDoubleExtra("경도", 0);
                String mapAddress = data.getStringExtra("위치");
                Log.d(TAG, "onActivityResult: " + mapAddress);
//                bo_address.setText(mapAddress);
            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();

        mapView = new MapView(getActivity());

        mapViewContainer = (ViewGroup) view.findViewById(R.id.mg_map_view);
        mapViewContainer.addView(mapView);

        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        mapViewContainer.removeAllViews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapViewContainer.removeAllViews();
    }


    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
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
        mapView.removeAllPOIItems();
        //맵 끝
        Double leftlatitude = mapView.getMapPointBounds().bottomLeft.getMapPointGeoCoord().latitude;
        Double leftlongitude = mapView.getMapPointBounds().bottomLeft.getMapPointGeoCoord().longitude;
        Double rightlatitude = mapView.getMapPointBounds().topRight.getMapPointGeoCoord().latitude;
        Double rightlongitude = mapView.getMapPointBounds().topRight.getMapPointGeoCoord().longitude;
        GetLocationList(leftlatitude, leftlongitude, rightlatitude, rightlongitude);
    }
    //마커 클릭시 호출
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        bottomBoardFragment = new BottomBoardFragment(getActivity(), mapPOIItem.getTag());
        bottomBoardFragment.show(getChildFragmentManager(), bottomBoardFragment.getTag());
        Log.d(TAG, "onPOIItemSelected: " + mapPOIItem.getTag());
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

    private void GetLocationList(Double leftlatitude, Double leftlongitude, Double rightlatitude, Double rightlongitude) {
        LocationAPI locationAPI = ApiClient.getApiClient().create(LocationAPI.class);
        locationAPI.getLocationBoard(leftlatitude, leftlongitude, rightlatitude, rightlongitude).enqueue(new Callback<List<LocationModel>>() {
            @Override
            public void onResponse(Call<List<LocationModel>> call, Response<List<LocationModel>> response) {
                List<LocationModel> locationList = response.body();


                arrayList = new ArrayList<>();

                for(int i = 0; i < locationList.size(); ++i)
                {
                    Log.d(ContentValues.TAG, "onResponse: " + locationList.get(i).getLatitude());
                    arrayList.add(locationList.get(i));

                    Log.d(TAG, "onResume: ");
                    MapPOIItem marker = new MapPOIItem();
                    marker.setItemName("Default Marker");
                    marker.setTag(locationList.get(i).getBoard_id());
                    marker.setMapPoint(mapPointWithGeoCoord(arrayList.get(i).getLatitude(), arrayList.get(i).getLongitude()));
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                    marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                    mapView.addPOIItem(marker);

                }
            }
            @Override
            public void onFailure(Call<List<LocationModel>> call, Throwable t) {
                Log.e(ContentValues.TAG, "Set Board onFailure: " + t.getMessage());
            }
        });
    }




}
