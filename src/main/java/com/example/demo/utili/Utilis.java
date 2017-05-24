package com.example.demo.utili;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.FileCopyUtils;

public class Utilis {

    public String convertStreamToString(InputStream is) throws IOException {
        return new String(FileCopyUtils.copyToByteArray(is), StandardCharsets.UTF_8);
    }

    public JSONObject convertInputStreamToJSON(InputStream in) throws IOException, JSONException {
        String inputStreamString = convertStreamToString(in);
        JSONObject obj = new JSONObject(inputStreamString);

        return obj;
    }

    // public File copyInputStreamToFile(InputStream in) throws IOException {
    //
    // File file = File.createTempFile("tempfile", ".json");
    //
    // try {
    // OutputStream out = new FileOutputStream(file);
    // byte[] buf = new byte[1024];
    // int len;
    // while ((len = in.read(buf)) > 0) {
    // out.write(buf, 0, len);
    // }
    // out.close();
    // in.close();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return file;
    // }

}
