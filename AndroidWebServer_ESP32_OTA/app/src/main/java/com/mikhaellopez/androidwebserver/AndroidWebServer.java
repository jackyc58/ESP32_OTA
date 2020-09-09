package com.mikhaellopez.androidwebserver;

import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Mikhael LOPEZ on 14/12/2015.
 */
public class AndroidWebServer extends NanoHTTPD {
    private String rootDir;
    //private String hostname;

    /*public AndroidWebServer(int port, String rootDir)
    {
        super("192.168.1.57", port);
        this.rootDir = rootDir;
    }*/

    public AndroidWebServer(int port) {
        super(port);
    }

    public AndroidWebServer(String hostname, int port) {
        super(hostname, port);
        //this.hostname = hostname;
    }

    @Override
    public Response serve(IHTTPSession session) {
        /*
        String msg = "<html><body><h1>Hello server</h1>\n";
        Map<String, String> parms = session.getParms();
        if (parms.get("username") == null) {
            msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
        } else {
            msg += "<p>Hello, " + parms.get("username") + "!</p>";
        }
        return newFixedLengthResponse( msg + "</body></html>\n" );*/

        //String filename1=GetFilename(parms);
        String fileName=session.getUri().substring(1);

        //File file1 = new File(rootDir + filename1);


            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(folder, fileName);

            if (file.exists())
                return downloadFile(file);
            else
                return newFixedLengthResponse( "File not found!!\n" );

    }

    private Response downloadFile(File file)
    {
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);
            return newFixedLengthResponse(Response.Status.OK, "application/octet-stream", fis, file.length());
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(AndroidWebServer.class.getName()).log(Level.SEVERE, null, ex);
            return newFixedLengthResponse( "File "+file.getName()+" not Found!" );
        }


    }


/*
    @Override
    public Response newFixedLengthResponse(Response.IStatus status, String mimeType, String message)
    {
        Response response = super.newFixedLengthResponse(status, mimeType, message);
        response.addHeader("Accept-Ranges", "bytes");
        return response;
    }*/
}
