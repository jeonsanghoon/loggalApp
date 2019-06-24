package com.altsoft.model.UserInfo;

import com.altsoft.model.DEVICE_LOCATION;
import com.altsoft.model.device.T_DEVICE_STATION;
import com.altsoft.model.signage.MOBILE_SIGNAGE_LIST;

import java.util.List;

public class DataList {
    public List<DEVICE_LOCATION> devicelist;
    public List<T_DEVICE_STATION> stationlist;
    public List<MOBILE_SIGNAGE_LIST> signagelist;
    public Boolean BANNER_BOOKMARK_YN;
    public Boolean LOCALBOX_BOOKMARK_YN;

    public Boolean BANNER_FAVORITE_YN;
    public Boolean LOCALBOX_FAVORITE_YN;

}
