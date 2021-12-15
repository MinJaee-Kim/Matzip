package kr.ac.uc.matzip.view;

import static android.content.Context.LOCATION_SERVICE;
import static net.daum.mf.map.api.MapPoint.mapPointWithGeoCoord;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

public class MapFragment extends Fragment implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener, MapView.POIItemEventListener {
    private View view;

    private static final String LOG_TAG = "MapFragment";
    private static final String TAG = "뷰페이저";

    public static final int SEARCH_REQUEST_CODE = 5;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;

    private MapView mapView;
    private ViewGroup mapViewContainer;
    private Button btnFragment, locationBtn;
    private EditText searchEt;

    private BottomBoardFragment bottomBoardFragment;
    private FusedLocationProviderClient fusedLocationClient;
    private ArrayList<LocationModel> arrayList;
    private ArrayList<MapPoint> mapArrayList;
    private LocationManager locationManager;

    private double latitude;
    private double longitude;

    String[] REQUIRED_PERMISSIONS  = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static MapFragment newInstance() {
        MapFragment mapFragment = new MapFragment();
        return mapFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_gallery, container, false);

        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        Bundle bundle = getArguments();
        if (bundle != null) {
            double latitude = bundle.getDouble("Latitude"); //Name 받기.
            double longitude = bundle.getDouble("Longitude");

            Log.d(TAG, "onCreateView: 좌표" + latitude + longitude);
        }

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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        if (!(ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                // 위치정보 설정 Intent
//                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(@NonNull Location location) {
                            // Got last known location. In some rare situations this can be null.
                            // Logic to handle location object
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            //맵포인트값
                            MapPoint mapPoint = mapPointWithGeoCoord(latitude, longitude);
                            Log.d(TAG, "onCreate: 위치" + latitude + longitude);

                            //맵 이동
                            mapView.setMapCenterPoint(mapPoint, true);
                        }
                    });
            }
        }

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Permission.hasPermissions(getContext(), REQUIRED_PERMISSIONS)) {
                    if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        if (mapView.getCurrentLocationTrackingMode().equals(MapView.CurrentLocationTrackingMode.TrackingModeOff)) {
                            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                        } else if (mapView.getCurrentLocationTrackingMode().equals(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading)) {
                            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
                        } else if (mapView.getCurrentLocationTrackingMode().equals(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading)) {
                            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                            mapView.setShowCurrentLocationMarker(false);
                            mapView.setShowCurrentLocationMarker(false);
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), "GPS를 설정해주세요.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
//        searchEt.addTextChangedListener();
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크합니다.
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if (check_result) {
                Log.d("@@@", "start");
                //위치 값을 가져올 수 있음

            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있다
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])) {
                    Toast.makeText(getActivity(), "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {
            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            // 3.  위치 값을 가져올 수 있음

        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.
            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])) {
                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(getActivity(), "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    public void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:
                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus(getActivity())) {
                    if (checkLocationServicesStatus(getActivity())) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }
                break;
            case MapSearchAdapter.RESULT_SEARCH:
                if(data == null){   // 검색 요소를 누르지 않은경우
                    Toast.makeText(getContext(), "위치를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                } else {
                    latitude = data.getDoubleExtra("위도", 0);
                    longitude = data.getDoubleExtra("경도", 0);
                    String mapAddress = data.getStringExtra("위치");
                    searchEt.setText(mapAddress);
                    Log.d(TAG, "onActivityResult: " + mapAddress + latitude + longitude);

                    MapPoint searchMapPoint = mapPointWithGeoCoord(latitude, longitude);
                    mapView.setMapCenterPoint(searchMapPoint, true);
//                bo_address.setText(mapAddress);
                }
                break;
        }
    }

    public boolean checkLocationServicesStatus(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
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

        Log.d(TAG, "onResume: d");

        if(getArguments() != null){
            //맵포인트값
            Log.d(TAG, "onResume: " + getArguments().getDouble(BoardListAdapter.LIST_LATITUDE));
            MapPoint takeMapPoint = mapPointWithGeoCoord(getArguments().getDouble(BoardListAdapter.LIST_LATITUDE),
                    getArguments().getDouble(BoardListAdapter.LIST_LONGITUDE));

            //맵 이동
            if(getArguments().getDouble(BoardListAdapter.LIST_LATITUDE) != 0) {
                mapView.setMapCenterPoint(takeMapPoint, true);
            }

            getArguments().clear();
        }

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
        mapView.removeAllPOIItems();
        //맵 끝
        Double leftlatitude = mapView.getMapPointBounds().bottomLeft.getMapPointGeoCoord().latitude;
        Double leftlongitude = mapView.getMapPointBounds().bottomLeft.getMapPointGeoCoord().longitude;
        Double rightlatitude = mapView.getMapPointBounds().topRight.getMapPointGeoCoord().latitude;
        Double rightlongitude = mapView.getMapPointBounds().topRight.getMapPointGeoCoord().longitude;

        GetLocationList(leftlatitude, leftlongitude, rightlatitude, rightlongitude);
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
        LocationAPI locationAPI = ApiClient.getNoHeaderApiClient().create(LocationAPI.class);
        locationAPI.getLocationBoard(leftlatitude, leftlongitude, rightlatitude, rightlongitude).enqueue(new Callback<List<LocationModel>>() {
            @Override
            public void onResponse(Call<List<LocationModel>> call, @NonNull Response<List<LocationModel>> response) {
                List<LocationModel> locationList = response.body();

                arrayList = new ArrayList<>();

                for(int i = 0; i < locationList.size(); ++i)
                {
                    Log.d(ContentValues.TAG, "onResponse: " + locationList.get(i).getLatitude());
                    arrayList.add(locationList.get(i));

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