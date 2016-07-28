package cse.knu.kr.deagu.beaconsecurityapp;

/**
 * Created by sujin on 2016. 7. 28..
 */

import android.app.Service;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BeaconActivity extends AppCompatActivity implements BeaconConsumer{

    private BeaconManager bm;//beacon manager
    TextView tv;
    private List<Beacon> beaconList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bm=BeaconManager.getInstanceForApplication(this);
        //tv=(TextView)findViewById(R.id.TextView);

        bm.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

        bm.bind(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        bm.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect(){
        bm.setRangeNotifier(new RangeNotifier() {

            //비콘 감지되면 해당 함수 호출됨
            //beacons에는 감지된 비콘 리스트
            //region에는 비콘들에 대응하는 Region객체가 들어옴

            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if(beacons.size()>0){
                    beaconList.clear();
                    for(Beacon beacon:beacons){
                        beaconList.add(beacon);
                    }
                }

            }
        });

        try{
            bm.startRangingBeaconsInRegion(new Region("myRangingUniqueId",null,null,null));
        }catch (RemoteException e){

        }
    }

    public void OnButtonClicked(View view){
        handler.sendEmptyMessage(0);
    }


    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            tv.setText("");

            for(Beacon beacon : beaconList){
                tv.append("ID : "+beacon.getId2()+" / "+"Distance : "+Double.parseDouble(String.format("%.3f",beacon.getDistance()))+"m/n");
            }
            handler.sendEmptyMessageDelayed(0,1000);
        }
    };
}
