package com.dong.nohttp;

import com.yolanda.nohttp.rest.Response;

/**
 * Created by 川东 on 2016/11/30.
 */

public interface HttpListener<T> {
    void onSucceed(int what, Response<T> response);

    void onFailed(int what, Response<T> response);
}
