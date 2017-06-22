package com.yonyou.framework.library.http.request;

import android.graphics.Bitmap;

import com.yonyou.framework.library.http.parser.DataParser;
import com.yonyou.framework.library.http.parser.impl.BitmapParser;
import com.yonyou.framework.library.http.request.param.HttpParamModel;
import com.yonyou.framework.library.http.request.param.NonHttpParam;

import java.io.File;

/**
 * 作者：addison on 15/12/15 19:14
 * 邮箱：lsf@yonyou.com
 */
public class BitmapRequest extends AbstractRequest<Bitmap> {

    @NonHttpParam
    protected File saveToFile;

    public BitmapRequest(HttpParamModel model) {
        super(model);
    }

    public BitmapRequest(HttpParamModel model, File saveToFile) {
        super(model);
        this.saveToFile = saveToFile;
    }

    public BitmapRequest(HttpParamModel model, String saveToPath) {
        super(model);
        setFileSavePath(saveToPath);
    }

    public BitmapRequest(String url) {
        super(url);
    }

    public BitmapRequest(String url, File saveToFile) {
        super(url);
        this.saveToFile = saveToFile;
    }

    public BitmapRequest(String url, String saveToPath) {
        super(url);
        setFileSavePath(saveToPath);
    }


    public BitmapRequest setFileSavePath(String savaToPath) {
        if (savaToPath != null) {
            saveToFile = new File(savaToPath);
        }
        return this;
    }

    public File getCachedFile() {
        return saveToFile != null ? saveToFile : super.getCachedFile();
    }

    @Override
    public DataParser<Bitmap> createDataParser() {
        return new BitmapParser(saveToFile);
    }
}

