package com.example.alldocunemtreader.constants;

/**
 * Author: Eccentric
 * Created on 2024/6/24 09:41.
 * Description: 请求码
 */
public class RequestCodeConstants {
    //Permissions
    public static final Integer REQUEST_WRITE_EXTERNAL_STORAGE = 1001;

    //EventBus
    public static final Integer REQUEST_SCAN_FINISHED = 2001;
    public static final Integer REQUEST_REFRESH = 2002;
    public static final Integer REQUEST_REFRESH_RECENT = 2003;
    public static final Integer REQUEST_NO_RESULTS = 2004;
    public static final Integer REQUEST_HAS_RESULTS = 2005;
    public static final Integer REQUEST_SCAN_FILE_FINISHED = 2006;


    //Base相关
    public final static Integer MEDIASTORE_FILENAME_UPDATE_FAILED = 3001;
    public final static Integer NEW_FILE_NAME_CAN_NOT_NULL = 3002;
    public final static Integer DATABASE_FILENAME_UPDATE_SUCCESS = 3003;

    //Intent
    public static final int REQUEST_GO_RECENT_DOCUMENT = 4001;
}
