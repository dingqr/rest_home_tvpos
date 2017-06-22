package com.yonyou.framework.library.http.request;

import com.yonyou.framework.library.http.parser.DataParser;
import com.yonyou.framework.library.http.parser.impl.FileParser;
import com.yonyou.framework.library.http.request.param.HttpParamModel;
import com.yonyou.framework.library.http.request.param.NonHttpParam;

import java.io.File;

/**
 * 作者：addison on 15/12/15 19:23
 * 邮箱：lsf@yonyou.com
 */
public class FileRequest extends AbstractRequest<File> {

    @NonHttpParam
    private File saveToFile;

    public FileRequest(String url) {
        super(url);
    }

    public FileRequest(HttpParamModel model, String savaToPath) {
        super(model);
        setFileSavePath(savaToPath);
    }

    public FileRequest(HttpParamModel model, File saveToFile) {
        super(model);
        this.saveToFile = saveToFile;
    }

    public FileRequest(String url, File saveToFile) {
        super(url);
        this.saveToFile = saveToFile;
    }

    public FileRequest(String url, String savaToPath) {
        super(url);
        setFileSavePath(savaToPath);
    }

    public FileRequest setFileSavePath(String savaToPath) {
        if (savaToPath != null) {
            saveToFile = new File(savaToPath);
        }
        return this;
    }

    public File getCachedFile() {
        return saveToFile != null ? saveToFile : super.getCachedFile();
    }

    @Override
    public DataParser<File> createDataParser() {
        return new FileParser(saveToFile);
    }
}

