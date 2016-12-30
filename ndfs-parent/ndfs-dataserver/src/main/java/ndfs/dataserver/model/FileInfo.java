
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.dataserver.model;

import ndfs.core.common.ToString;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年8月1日 下午2:13:06
 */
public class FileInfo extends ToString {
    public FileInfo(int fileId, long offset, long length) {
        super();
        this.fileId = fileId;
        this.offset = offset;
        this.length = length;
    }

    public int getFileId() {

        return fileId;
    }

    public void setFileId(int fileId) {

        this.fileId = fileId;
    }

    public long getOffset() {

        return offset;
    }

    public void setOffset(long offset) {

        this.offset = offset;
    }

    public long getLength() {

        return length;
    }

    public void setLength(long length) {

        this.length = length;
    }

    private int fileId;
    private long offset;
    private long length;
}
