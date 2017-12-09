package com.crud.portal;

import java.io.Serializable;

import org.primefaces.model.UploadedFile;

public class FileUploadHolder implements Serializable
{
    private UploadedFile file;

    public UploadedFile getFile()
    {
        return file;
    }

    public void setFile(UploadedFile file)
    {
        this.file = file;
    }

}
