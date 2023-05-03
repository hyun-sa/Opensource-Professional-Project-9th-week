/*
* 프로젝트명 : 구글 지도 활용 앱 만들기
* 작성자 : 2019038004 조민우
* 작성일 : 2023. 5. 3.
* 프로그램 설명 : 구글 지도 활용
 */


package com.hyunsa.currentusing


import android.R
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    var gMap: GoogleMap? = null
    var mapFrag: MapFragment? = null
    var videoMark: GroundOverlayOptions? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Google 지도 활용 (수정)"
        mapFrag = fragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFrag!!.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        gMap = map
        gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE)
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.568256, 126.897240), 15))
        gMap.getUiSettings().setZoomControlsEnabled(true)
        gMap.setOnMapClickListener(object : GoogleMap.OnMapClickListener() {
            override fun onMapClick(point: LatLng?) {
                videoMark = GroundOverlayOptions().image(
                    BitmapDescriptorFactory
                        .fromResource(R.drawable.rate_star_small_on)
                )
                    .position(point, 100f, 100f)
                gMap.addGroundOverlay(videoMark)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menu.add(0, 1, 0, "위성 지도")
        menu.add(0, 2, 0, "일반 지도")
        val sMenu = menu.addSubMenu("유명장소 바로가기 >>")
        sMenu.add(0, 3, 0, "정동진")
        sMenu.add(0, 4, 0, "해운대")
        sMenu.add(0, 5, 0, "땅끝마을")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            1 -> {
                gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID)
                return true
            }
            2 -> {
                gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL)
                return true
            }
            3 -> {
                gMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            37.689254, 129.033051
                        ), 15
                    )
                )
                return true
            }
            4 -> {
                gMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            35.159003, 129.161882
                        ), 15
                    )
                )
                return true
            }
            5 -> {
                gMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            34.301472, 126.524048
                        ), 15
                    )
                )
                return true
            }
        }
        return false
    }
}
