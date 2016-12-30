
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.dataserver.model;

import static ndfs.core.common.StatusCodeEnum.FILE_OPEN_ERROR;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

import ndfs.core.cache.exception.CommonException;

/**
 * <p>
 *     Description: 
 * </p>
 * @author yibingsong
 * @Date 2016年7月19日 上午11:00:23
 */
public class BlockInfo {
    
    //块的唯一id
    private long id;
    
    //实际大小
    private long actualSize;
    
    //读文件句柄
    private RandomAccessFile readFile;
    
    //文件路径
    private String filePath;
   
    //是否写满
    private boolean isFull;
    
    //写文件句柄
    private RandomAccessFile writeFile;
    
    //当前文件id的最大值
    private int currentFileId;
    
    public void init(long id, String filePath) {
        setActualSize(0);
        setFilePath(filePath);
        setFull(false);
        setId(id);
        setCurrentFileId(0);
        try {
            setReadFile(new RandomAccessFile(filePath, "r"));
            setWriteFile(new RandomAccessFile(filePath, "rw"));
        } catch (FileNotFoundException e) {
            throw new CommonException(FILE_OPEN_ERROR, e.getMessage());
        }
    }
    
 

    public long getId() {
    
        return id;
    }

    public void setId(long id) {
    
        this.id = id;
    }

    public long getActualSize() {
    
        return actualSize;
    }

    public void setActualSize(long actualSize) {
    
        this.actualSize = actualSize;
    }

    public RandomAccessFile getReadFile() {
    
        return readFile;
    }

    public void setReadFile(RandomAccessFile readFile) {
    
        this.readFile = readFile;
    }

    public String getFilePath() {
    
        return filePath;
    }

    public void setFilePath(String filePath) {
    
        this.filePath = filePath;
    }

    public boolean isFull() {
    
        return isFull;
    }

    public void setFull(boolean isFull) {
    
        this.isFull = isFull;
    }

    public RandomAccessFile getWriteFile() {
    
        return writeFile;
    }

    public void setWriteFile(RandomAccessFile writeFile) {
    
        this.writeFile = writeFile;
    }



    public int getCurrentFileId() {
        
        return currentFileId;
            
    }



    public void setCurrentFileId(int currentFileId) {
        
        this.currentFileId = currentFileId;
            
    }
    
    
}

    