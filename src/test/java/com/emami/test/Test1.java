package com.emami.test;

import com.emami.scwa.model.Message;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

public class Test1 {
    @Test
    public void testMessageGson(){
        Message message=new Message("messageType","userType","from","to","content","auth");
        Gson gson=new Gson();
        System.out.println(gson.toJson(message));
    }
}
