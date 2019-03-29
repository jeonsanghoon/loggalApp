package com.altsoft.model;

import com.altsoft.model.category.CATEGORY_LIST;

import java.io.Serializable;
import java.util.List;

public class SEARCH_INFO  implements Serializable {
    public List<CATEGORY_LIST> categoryList;
    public List<MOBILE_AD_DETAIL_DATA>  mobileAdList;
    public List<DEVICE_LOCATION> localboxList;
}
